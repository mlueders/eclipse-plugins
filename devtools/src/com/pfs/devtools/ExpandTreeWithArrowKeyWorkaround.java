package com.pfs.devtools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.pfs.devtools.util.PartListenerAdapter;
import com.pfs.devtools.util.WindowListenerAdapter;

public class ExpandTreeWithArrowKeyWorkaround {
	
	public static class WindowListener extends WindowListenerAdapter {
		@Override
		public void windowOpened(IWorkbenchWindow window) {
			window.getActivePage().addPartListener(new PartListener());
		}
	}
	
	private static class PartListener extends PartListenerAdapter {

		@Override
		public void partOpened(IWorkbenchPart part) {
			if (part instanceof PackageExplorerPart) {
				TreeViewer treeViewer = ((PackageExplorerPart) part).getTreeViewer();
				ExpandTreeWithArrowKeyWorkaround workaround = new ExpandTreeWithArrowKeyWorkaround(treeViewer);
				treeViewer.getControl().addKeyListener(workaround.new KeyListener());
			}
		}
	}
	
	private class KeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent keyEvent) {
			// TODO: figure out where right arrow key is defined for eclipse key code
			if (keyEvent.keyCode == SWT.ARROW_RIGHT) {
				onExpandEvent();
			}
		}
	}
	
	
	private static final Integer NO_NON_EXPANDED_ITEMS = Integer.MAX_VALUE;
	
	private TreeViewer treeViewer;
	
	private ExpandTreeWithArrowKeyWorkaround(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}
	
	void onExpandEvent() {
		TreeSelection treeSelection = (TreeSelection) treeViewer.getSelection();
		TreePath[] selectedPaths = treeSelection.getPaths();
		
		if (selectedPaths.length == 1) {
			TreeItem[] selectedTreeItems = treeViewer.getTree().getSelection();
			expandTree(selectedPaths[0], selectedTreeItems[0]);
		}
	}

	private void expandTree(TreePath selectedPath, TreeItem selectedItem) {
		if (selectedItem.getExpanded()) {
			int level = getLevelOfFirstNonExpandedItem(selectedItem);
			treeViewer.expandToLevel(selectedPath, level);
		}
	}
	
	private int getLevelOfFirstNonExpandedItem(TreeItem parentItem) {
		return getLevelOfFirstNonExpandedItem(parentItem, 1);
	}
	
	private int getLevelOfFirstNonExpandedItem(TreeItem parentItem, int parentLevel) {
		TreeItem[] children = parentItem.getItems();
		List<Integer> levelsOfNonExpandedItems = new ArrayList<Integer>();
		int currentLevel = parentLevel + 1;
		
		for (TreeItem child : children) {
			if (isExpandableAndNotExpanded(child)) {
				return currentLevel;
			} else if (isExpandable(child)) {
				Integer levelOfFirstNonExpandedItem = getLevelOfFirstNonExpandedItem(child, currentLevel);
				if (levelOfFirstNonExpandedItem != NO_NON_EXPANDED_ITEMS) {
					levelsOfNonExpandedItems.add(levelOfFirstNonExpandedItem);
				}
			}
		}
		
		if (levelsOfNonExpandedItems.isEmpty()) {
			return NO_NON_EXPANDED_ITEMS;
		} else {
			Collections.sort(levelsOfNonExpandedItems);
			return levelsOfNonExpandedItems.get(0);
		}
	}
	
	private boolean isExpandableAndNotExpanded(TreeItem item) {
		return isExpandable(item) && !item.getExpanded();
	}
	
	private boolean isExpandable(TreeItem item) {
		return item.getItemCount() != 0;
	}
}
