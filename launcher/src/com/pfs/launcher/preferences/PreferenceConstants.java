package com.pfs.launcher.preferences;

import com.pfs.base.preferences.PreferenceNameFactory;

/**
 * Constant definitions for plug-in preferences
 */
public interface PreferenceConstants {

	PreferenceNameFactory FACTORY = new PreferenceNameFactory(PreferenceConstants.class);

	String FIRST_LAUNCHER = FACTORY.createPreferenceName("firstLauncherPreference");

	String SECOND_LAUNCHER = FACTORY.createPreferenceName("secondLauncherPreference");

	String THIRD_LAUNCHER = FACTORY.createPreferenceName("thirdLauncherPreference");

	String FOURTH_LAUNCHER = FACTORY.createPreferenceName("fourthLauncherPreference");

	String FIFTH_LAUNCHER = FACTORY.createPreferenceName("fifthLauncherPreference");
}
