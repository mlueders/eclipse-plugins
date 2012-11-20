/*
 * Created on Feb 5, 2005
 * @author mike
 */
package com.pfs.base.preferences;

public class PreferenceNameFactory {
	private String prefix;

	public PreferenceNameFactory(Class<?> type) {
		prefix = type.getName();
	}

	public String createPreferenceName(String preference) {
		return prefix + "." + preference;
	}
}
