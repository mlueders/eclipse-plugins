package com.pfs.devtools.preferences;

import com.pfs.devtools.DevToolsPlugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = DevToolsPlugin.getDefault().getPreferenceStore();

		store.setDefault(PreferenceConstants.INITIAL_DEFAULT_VM_ARGS, "");
	}
}
