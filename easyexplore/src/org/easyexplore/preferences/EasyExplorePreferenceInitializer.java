package org.easyexplore.preferences;

import org.easyexplore.EasyExplorePlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class EasyExplorePreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = EasyExplorePlugin.getDefault().getPreferenceStore();
		store.setDefault(EasyExplorePreferencePage.P_EXPLORE_TARGET, EasyExplorePreferencePage.LINUX_EXPLORE_TARGET);
		store.setDefault(EasyExplorePreferencePage.P_CMD_TARGET, EasyExplorePreferencePage.LINUX_CMD_TARGET);
	}
}
