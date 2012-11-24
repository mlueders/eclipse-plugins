package com.pfs.devtools.util;

import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;

public class WindowListenerAdapter implements IWindowListener {

	@Override
	public void windowActivated(IWorkbenchWindow window) {}

	@Override
	public void windowClosed(IWorkbenchWindow window) {}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {}

	@Override
	public void windowOpened(IWorkbenchWindow window) {}

}
