package com.pfs.devtools.preferences;

import java.lang.reflect.Field;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.pfs.devtools.DevToolsPlugin;

public class DevToolsPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	private static final class InitWorkspaceDefaults extends StringButtonFieldEditor {
		
		public InitWorkspaceDefaults(Composite parent) {
			super("com.pfs.applymydefaultprefs", "Apply My Default Prefs", parent);
		}

		@Override
		protected String changePressed() {
			try {
				resetDefaultPreferences();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
			return null;
		}
		
		private void resetDefaultPreferences() throws Exception {
			setPreference("org.eclipse.core.resources", "description.autobuilding", false);
			setPreference("org.eclipse.debug.ui", "Console.lowWaterMark", 500000);
			setPreference("org.eclipse.debug.ui", "Console.highWaterMark", 508000);
			setPreference("org.eclipse.debug.ui", "org.eclipse.debug.ui.switch_perspective_on_suspend", "always");
			setPreference("org.eclipse.jdt.ui", "smart_semicolon", true);
			setPreference("org.eclipse.jdt.ui", "smart_opening_brace", true);
			setPreference("org.eclipse.jdt.ui", "content_assist_guess_method_arguments", true);
			setPreference("org.eclipse.team.ui", "org.eclipse.team.ui.sychronizing_default_participant", "org.tigris.subversion.subclipse.participant");
			setPreference("org.eclipse.ui", "SHOW_TEXT_ON_PERSPECTIVE_BAR", false);
			setPreference("org.eclipse.ui.editors", "lineNumberRuler", true);
			setPreference("org.eclipse.ui.editors", "spellingEnabled", false);
			setPreference("org.eclipse.ui.ide", "EXIT_PROMPT_ON_CLOSE_LAST_WINDOW", false);
			setPreference("org.eclipse.ui.ide", "SAVE_ALL_BEFORE_BUILD", true);
			setPreference("org.eclipse.ui.workbench", "RUN_IN_BACKGROUND", true);
			setPreference("org.tigris.subversion.subclipse.tools.usage", "allow_usage_report_preference", true);
			setPreference("org.tigris.subversion.subclipse.tools.usage", "ask_user_for_usage_report_preference", false);
			initializeMyKeyBindings();
		}
		
		private void setPreference(String qualifier, String name, boolean value) throws Exception {
	        ScopedPreferenceStore ps = new ScopedPreferenceStore(InstanceScope.INSTANCE, qualifier);
	        ps.setValue(name, value);
	        ps.save();
		}
	
		private void setPreference(String qualifier, String name, int value) throws Exception {
	        ScopedPreferenceStore ps = new ScopedPreferenceStore(InstanceScope.INSTANCE, qualifier);
	        ps.setValue(name, value);
	        ps.save();
		}
		
		private void setPreference(String qualifier, String name, String value) throws Exception {
	        ScopedPreferenceStore ps = new ScopedPreferenceStore(InstanceScope.INSTANCE, qualifier);
	        ps.setValue(name, value);
	        ps.save();
		}
		
		private void initializeMyKeyBindings() throws Exception {
			String osName = System.getProperty("os.name");
			if (osName.toLowerCase().contains("mac")) {
				setPreference("org.eclipse.ui", "KEY_CONFIGURATION_ID", "com.pfs.keybindings.luedersMacAcceleratorConfiguration");
				installMacUserKeyBindingOverrides();
			} else {
				setPreference("org.eclipse.ui", "KEY_CONFIGURATION_ID", "com.pfs.keybindings.luedersAcceleratorConfiguration");
			}
		}
		
		/**
		 * For some unknown and highly annoying reason, some key bindings in lueders/plugin.xml are getting
		 * completely ignored.    
		 */
		private void installMacUserKeyBindingOverrides() throws Exception {
			SimpleBindingManager bindingsManager = new SimpleBindingManager("com.pfs.keybindings.luedersMacAcceleratorConfiguration");
			bindingsManager.addUserBinding("COMMAND+W", "org.eclipse.ui.edit.copy", "org.eclipse.ui.contexts.dialogAndWindow");
			bindingsManager.addUserBinding("COMMAND+3", "org.eclipse.ui.window.activateEditor", "org.eclipse.ui.contexts.window");
			bindingsManager.addUserTextEditorBinding("COMMAND+L", "com.pfs.devtools.CenterEditorCommand");
			bindingsManager.addUserTextEditorBinding("Ctrl+K", "org.eclipse.ui.file.close");
			bindingsManager.addUserTextEditorBinding("Ctrl+V", "org.eclipse.ui.edit.text.goto.pageUp");
		}
		
	}
	
	public DevToolsPreferencePage() {
		super(GRID);
		setPreferenceStore(DevToolsPlugin.getDefault().getPreferenceStore());
		setDescription("Development extension preferences");
	}

	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.INITIAL_DEFAULT_VM_ARGS, "Initial Default &VM Args:",
				getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.DERIVED_FOLDER_NAMES, "Initial &Derived Directory Names:",
				getFieldEditorParent()));
		addField(new InitWorkspaceDefaults(getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}
}