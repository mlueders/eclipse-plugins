/*
 * Created on Feb 3, 2005
 * @author mike
 */
package com.pfs.launcher.actions;

import com.pfs.base.actions.BaseSelectionListener;
import com.pfs.launcher.preferences.PreferenceConstants;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.internal.core.LaunchManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

public class LaunchRunAction extends BindableLaunchAction implements IWorkbenchWindowPulldownDelegate {
  public static class First extends LaunchRunAction {
    public First() {
      super( PreferenceConstants.FIRST_LAUNCHER );
    }
  }

  public static class Second extends LaunchRunAction {
    public Second() {
      super( PreferenceConstants.SECOND_LAUNCHER );
    }
  }

  public static class Third extends LaunchRunAction {
    public Third() {
      super( PreferenceConstants.THIRD_LAUNCHER );
    }
  }

  public static class Fourth extends LaunchRunAction {
    public Fourth() {
      super( PreferenceConstants.FOURTH_LAUNCHER );
    }
  }

  public static class Fifth extends LaunchRunAction {
    public Fifth() {
      super( PreferenceConstants.FIFTH_LAUNCHER );
    }
  }
  
  
  private Menu _historyMenu;
  private BoundRunHistoryMenuAction _runHistoryMenuAction;
  
  public LaunchRunAction( String launchConfigurationPreferenceName ) {
    super( launchConfigurationPreferenceName );
  }
  
  public void init( final IAction action ) {
    super.init( action );
    
    final ImageDescriptor boundImage = getImageDescriptor( action, "bound" );
    final ImageDescriptor unboundImage = getImageDescriptor( action, "unbound" );
    
    // initialize the tooltip and image
    decorate( action, getLauncherName(), boundImage, unboundImage );
    
    getPreferences().addPropertyChangeListener( new IPropertyChangeListener() {
      public void propertyChange( PropertyChangeEvent event ) {
        if( getLauncherPreferenceName().equals( event.getProperty() ) ) {
          String launcherName = event.getNewValue().toString().trim();
          
          decorate( action, launcherName, boundImage, unboundImage );
        }
      }
    });
  }
  
  public void dispose() {
    super.dispose();
    
    if( _historyMenu != null ) {
      _historyMenu.dispose();
      _historyMenu = null;
    }
    
    if( _runHistoryMenuAction != null ) {
      _runHistoryMenuAction.dispose();
      _runHistoryMenuAction = null;
    }
  }
  
  private ImageDescriptor getImageDescriptor( IAction action, String type ) {
    String location = "/icons/" + action.getId() + "-" + type + ".gif";
    
    return getPlugin().getImageDescriptor( location );
  }
  
  protected void decorate( IAction action, String launcherName, ImageDescriptor boundImage, ImageDescriptor unboundImage ) {
    ImageDescriptor image = isLauncherBound() ? boundImage : unboundImage;
    action.setImageDescriptor( image );
    action.setToolTipText( launcherName );
  }
  
  protected void onRun( IAction action ) throws Exception {
    launch( LaunchManager.RUN_MODE );
  }

  public Menu getMenu( Control parent ) {
    Menu menu = new Menu( parent );
    
    addDebugAction( menu );
    addClearAction( menu );
    addHistoryAction( menu );
    return menu;
  }
  
  private void addDebugAction( Menu menu ) {
    MenuItem debug = new MenuItem( menu, SWT.PUSH );
    
    debug.setText( "Debug" );
    debug.addSelectionListener( new BaseSelectionListener( getPlugin() ) {
      protected void onSelection() throws CoreException {
        launch( LaunchManager.DEBUG_MODE );
      }
    });
  }
  
  private void addClearAction( Menu menu ) {
    MenuItem clear = new MenuItem( menu, SWT.PUSH );
    
    clear.setText( "Clear" );
    clear.addSelectionListener( new BaseSelectionListener( getPlugin() ) {
      protected void onSelection() throws CoreException {
        clearLaunchConfiguration();
      }
    });
  }
  
  private void addHistoryAction( Menu menu ) {
    _runHistoryMenuAction = new BoundRunHistoryMenuAction( this );
    _historyMenu = _runHistoryMenuAction.getMenu( menu );
    
    MenuItem historyMenuItem = new MenuItem( menu, SWT.CASCADE );
    historyMenuItem.setText( "Bind" );
    historyMenuItem.setMenu( _historyMenu );
  }
}