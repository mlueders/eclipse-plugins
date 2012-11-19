package com.pfs.devtools.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.keys.KeyBinding;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.keys.BindingService;
import org.eclipse.ui.keys.IBindingService;

import com.pfs.devtools.DevToolsPlugin;

public class SimpleBindingManager {
	
	private Map<?,?> EMPTY_PARAMETER_MAP = new HashMap<Object,Object>();
	
	private String schemeId;
	private ICommandService commandService;
	private IBindingService bindingService;
	private BindingManager bindingManager;
	
	public SimpleBindingManager(String schemeId) throws Exception {
		this.schemeId = schemeId;
		this.commandService = (ICommandService) DevToolsPlugin.getDefault().getWorkbench().getService(ICommandService.class);
		this.bindingService = (IBindingService) DevToolsPlugin.getDefault().getWorkbench().getService(IBindingService.class);
		this.bindingManager = ((BindingService) bindingService).getBindingManager();
	}
	
	public void makeSchemeActive() throws Exception {
		Scheme scheme = bindingService.getScheme(schemeId);
		bindingManager.setActiveScheme(scheme);
	}
	
	public void addUserTextEditorBinding(String keySequenceString, String commandId) throws Exception {
		addUserBinding(keySequenceString, commandId, "org.eclipse.ui.textEditorScope");
	}
	
	public void addUserBinding(String keySequenceString, String commandId, String contextId) throws Exception {
		KeyBinding keyBinding = createUserKeyBinding(keySequenceString, commandId, contextId);
		bindingManager.addBinding(keyBinding);
	}
	
	private KeyBinding createUserKeyBinding(String keySequenceString, String commandId, String contextId) throws Exception {
		KeySequence keySequence = KeySequence.getInstance(keySequenceString);
		Command command = commandService.getCommand(commandId);
		ParameterizedCommand parameterizedCommand = ParameterizedCommand.generateCommand(command, EMPTY_PARAMETER_MAP);
		return new KeyBinding(keySequence, parameterizedCommand, schemeId, contextId, null, null, null, KeyBinding.USER);
	}
}