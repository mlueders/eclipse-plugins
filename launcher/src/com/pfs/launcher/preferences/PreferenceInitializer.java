package com.pfs.launcher.preferences;

import com.pfs.launcher.QuickLaunchPlugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

  public void initializeDefaultPreferences() {
    IPreferenceStore store = QuickLaunchPlugin.getDefault().getPreferenceStore();

    store.setDefault( PreferenceConstants.FIRST_LAUNCHER, "" );
    store.setDefault( PreferenceConstants.SECOND_LAUNCHER, "" );
    store.setDefault( PreferenceConstants.THIRD_LAUNCHER, "" );
    store.setDefault( PreferenceConstants.FOURTH_LAUNCHER, "" );
    store.setDefault( PreferenceConstants.FIFTH_LAUNCHER, "" );
  }
}
