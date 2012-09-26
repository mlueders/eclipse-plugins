package com.pfs.devtools.preferences;

import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.pfs.base.BasePlugin;
import com.pfs.devtools.DevToolsPlugin;

public class DevToolsPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	private static final class InitWorkspaceDefaults extends StringButtonFieldEditor {

		public InitWorkspaceDefaults(Composite parent) {
			super("com.pfs.resetdefaults", "Reset default preferences", parent);
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
			setPreference("org.eclipse.ui.editors", "lineNumberRuler", true);
			setPreference("org.eclipse.ui.ide", "EXIT_PROMPT_ON_CLOSE_LAST_WINDOW", false);
			setPreference("org.eclipse.ui.ide", "SAVE_ALL_BEFORE_BUILD", true);
		}
		
		private void setPreference(String qualifier, String name, boolean value) throws Exception {
	        ScopedPreferenceStore ps = new ScopedPreferenceStore(InstanceScope.INSTANCE, qualifier);
	        ps.setValue(name, value);
	        ps.save();
		}
	
	}

	public DevToolsPreferencePage() {
		super(GRID);
		setPreferenceStore(DevToolsPlugin.getDefault().getPreferenceStore());
		setDescription("Development extension preferences");
	}

	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.INITIAL_DEFAULT_VM_ARGS, "&Initial Default VM Args:",
				getFieldEditorParent()));
		addField(new InitWorkspaceDefaults(getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}
}