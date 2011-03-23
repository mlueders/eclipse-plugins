/*
 * Created on Feb 16, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import com.pfs.base.PluginSupport;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;


public class CenterEditorAction extends DevToolsAction {

  protected void onRun( IAction action ) throws Exception {
    ITextEditor editor = getActiveTextEditor();
    
    if( editor instanceof AbstractTextEditor ) {
      ISourceViewer viewer = (ISourceViewer) PluginSupport.invokeInaccessibleMethod( editor, "getSourceViewer", new Object[0] );
      ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();
      
      int topIndex = viewer.getTopIndex();
      int bottomIndex = viewer.getBottomIndex();
      int newTopIndex = sel.getStartLine() - ((bottomIndex - topIndex) / 2);
      
      if( newTopIndex < 0 )
        newTopIndex = 0;
      
      viewer.setTopIndex( newTopIndex );
    }
  }

}
