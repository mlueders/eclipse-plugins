/*
 * Created on Feb 10, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;


public class HideActiveViewAction extends DevToolsAction {
  
  protected void onRun( IAction action ) throws Exception {
    IWorkbenchPage activePage = getWindow().getActivePage();
    IWorkbenchPart active = activePage.getActivePart();
    
    if( active instanceof IViewPart )
      activePage.hideView( (IViewPart) active );
  }
}
