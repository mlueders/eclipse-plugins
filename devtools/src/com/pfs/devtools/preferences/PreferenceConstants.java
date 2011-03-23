package com.pfs.devtools.preferences;

import com.pfs.base.preferences.PreferenceNameFactory;

/**
 * Constant definitions for plug-in preferences
 */
public interface PreferenceConstants {

	PreferenceNameFactory FACTORY = new PreferenceNameFactory(PreferenceConstants.class);

	String INITIAL_DEFAULT_VM_ARGS = FACTORY.createPreferenceName("initialDefaultVMArgsPreference");
}
