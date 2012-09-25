/*
 * Created on Feb 17, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.WorkbenchPage;

/**
 * Possibly the biggest hack I've ever laid down, but those fuckers left me no choice when they made the default behavior of navigating
 * beyond the current set of tabs that fucking pop-up editor list and NO WAY TO CHANGE IT! Fuck!
 */
public abstract class ActivateEditorAction extends DevToolsAction {
	
	private static final class EditorManager {
		
		private IWorkbenchPage activePage;
		private MPart[] editorParts;
		
		public EditorManager(IWorkbenchWindow window) {
			activePage = window.getActivePage();
			initEditorParts(window);
		}
		
		public MPart[] getEditorParts() {
			return editorParts;
		}
		
		public IEditorReference getEditorReferenceForIndex(int index) {
			if ((index >= 0) && (index < editorParts.length)) {
				String editorTitle = editorParts[index].getLabel();
				if (editorTitle != null) {
					for (IEditorReference editorReference : activePage.getEditorReferences()) {
						if (editorTitle.equals(editorReference.getTitle())) {
							return editorReference;
						}
					}
				}
			}
			return null;
		}

		public int getIndexForTitle(String title) {
			if (title == null) {
				return -1;
			}
			
			for (int i = 0; i < editorParts.length; i++) {
				if (title.equals(editorParts[i].getLabel())) {
					return i;
				}
			}
			return -1;
		}
		
		private void initEditorParts(IWorkbenchWindow window) {
			List<MPart> editorPartList = new ArrayList<MPart>();
			WorkbenchPage page = (WorkbenchPage) window.getActivePage();
			
			if (page != null) {
				MUIElement area = page.findSharedArea();
				if (area instanceof MPlaceholder) {
					area = ((MPlaceholder) area).getRef();
				}

				MPartStack activeStack = getActiveStack(area);
				if (activeStack != null) {
					for (MStackElement element : activeStack.getChildren()) {
						editorPartList.add((MPart) element);
					}
				}
			}
			editorParts = editorPartList.toArray(new MPart[0]);
		}

		private MPartStack getActiveStack(Object element) {
			if (element instanceof MPartStack) {
				return (MPartStack) element;
			} else if (element instanceof MElementContainer<?>) {
				return getActiveStack(((MElementContainer<?>) element).getSelectedElement());
			}
			return null;
		}
		
	}

	
	public static class Next extends ActivateEditorAction {
		protected int getIndexToActivate(int selectedIndex, MPart[] editors) throws Exception {
			int indexToActivate = selectedIndex + 1;
			
			if (indexToActivate >= editors.length) {
				indexToActivate = 0;
			}
			return indexToActivate;
		}
	}

	public static class Previous extends ActivateEditorAction {
		protected int getIndexToActivate(int selectedIndex, MPart[] editors) throws Exception {
			int indexToActivate = selectedIndex - 1;
			
			if (indexToActivate < 0) {
				indexToActivate = editors.length - 1;
			}
			return indexToActivate;
		}
	}

	protected abstract int getIndexToActivate(int selectedIndex, MPart[] editors) throws Exception;
		
	protected void onRun(IAction action) throws Exception {
		IWorkbenchPage activePage = getWindow().getActivePage();
		IEditorPart activeEditor = activePage.getActiveEditor();
		
		if (activeEditor != null) {
			EditorManager editorManager = new EditorManager(getWindow());
			
			int activeEditorIndex = editorManager.getIndexForTitle(activeEditor.getTitle());
			int indexToActivate = getIndexToActivate(activeEditorIndex, editorManager.getEditorParts());
			IEditorReference editorReference = editorManager.getEditorReferenceForIndex(indexToActivate);
			if (editorReference != null) {
				IEditorPart editor = editorReference.getEditor(true);
				activePage.activate(editor);
			}
		}
	}
	
}
