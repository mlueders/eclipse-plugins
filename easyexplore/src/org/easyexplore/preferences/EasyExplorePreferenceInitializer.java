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
    store.setDefault(EasyExplorePreferencePage.P_EXPLORE_TARGET, "explorer.exe {0}");
    // quotes are necessary around {0} in case there's a space in the directory name
    store.setDefault(EasyExplorePreferencePage.P_CMD_TARGET, "cmd.exe /k start \"Eclipse Command\" /D\"{0}\"");
  }
}
