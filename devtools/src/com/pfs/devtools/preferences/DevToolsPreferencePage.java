package com.pfs.devtools.preferences;

import com.pfs.devtools.DevToolsPlugin;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class DevToolsPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public DevToolsPreferencePage() {
		super(GRID);
		setPreferenceStore(DevToolsPlugin.getDefault().getPreferenceStore());
		setDescription("Development extension preferences");
	}

	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.INITIAL_DEFAULT_VM_ARGS, "&Initial Default VM Args:",
				getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}
}