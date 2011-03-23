package com.pfs.launcher.launchers;

import com.pfs.launcher.ILauncher;

import org.eclipse.ui.texteditor.ITextEditor;



public abstract class AbstractLauncher implements ILauncher {
  private LauncherConfig _config;
  
  public AbstractLauncher( LauncherConfig config ) {
    _config = config;
  }
  
  protected LauncherConfig getConfig() {
    return _config;
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
