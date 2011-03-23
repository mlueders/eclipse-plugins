package com.pfs.devtools.properties;

import com.pfs.devtools.DevToolsPlugin;
import com.pfs.devtools.preferences.PreferenceConstants;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class DevToolsPropertyPage extends PropertyPage {
	/**
	 * The element.
	 */
	private IAdaptable element;
	private Text defaultVmArgsText;

	public DevToolsPropertyPage() {
	}

	public IAdaptable getElement() {
		return element;
	}

	public void setElement(IAdaptable element) {
		this.element = element;
	}

	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		composite.setLayoutData(data);

		Label vmArgsLabel = new Label(composite, SWT.NONE);
		vmArgsLabel.setText("D&efault VM Args");

		data.grabExcessHorizontalSpace = true;
		// Owner text field
		defaultVmArgsText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		defaultVmArgsText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		try {
			String text = ((IResource) getElement()).getPersistentProperty(PropertyConstants.DEFAULT_VM_ARGS_PROPERTY);
			defaultVmArgsText.setText((text != null) ? text : "");
		} catch (CoreException e) {
			defaultVmArgsText.setText("");
		}

		return composite;
	}

	protected void performDefaults() {
		String initialValue = DevToolsPlugin.getDefault().getStringPreference(PreferenceConstants.INITIAL_DEFAULT_VM_ARGS);

		defaultVmArgsText.setText(initialValue);
	}

	public boolean performOk() {
		// store the value in the owner text field
		try {
			String defaultVmArgs = defaultVmArgsText.getText();
			((IResource) getElement()).setPersistentProperty(PropertyConstants.DEFAULT_VM_ARGS_PROPERTY, defaultVmArgs);
		} catch (CoreException e) {
			return false;
		}
		return true;
	}
}