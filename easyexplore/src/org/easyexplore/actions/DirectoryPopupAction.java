package org.easyexplore.actions;

import org.easyexplore.EasyExplorePlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import java.io.File;

public class DirectoryPopupAction implements IObjectActionDelegate {

	private ActionExecutor executor = null;
	private Object selected = null;
	private Class selectedClass = null;
	private String failureMessage = null;

	/**
	 * Constructor for EasyCmdAction.
	 */
	public DirectoryPopupAction(ActionExecutor executor, String failureMessage) {
		this.executor = executor;
		this.failureMessage = failureMessage;
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (selected != null) {
			executor.execute(selected);
		} else {
			String failureMsg = failureMessage + " " + selectedClass;

			MessageDialog.openInformation(new Shell(), "Easy Explorer", failureMsg);
			EasyExplorePlugin.log(failureMsg);
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IAdaptable adaptable = null;
		this.selected = null;
		if (selection instanceof IStructuredSelection) {
			adaptable = (IAdaptable) ((IStructuredSelection) selection).getFirstElement();
			if (adaptable != null) {
				this.selectedClass = adaptable.getClass();
				if (adaptable instanceof IResource) {
					this.selected = (IResource) adaptable;
				} else if (adaptable instanceof PackageFragment
						&& ((PackageFragment) adaptable).getPackageFragmentRoot() instanceof JarPackageFragmentRoot) {
					this.selected = getJarFile(((PackageFragment) adaptable).getPackageFragmentRoot());
				} else if (adaptable instanceof JarPackageFragmentRoot) {
					this.selected = getJarFile(adaptable);
				} else {
					this.selected = (IResource) adaptable.getAdapter(IResource.class);
				}
			}
		}
	}

	protected File getJarFile(IAdaptable adaptable) {
		JarPackageFragmentRoot jpfr = (JarPackageFragmentRoot) adaptable;
		File selected = (File) jpfr.getPath().makeAbsolute().toFile();
		if (!((File) selected).exists()) {
			File projectFile = new File(jpfr.getJavaProject().getProject().getLocation().toOSString());
			selected = new File(projectFile.getParent() + selected.toString());
		}
		return selected;
	}

}
