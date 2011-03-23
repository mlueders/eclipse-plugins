/*
 * Created on Feb 3, 2005
 * @author mike
 */
package com.pfs.base.actions;

import com.pfs.base.BasePlugin;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class BaseSelectionListener implements SelectionListener {
	private BasePlugin plugin;

	/**
	 * @param plugin The plugin to notify if an error occurs during selection. If null, any errors will simply be
	 *        ignored.
	 */
	public BaseSelectionListener(BasePlugin plugin) {
		this.plugin = plugin;
	}

	protected void onSelection() throws Exception {
	}

	protected void showError(Throwable error) {
		if (plugin != null) {
			plugin.showError(error);
		}
	}

	public void widgetSelected(SelectionEvent e) {
		try {
			onSelection();
		} catch (Exception ex) {
			showError(ex);
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
