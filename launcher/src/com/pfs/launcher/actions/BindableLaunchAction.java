/*
 * Created on Feb 3, 2005
 * @author mike
 */
package com.pfs.launcher.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.jface.action.IAction;


public abstract class BindableLaunchAction extends AbstractLauncherAction {
  private String _launcherPreferenceName;

  protected BindableLaunchAction( String launcherPreferenceName ) {
    _launcherPreferenceName = launcherPreferenceName;
  }
  
  public void init( final IAction action ) {
    if( isLauncherBound() == false )
      setLauncherName( "" );
  }
  
  protected boolean isLauncherBound() {
    String launcherName = getLauncherName();
    
    if( launcherName.trim().length() == 0 )
      return false;
    
    try {
      return getLaunchConfiguration( launcherName ) != null;
    } 
    catch( Exception e ) {
      return false;
    }
  }
  
  protected String getLauncherPreferenceName() {
    return _launcherPreferenceName;
  }
  
  protected void clearLaunchConfiguration() {
    clearLaunchConfiguration( _launcherPreferenceName );
  }
  
  protected void setLauncherName( String name ) {
    setLauncherName( _launcherPreferenceName, name );
  }
  
  protected String getLauncherName() {
    return getLauncherName( _launcherPreferenceName );
  }
  
  protected void launch( String runMode ) throws CoreException {
    String launcherName = getLauncherName();
    ILaunchConfiguration config = getLaunchConfiguration( launcherName );
    
    if( config == null ) {
      launcherName = launchActive( runMode );
      
      if( launcherName != null )
        setLauncherName( launcherName );
    }
    else {
      ILaunchConfigurationType type = config.getType();
      
      if( type.supportsMode( runMode ) )
        config.launch( runMode, null );
      else
        notifyUnsupportedLaunchMode( runMode );
    }
  }
  
  protected ILaunchConfiguration getLaunchConfiguration( String name ) throws CoreException {
    ILaunchConfiguration[] configurations = getLaunchManager().getLaunchConfigurations();
    
    if( name.length() > 0 ) {
      for( int i = 0; i < configurations.length; i++ ) {
        if( name.equals( configurations[i].getName() ) )
          return configurations[i];
      }
    }
    return null;
  }
}
