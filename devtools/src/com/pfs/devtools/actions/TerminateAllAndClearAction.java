/*
 * Created on May 23, 2006
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.jface.action.IAction;

public class TerminateAllAndClearAction extends DevToolsAction {
	private ActiveLaunchMonitor monitor;

	public void init(IAction action) {
		super.init(action);

		monitor = ActiveLaunchMonitor.getInstance();
	}

	protected void onRun(IAction action) throws Exception {
		ILaunch[] launches = monitor.getActiveLaunches();

		for (int i = 0; i < launches.length; i++) {
			launches[i].terminate();
		}

		launches = monitor.getAllLaunches();
		for (int i = 0; i < launches.length; i++) {
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launches[i]);
		}
	}
}
