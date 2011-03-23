package com.pfs.launcher.launchers;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.ui.texteditor.ITextEditor;

public class LauncherConfig {
	private ICompilationUnit compilationUnit;
	private ITextEditor editor;
	private String launchMode;
	private String launcherName;
	private int position;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public ICompilationUnit getCompilationUnit() {
		return compilationUnit;
	}

	public void setCompilationUnit(ICompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
	}

	public ITextEditor getEditor() {
		return editor;
	}

	public void setEditor(ITextEditor editor) {
		this.editor = editor;
	}

	public String getLauncherName() {
		return launcherName;
	}

	public void setLauncherName(String launcherName) {
		this.launcherName = launcherName;
	}

	public String getLaunchMode() {
		return launchMode;
	}

	public void setLaunchMode(String launchMode) {
		this.launchMode = launchMode;
	}
}
