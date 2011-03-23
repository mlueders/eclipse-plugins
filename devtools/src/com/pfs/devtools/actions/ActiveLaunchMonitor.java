/*
 * Created on May 23, 2006
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener2;

import java.util.ArrayList;

public class ActiveLaunchMonitor implements ILaunchesListener2 {
	private static ILaunch[] EMPTY = new ILaunch[0];
	private static ActiveLaunchMonitor INSTANCE = null;

	public static ActiveLaunchMonitor getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ActiveLaunchMonitor();
			DebugPlugin.getDefault().getLaunchManager().addLaunchListener(INSTANCE);
		}

		return INSTANCE;
	}

	private ArrayList<ILaunch> active;
	private ArrayList<ILaunch> launches;

	private ActiveLaunchMonitor() {
		active = new ArrayList<ILaunch>();
		launches = new ArrayList<ILaunch>();
	}

	public ILaunch[] getAllLaunches() {
		return launches.toArray(EMPTY);
	}

	public ILaunch[] getActiveLaunches() {
		return active.toArray(EMPTY);
	}

	public ILaunch getLastActiveLaunch() {
		if (active.isEmpty()) {
			return null;
		}

		return active.get(0);
	}

	public void launchesTerminated(ILaunch[] launchesTerminated) {
		for (int i = 0; i < launchesTerminated.length; i++) {
			active.remove(launchesTerminated[i]);
		}
	}

	public void launchesAdded(ILaunch[] launchesAdded) {
		for (int i = 0; i < launchesAdded.length; i++) {
			launches.add(launchesAdded[i]);
			active.add(launchesAdded[i]);
		}
	}

	public void launchesChanged(ILaunch[] launchesChanged) {
	}

	public void launchesRemoved(ILaunch[] launchesRemoved) {
		for (int i = 0; i < launchesRemoved.length; i++) {
			launches.remove(launchesRemoved[i]);
		}
	}
}