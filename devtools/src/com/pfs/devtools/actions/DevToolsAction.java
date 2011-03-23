package com.pfs.devtools.actions;

import com.pfs.base.BasePlugin;
import com.pfs.base.actions.BaseActionDelegate;
import com.pfs.devtools.DevToolsPlugin;

public abstract class DevToolsAction extends BaseActionDelegate {

	protected BasePlugin getPlugin() {
		return DevToolsPlugin.getDefault();
	}
}