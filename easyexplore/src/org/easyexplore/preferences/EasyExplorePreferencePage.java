package org.easyexplore.preferences;

import org.easyexplore.EasyExplorePlugin;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preference for program used by Easy Explore
 */
public class EasyExplorePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	public static final String P_EXPLORE_TARGET = "exploreTargetPreference";
	public static final String P_CMD_TARGET = "cmdTargetPreference";

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
		addField(new StringFieldEditor(P_EXPLORE_TARGET, "&Explore Target:", getFieldEditorParent()));
		addField(new StringFieldEditor(P_CMD_TARGET, "&Command Target:", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}
}