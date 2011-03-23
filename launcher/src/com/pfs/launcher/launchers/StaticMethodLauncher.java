/*
 * Created on Feb 8, 2005
 * @author mike
 */
package com.pfs.launcher.launchers;

import com.pfs.launcher.QuickLaunchPlugin;
import com.pfs.launcher.StaticMethodLaunchConfiguration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

public class StaticMethodLauncher extends MainLauncher {
  private String _methodName;
  
  public StaticMethodLauncher( LauncherConfig config, String methodName ) {
    super( getConfig( config, methodName ) );
    _methodName = methodName;
  }
  
  private static LauncherConfig getConfig( LauncherConfig config, String methodName ) {
    String launcherName = config.getLauncherName() + "." + methodName;
    config.setLauncherName( launcherName );
    return config;
  }
  
  protected String getLauncherType() {
    return StaticMethodLaunchConfiguration.ID_STATIC_APPLICATION;
  }
  
  protected void setAdditionalAttributes( ILaunchConfigurationWorkingCopy workingCopy ) {
    String typeName;
    try {
      typeName = workingCopy.getAttribute( IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "" );
    }
    catch( CoreException ex ) {
      QuickLaunchPlugin.getDefault().logError( ex );
      throw new RuntimeException( ex );
    }
    
    workingCopy.setAttribute( StaticMethodLaunchConfiguration.ID_MAIN_TYPE_NAME, typeName );
    workingCopy.setAttribute( StaticMethodLaunchConfiguration.ID_METHOD_NAME, _methodName );
  }
}
