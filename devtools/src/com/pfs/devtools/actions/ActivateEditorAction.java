/*
 * Created on Feb 17, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import com.pfs.base.PluginSupport;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.internal.EditorPane;
import org.eclipse.ui.internal.EditorSite;
import org.eclipse.ui.internal.presentations.PresentablePart;
import org.eclipse.ui.internal.presentations.defaultpresentation.DefaultTabItem;


/**
 * Possibly the biggest hack I've ever laid down, but those fuckers left me no choice
 * when they made the default behavior of navigating beyond the current set of tabs
 * that fucking pop-up editor list and NO WAY TO CHANGE IT!  
 * Fuck!
 */
public abstract class ActivateEditorAction extends DevToolsAction {
  public static class Next extends ActivateEditorAction {
    protected int getIndexToActivate( int selectedIndex, int selectedVisibleIndex, int[] visibleIndices, int lastVisibleIndex, CTabItem[] items ) throws Exception {
      int indexToActivate;
      
      if( selectedVisibleIndex < lastVisibleIndex ) {
        return visibleIndices[ selectedVisibleIndex + 1 ];
      }
      else {
        indexToActivate = selectedIndex + 1;
        
        if( indexToActivate >= visibleIndices.length ) {
          for( int i = 0; i < lastVisibleIndex + 1; i++ ) {
            if( i >= items.length ) 
              continue;
            getEditorPane( items[i] ).setFocus();
          }
          
          indexToActivate = 0;
        }
        return indexToActivate;
      }
    }
  }
  
  public static class Previous extends ActivateEditorAction {
    protected int getIndexToActivate( int selectedIndex, int selectedVisibleIndex, int[] visibleIndices, int lastVisibleIndex, CTabItem[] items ) throws Exception {
      int indexToActivate;
      if( selectedVisibleIndex > 0 ) {
        return visibleIndices[ selectedVisibleIndex - 1 ];
      }
      else {
        indexToActivate = selectedIndex - 1;
        
        // when we cross over from first to last index, need to make all necessary editors visible
        if( indexToActivate < 0 ) {
          for( int i = (visibleIndices.length - lastVisibleIndex - 1); i < visibleIndices.length; i++ ) {
            if( i < 0 ) 
              continue;
            getEditorPane( items[i] ).setFocus();
          }
          
          indexToActivate = visibleIndices.length - 1;
        }
        return indexToActivate;
      }
    }
  }
  
  protected abstract int getIndexToActivate( int selectedIndex, int selectedVisibleIndex, int[] visibleIndeces, int lastVisibleIndex, CTabItem[] items ) throws Exception;
  
  protected void onRun( IAction action ) throws Exception {
    IWorkbenchPage activePage = getWindow().getActivePage();
    IEditorPart activeEditor = activePage.getActiveEditor();
    
    if( activeEditor != null ) {
      EditorPane editorToActivate = getEditorToActivate( activeEditor );
      String editorTitleToActivate = editorToActivate.getPartReference().getTitle();
      IEditorReference[] editors = activePage.getEditorReferences();
      
      for( int i = 0; i < editors.length; i++ ) {
        if( editorTitleToActivate.equals( editors[i].getTitle() ) ) {
          IEditorPart editorPartToActivate = editors[i].getEditor( true );
          EditorSite site = (EditorSite) editorPartToActivate.getEditorSite();
          
          if( editorToActivate.equals( site.getPane() ) ) {
            activePage.activate( editorPartToActivate );
            return;
          }
        }
      }
    }
  }
  
  private EditorPane getEditorToActivate( IEditorPart activeEditor ) throws Exception {
    EditorSite site = (EditorSite) activeEditor.getSite();
    EditorPane pane = (EditorPane) site.getPane();
    
    CTabFolder folder = (CTabFolder) pane.getWorkbook().getControl();
    CTabItem[] items = folder.getItems();
    int selectedIndex = folder.getSelectionIndex();
    
    int[] visible = new int[items.length];
    int selectedVisibleIndex = -1;
    int lastVisibleIndex = -1;
    for( int i = 0; i < items.length; i++ ) {
      if( items[i].isShowing() ) {
        ++lastVisibleIndex;
        
        if( i == selectedIndex ) 
          selectedVisibleIndex = lastVisibleIndex;
        
        visible[lastVisibleIndex] = i;
      }
    }
    
    if( selectedVisibleIndex < 0 )
      return null;
    
    int indexToActivate = getIndexToActivate( selectedIndex, selectedVisibleIndex, visible, lastVisibleIndex, items );
    CTabItem item = folder.getItem( indexToActivate );
    
    return getEditorPane( item );
  }
  
  protected EditorPane getEditorPane( CTabItem item ) throws Exception {
    DefaultTabItem tabItem = (DefaultTabItem) item.getData();
    PresentablePart presentable = (PresentablePart) tabItem.getData();
    return (EditorPane) PluginSupport.getInaccessibleValue( presentable, "part" );
  }
}
