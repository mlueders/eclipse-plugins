/*
 * Created on Feb 4, 2005
 * @author mike
 */
package com.pfs.launcher.actions;

import org.eclipse.debug.internal.core.LaunchManager;
import org.eclipse.jface.action.IAction;

public class LaunchActiveAction extends AbstractLauncherAction {
	public static class Debug extends LaunchActiveAction {
		protected void onRun(IAction action) throws Exception {
			launchActive(LaunchManager.DEBUG_MODE);
		}
	}

	protected void onRun(IAction action) throws Exception {
		launchActive(LaunchManager.RUN_MODE);
	}
}
