/*
 * Created on Feb 9, 2005
 * @author mike
 */
package com.pfs.launcher.actions;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.internal.core.LaunchManager;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.actions.RunHistoryMenuAction;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationManager;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchHistory;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.actions.LaunchAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.externaltools.internal.model.IExternalToolConstants;

/**
 * A specialized run history menu which binds the selected action to the 
 * associated BindableLaunchAction.
 */
class BoundRunHistoryMenuAction extends RunHistoryMenuAction {
  private BindableLaunchAction _boundLaunchAction;
  
  public BoundRunHistoryMenuAction( BindableLaunchAction boundLaunchAction ) {
    _boundLaunchAction = boundLaunchAction;
  }
  
  protected void fillMenu(Menu menu) {
    int menuItemCount = fillMenu( menu, getLaunchHistory() );
    
    MenuItem separator = null;
    if( menuItemCount > 0 )
      separator = new MenuItem( menu, SWT.SEPARATOR );
    
    menuItemCount = fillMenu( menu, getExternalLaunchHistory() );
    if( (separator != null) && (menuItemCount == 0) )
      separator.dispose();
  }
  
  // copied from superclass b/c I needed access to the action creation (really, i just
  // need to know when an item is selected and the associated launch configuration, and
  // this is the easiest way to get all that).
  private int fillMenu( Menu menu, LaunchHistory history ) {
    ILaunchConfiguration[] historyList= LaunchConfigurationManager.filterConfigs(history.getHistory());
    ILaunchConfiguration[] favoriteList = LaunchConfigurationManager.filterConfigs(history.getFavorites());
    int itemCount = 0;
    
    // Add favorites
    int accelerator = 1;
    for (int i = 0; i < favoriteList.length; i++) {
      ILaunchConfiguration launch= favoriteList[i];
      BoundLaunchActionDelegate action= new BoundLaunchActionDelegate(_boundLaunchAction,launch, getMode());
      addToMenu(menu, action, accelerator);
      accelerator++;
      itemCount++;
    }   
    
    // Separator between favorites and history
    if (favoriteList.length > 0 && historyList.length > 0) {
      addSeparator(menu);
    }
    
    // Add history launches next
    for (int i = 0; i < historyList.length; i++) {
      ILaunchConfiguration launch= historyList[i];
      BoundLaunchActionDelegate action= new BoundLaunchActionDelegate(_boundLaunchAction,launch, getMode());
      addToMenu(menu, action, accelerator);
      accelerator++;
      itemCount++;
    }
    
    return itemCount;
  }
  
  private LaunchHistory getExternalLaunchHistory() {
    return getLaunchConfigurationManager().getLaunchHistory( IExternalToolConstants.ID_EXTERNAL_TOOLS_LAUNCH_GROUP );
  } 
  
  private LaunchConfigurationManager getLaunchConfigurationManager() {
    return DebugUIPlugin.getDefault().getLaunchConfigurationManager();
  }
  
  private static class BoundLaunchActionDelegate extends LaunchAction {
    private BindableLaunchAction _boundLaunchAction;
    private ILaunchConfiguration _configuration;
    private String _launchMode;
    
    public BoundLaunchActionDelegate( BindableLaunchAction boundLaunchAction, ILaunchConfiguration configuration, String mode ) {
      super( configuration, mode );
      _launchMode = mode;
      _configuration = configuration;
      _boundLaunchAction = boundLaunchAction;
    }
    
    private void bindLaunchConfiguration() {
      _boundLaunchAction.setLauncherName( _configuration.getName() );
    }

    public void run() {
      bindLaunchConfiguration();
      
      DebugUITools.launch( _configuration, _launchMode );
    }
    
    public void runWithEvent(Event event) {
      // no modifiers == run and bind
      // holding shift down == run with debug and bind
      // holding shift + ctrl down == bind, but do not run
      if( (event.stateMask & SWT.SHIFT) > 0 ) {
        if( (event.stateMask & SWT.CONTROL) > 0 ) {
          bindLaunchConfiguration();
          return;
        }
        else {
          _launchMode = LaunchManager.DEBUG_MODE;
        }
      }
      else {
        _launchMode = LaunchManager.RUN_MODE;
      }
      
      super.runWithEvent( event );
    }
  }
}