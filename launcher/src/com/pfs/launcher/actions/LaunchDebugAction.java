/*
 * Created on Feb 3, 2005
 * @author mike
 */
package com.pfs.launcher.actions;

import com.pfs.launcher.preferences.PreferenceConstants;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.internal.core.LaunchManager;
import org.eclipse.jface.action.IAction;

public class LaunchDebugAction extends BindableLaunchAction {
	public static class First extends LaunchDebugAction {
		public First() {
			super(PreferenceConstants.FIRST_LAUNCHER);
		}
	}

	public static class Second extends LaunchDebugAction {
		public Second() {
			super(PreferenceConstants.SECOND_LAUNCHER);
		}
	}

	public static class Third extends LaunchDebugAction {
		public Third() {
			super(PreferenceConstants.THIRD_LAUNCHER);
		}
	}

	public static class Fourth extends LaunchDebugAction {
		public Fourth() {
			super(PreferenceConstants.FOURTH_LAUNCHER);
		}
	}

	public static class Fifth extends LaunchDebugAction {
		public Fifth() {
			super(PreferenceConstants.FIFTH_LAUNCHER);
		}
	}

	public LaunchDebugAction(String launchConfigurationPreferenceName) {
		super(launchConfigurationPreferenceName);
	}

	protected void onRun(IAction action) throws CoreException {
		launch(LaunchManager.DEBUG_MODE);
	}
}