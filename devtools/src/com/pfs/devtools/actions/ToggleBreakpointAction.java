package com.pfs.devtools.actions;

import org.eclipse.jdt.internal.debug.ui.actions.ToggleBreakpointAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;


/**
 * Toggle Breakpoint is apparently broken in M5...
 */
public class ToggleBreakpointAction extends DevToolsAction {
  private ToggleBreakpointAdapter _adapter;
  
  public ToggleBreakpointAction() {
    _adapter = new ToggleBreakpointAdapter();
  }

  protected void onRun( IAction action ) throws Exception {
    IEditorPart activeEditor = getActiveEditor();
    
    if( activeEditor != null ) {
      ISelection selection = getWindow().getSelectionService().getSelection();
      
      if( selection != null ) {
        if( _adapter.canToggleLineBreakpoints( activeEditor, selection ) )
          _adapter.toggleLineBreakpoints( activeEditor, selection, true );
        else if( _adapter.canToggleWatchpoints( activeEditor, selection ) )
          _adapter.toggleWatchpoints( activeEditor, selection );
      }
    }
  }
}
