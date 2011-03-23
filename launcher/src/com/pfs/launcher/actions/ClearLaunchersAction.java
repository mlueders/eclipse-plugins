/*
 * Created on Feb 4, 2005
 * @author mike
 */
package com.pfs.launcher.actions;

import com.pfs.launcher.preferences.PreferenceConstants;

import org.eclipse.jface.action.IAction;


public class ClearLaunchersAction extends AbstractLauncherAction {

  protected void onRun( IAction action ) throws Exception {
    clearLaunchConfiguration( PreferenceConstants.FIRST_LAUNCHER );
    clearLaunchConfiguration( PreferenceConstants.SECOND_LAUNCHER );
    clearLaunchConfiguration( PreferenceConstants.THIRD_LAUNCHER );
    clearLaunchConfiguration( PreferenceConstants.FOURTH_LAUNCHER );
    clearLaunchConfiguration( PreferenceConstants.FIFTH_LAUNCHER );
  }
}
