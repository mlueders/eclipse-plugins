/*
 * Created on Jan 31, 2005
 * @author mike
 */
package com.pfs.launcher.actions;


import com.pfs.base.BasePlugin;
import com.pfs.base.actions.BaseActionDelegate;
import com.pfs.launcher.ILauncher;
import com.pfs.launcher.LauncherFactory;
import com.pfs.launcher.QuickLaunchPlugin;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.ui.texteditor.ITextEditor;


public abstract class AbstractLauncherAction extends BaseActionDelegate {
  
  protected BasePlugin getPlugin() {
    return QuickLaunchPlugin.getDefault();
  }
  
  protected ILaunchManager getLaunchManager() {
    return DebugPlugin.getDefault().getLaunchManager();
  }
  
  protected String getLauncherName( String launcherPreferenceName ) {
    String name = getPreferences().getString( launcherPreferenceName );
    
    return ((name == null) || (name.trim().length() == 0)) ? "" : name;
  }
  
  protected void clearLaunchConfiguration( String launcherPreferenceName ) {
    setLauncherName( launcherPreferenceName, "" );
  }
  
  protected void setLauncherName( String launcherPreferenceName, String launcherName ) {
    getPreferences().setValue( launcherPreferenceName, launcherName );
  }
  
  /**
   * Launches a new configuration based on the active compilation unit.
   * @return the name of the launcher
   */
  protected String launchActive( String runMode ) {
    ITextEditor editor = getActiveTextEditor();
    String launcherName = null;
    
    if( editor != null ) {
      ILauncher launcher = LauncherFactory.createLauncher( editor, runMode );
      
      if( launcher != null ) {
        if( launcher.supportsLaunchMode() ) {
          launcherName = launcher.getLauncherName();
          launcher.run();
        }
        else {
          notifyUnsupportedLaunchMode( runMode );
        }
      }
    }
    return launcherName;
  }
  
  protected void notifyUnsupportedLaunchMode( String runMode ) {
    showInfo( "Unsupported launch mode '" + runMode + "'" );
  }
}