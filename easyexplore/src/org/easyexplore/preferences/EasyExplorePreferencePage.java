package org.easyexplore.preferences;

import org.easyexplore.EasyExplorePlugin;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preference for program used by Easy Explore
 */
public class EasyExplorePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public static final String P_EXPLORE_TARGET = "exploreTargetPreference";
	public static final String P_CMD_TARGET = "cmdTargetPreference";

	static final String WINDOWS_EXPLORE_TARGET = "explorer.exe {0}";
	// quotes are necessary around {0} in case there's a space in the directory name
	static final String WINDOWS_CMD_TARGET = "cmd.exe /k start \"Eclipse Command\" /D\"{0}\"";
	static final String LINUX_EXPLORE_TARGET = "nautilus {0}";
	static final String LINUX_CMD_TARGET = "gnome-terminal --working-directory={0}";
	
	private static final String PROPERTY_CHOICE_LINUX = "linux";
	private static final String PROPERTY_CHOICE_WINDOWS = "windows";
	
	private StringFieldEditor cmdTargetField;
	private StringFieldEditor exploreTargetField;
	
	public EasyExplorePreferencePage() {
		super(GRID);
		setPreferenceStore(EasyExplorePlugin.getDefault().getPreferenceStore());
		setDescription("Set up your file explorer application.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI blocks needed to manipulate various types of preferences.
	 * Each field editor knows how to save and restore itself.
	 */

	public void createFieldEditors() {
		String[][] labelAndValues = new String[][] { 
				{ "&Linux", PROPERTY_CHOICE_LINUX },
				{ "&Windows", PROPERTY_CHOICE_WINDOWS } };
		RadioGroupFieldEditor radioEditor = new RadioGroupFieldEditor("CHOICE", "Operating System", 1, labelAndValues, getFieldEditorParent());
		exploreTargetField = new StringFieldEditor(P_EXPLORE_TARGET, "&Explore Target:", getFieldEditorParent());
		cmdTargetField = new StringFieldEditor(P_CMD_TARGET, "&Command Target:", getFieldEditorParent());
		
		addField(radioEditor);
		addField(exploreTargetField);
		addField(cmdTargetField);
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		
		if ("field_editor_value".equals(event.getProperty())) {
			if (PROPERTY_CHOICE_LINUX.equals(event.getNewValue())) {
				cmdTargetField.setStringValue(LINUX_CMD_TARGET);
				exploreTargetField.setStringValue(LINUX_EXPLORE_TARGET);
			} else if (PROPERTY_CHOICE_WINDOWS.equals(event.getNewValue())) {
				cmdTargetField.setStringValue(WINDOWS_CMD_TARGET);
				exploreTargetField.setStringValue(WINDOWS_EXPLORE_TARGET);
			}
		}
	}
	
	public void init(IWorkbench workbench) {
	}
}