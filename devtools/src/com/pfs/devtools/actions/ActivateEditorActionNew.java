/*
 * Created on Feb 17, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;


public abstract class ActivateEditorActionNew extends DevToolsAction {
  public static class Next extends ActivateEditorActionNew {
    protected int getIndexToActivate( int index ) {
      return index + 1;
    }
  }
  
  public static class Previous extends ActivateEditorActionNew {
    protected int getIndexToActivate( int index ) {
      return index - 1;
    }
  }
  
  protected abstract int getIndexToActivate( int index );
  
  protected void onRun( IAction action ) throws Exception {
    IWorkbenchPage activePage = getWindow().getActivePage();
    IEditorPart activeEditor = activePage.getActiveEditor();
    
    IEditorReference[] references = activePage.getEditorReferences();
    for( int i = 0; i < references.length; i++ ) {
      if( references[i].getEditor( false ) == activeEditor ) {
        int index = getIndexToActivate( i );
        
        if( index < 0 )
          index = references.length - 1;
        
        if( index >= references.length )
          index = 0;
        
        activePage.activate( references[index].getEditor( true ) );
        break;
      }
    }
  }
}
