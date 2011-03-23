/*
 * Created on Feb 6, 2005
 * @author mike
 */
package org.easyexplore.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionDelegate;


public class DirectoryWorkbenchAction extends ActionDelegate implements IWorkbenchWindowActionDelegate {
  public static class Explore extends DirectoryWorkbenchAction {
    public Explore() {
      super( ActionExecutor.EXPLORE );
    }
  }
  
  public static class Command extends DirectoryWorkbenchAction {
    public Command() {
      super( ActionExecutor.COMMAND );
    }
  }
  
  
  private IWorkbenchWindow window;
  private ActionExecutor executor;
  
  public DirectoryWorkbenchAction( ActionExecutor executor ) {
    this.executor = executor;
  }
  
  public void init( IWorkbenchWindow window ) {
    this.window = window;
  }
  
  public void run( IAction action ) {
    IEditorPart editor = window.getActivePage().getActiveEditor();
    
    if( editor != null ) {
      IEditorInput input = editor.getEditorInput();
      
      if( input != null ) {
        IResource resource = (IResource) input.getAdapter( IResource.class );
        
        if( resource != null )
          executor.execute( resource );
      }
    }
  }
}
