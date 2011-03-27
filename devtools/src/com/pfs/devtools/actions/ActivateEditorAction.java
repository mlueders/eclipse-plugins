/*
 * Created on Feb 17, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

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

import com.pfs.base.PluginSupport;

/**
 * Possibly the biggest hack I've ever laid down, but those fuckers left me no choice when they made the default behavior of navigating
 * beyond the current set of tabs that fucking pop-up editor list and NO WAY TO CHANGE IT! Fuck!
 */
public abstract class ActivateEditorAction extends DevToolsAction {
	public static class Next extends ActivateEditorAction {
		private int getMaxVisibleIndex(CTabItem[] items) {
			for (int i = items.length - 1; i >= 0; i--) {
				if (items[i].isShowing()) {
					return i;
				}
			}
			return -1;
		}
		
		protected void setFocusIfTransition(int indexToActivate, CTabItem[] items) throws Exception {
			if (indexToActivate == 0) {
				for (int i = indexToActivate; i < items.length; i++) {
					getEditorPane(items[i]).setFocus();
					int maxVisibleIndex = getMaxVisibleIndex(items);
					
					if (i == maxVisibleIndex) {
						break;
					}
				}
			}
		}
		
		protected int getIndexToActivate(int selectedIndex, CTabItem[] items) throws Exception {
			int indexToActivate = selectedIndex + 1;
			
			if (indexToActivate >= items.length) {
				indexToActivate = 0;
			}
			return indexToActivate;
		}
	}

	public static class Previous extends ActivateEditorAction {
		private int getMinVisibleIndex(CTabItem[] items) {
			for (int i = 0; i < items.length; i++) {
				if (items[i].isShowing()) {
					return i;
				}
			}
			return -1;
		}
		
		protected void setFocusIfTransition(int indexToActivate, CTabItem[] items) throws Exception {
			if (indexToActivate == items.length - 1) {
				for (int i = items.length - 1; i >= 0; i--) {
					getEditorPane(items[i]).setFocus();
					int minVisibleIndex = getMinVisibleIndex(items);
					
					if (i == minVisibleIndex) {
						break;
					}
				}
			}
		}
		
		protected int getIndexToActivate(int selectedIndex, CTabItem[] items) throws Exception {
			int indexToActivate = selectedIndex - 1;
			
			if (indexToActivate < 0) {
				indexToActivate = items.length - 1;
			}
			return indexToActivate;
		}
	}

	protected abstract int getIndexToActivate(int selectedIndex, CTabItem[] items) throws Exception;
	protected abstract void setFocusIfTransition(int indexToActivate, CTabItem[] items) throws Exception;
		
	protected void onRun(IAction action) throws Exception {
		IWorkbenchPage activePage = getWindow().getActivePage();
		IEditorPart activeEditor = activePage.getActiveEditor();

		if (activeEditor != null) {
			EditorPane editorToActivate = getEditorToActivate(activeEditor);
			String editorTitleToActivate = editorToActivate.getPartReference().getTitle();
			IEditorReference[] editors = activePage.getEditorReferences();

			for (int i = 0; i < editors.length; i++) {
				if (editorTitleToActivate.equals(editors[i].getTitle())) {
					IEditorPart editorPartToActivate = editors[i].getEditor(true);
					EditorSite site = (EditorSite) editorPartToActivate.getEditorSite();
					
					if (editorToActivate.equals(site.getPane())) {
						activePage.activate(editorPartToActivate);
						return;
					}
				}
			}
		}
	}
	
	private EditorPane getEditorToActivate(IEditorPart activeEditor) throws Exception {
		EditorSite site = (EditorSite) activeEditor.getSite();
		EditorPane pane = (EditorPane) site.getPane();

		CTabFolder folder = (CTabFolder) pane.getWorkbook().getControl();
		int selectedIndex = folder.getSelectionIndex();
		CTabItem[] items = folder.getItems();
		
		if (selectedIndex < 0) {
			return null;
		}
		
		int indexToActivate = getIndexToActivate(selectedIndex, items);
		setFocusIfTransition(indexToActivate, items);
		CTabItem item = folder.getItem(indexToActivate);
		return getEditorPane(item);
	}
	
	protected EditorPane getEditorPane(CTabItem item) throws Exception {
		DefaultTabItem tabItem = (DefaultTabItem) item.getData();
		PresentablePart presentable = (PresentablePart) tabItem.getData();
		return (EditorPane) PluginSupport.getInaccessibleValue(presentable, "part");
	}
}
