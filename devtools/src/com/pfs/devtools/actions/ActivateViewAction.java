package com.pfs.devtools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewPart;


public class ActivateViewAction extends DevToolsAction {
  public static class EditorList extends ActivateViewAction {

    public EditorList() {
      super( "org.eclipse.ui.views.EditorList" );
    }
  }
  
  
  private String _viewId;
  
  public ActivateViewAction( String viewId ) {
    _viewId = viewId;
  }

  protected void onRun( IAction action ) throws Exception {
    IViewPart view = getWindow().getActivePage().findView( _viewId );
    
    if( view != null )
      getWindow().getActivePage().activate( view );
  }
}
