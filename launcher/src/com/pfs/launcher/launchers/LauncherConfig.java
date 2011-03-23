package com.pfs.launcher.launchers;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.ui.texteditor.ITextEditor;


public class LauncherConfig {
  private ICompilationUnit _compilationUnit;
  private ITextEditor _editor;
  private String _launchMode;
  private String _launcherName;
  private int _position;
  
  public int getPosition() {
    return _position;
  }
  
  public void setPosition( int position ) {
    _position = position;
  }

  public ICompilationUnit getCompilationUnit() {
    return _compilationUnit;
  }
  
  public void setCompilationUnit( ICompilationUnit compilationUnit ) {
    _compilationUnit = compilationUnit;
  }
  
  public ITextEditor getEditor() {
    return _editor;
  }
  
  public void setEditor( ITextEditor editor ) {
    _editor = editor;
  }
  
  public String getLauncherName() {
    return _launcherName;
  }
  
  public void setLauncherName( String launcherName ) {
    _launcherName = launcherName;
  }
  
  public String getLaunchMode() {
    return _launchMode;
  }
  
  public void setLaunchMode( String launchMode ) {
    _launchMode = launchMode;
  }
}
