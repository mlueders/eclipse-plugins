package com.pfs.launcher.launchers;

import com.pfs.launcher.ILauncher;

import org.eclipse.ui.texteditor.ITextEditor;

public abstract class AbstractLauncher implements ILauncher {
	private LauncherConfig config;

	public AbstractLauncher(LauncherConfig config) {
		this.config = config;
	}

	protected LauncherConfig getConfig() {
		return config;
	}

	protected ITextEditor getEditor() {
		return getConfig().getEditor();
	}

	public String getLauncherName() {
		return getConfig().getLauncherName();
	}

	public String getLaunchMode() {
		return getConfig().getLaunchMode();
	}

	public boolean supportsLaunchMode() {
		return true;
	}
}
