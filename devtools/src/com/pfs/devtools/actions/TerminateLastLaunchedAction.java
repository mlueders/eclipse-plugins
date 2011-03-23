/*
 * Created on Feb 15, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.jface.action.IAction;


public class TerminateLastLaunchedAction extends DevToolsAction {
  private ActiveLaunchMonitor _monitor;
  
  public void init( IAction action ) {
    super.init( action );
    
    _monitor = ActiveLaunchMonitor.getInstance();
  }
  
  protected void onRun( IAction action ) throws Exception {
    ILaunch launch = _monitor.getLastActiveLaunch();
    
    if( launch != null )
      launch.terminate();
  }
}
