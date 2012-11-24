package com.pfs.devtools.util;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

public class PartListenerAdapter implements IPartListener {

	@Override
	public void partActivated(IWorkbenchPart arg0) {}

	@Override
	public void partBroughtToTop(IWorkbenchPart arg0) {}

	@Override
	public void partClosed(IWorkbenchPart arg0) {}

	@Override
	public void partDeactivated(IWorkbenchPart arg0) {}

	@Override
	public void partOpened(IWorkbenchPart arg0) {}

}
