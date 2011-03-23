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
    if( INSTANCE == null ) {
      INSTANCE = new ActiveLaunchMonitor();
      DebugPlugin.getDefault().getLaunchManager().addLaunchListener( INSTANCE );
    }
    
    return INSTANCE;
  }
  
  private ArrayList<ILaunch> _active;
  private ArrayList<ILaunch> _launches;

  private ActiveLaunchMonitor() {
    _active = new ArrayList<ILaunch>();
    _launches = new ArrayList<ILaunch>();
  }
  
  public ILaunch[] getAllLaunches() {
    return _launches.toArray( EMPTY );
  }
  
  public ILaunch[] getActiveLaunches() {
    return _active.toArray( EMPTY );
  }
  
  public ILaunch getLastActiveLaunch() {
    if( _active.isEmpty() )
      return null;
    
    return _active.get( 0 );
  }

  public void launchesTerminated( ILaunch[] launches ) {
    for( int i = 0; i < launches.length; i++ )
      _active.remove( launches[i] );
  }

  public void launchesAdded( ILaunch[] launches ) {
    for( int i = 0; i < launches.length; i++ ) {
      _launches.add( launches[i] );
      _active.add( launches[i] );
    }
  }

  public void launchesChanged( ILaunch[] launches ) {}
  
  public void launchesRemoved( ILaunch[] launches ) {
    for( int i = 0; i < launches.length; i++ )
      _launches.remove( launches[i] );
  }
}