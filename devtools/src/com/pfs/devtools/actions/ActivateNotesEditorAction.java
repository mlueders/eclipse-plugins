package com.pfs.devtools.actions;

import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.part.FileEditorInput;

import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Opens (creating if necessary) a 'Notes.txt' file in the root of the active editor's project.
 */
public class ActivateNotesEditorAction extends DevToolsAction {
	private static final String NOTES_FILENAME = "Notes";

	protected void onRun(IAction action) throws Exception {
		IEditorInput input = getActiveEditor().getEditorInput();
		IFile file = (IFile) input.getAdapter(IFile.class);

		if (file != null) {
			activateNotesEditor(file.getProject());
		} else {
			showInfo("Unable to resolve parent project of active editor");
		}
	}

	private void activateNotesEditor(IProject project) throws CoreException {
		IFile notes = project.getFile(NOTES_FILENAME + ".txt");

		if (notes.exists() == false) {
			try {
				notes.create(new ByteArrayInputStream(new byte[0]), false, null);
			}
			// if the file exists w/ a different case, this exception will be something like
			// "resource exists with a different case: /<project>/<filename>."
			// which we can strip the actual filename from
			catch (ResourceException ex) {
				Pattern pattern = Pattern.compile(NOTES_FILENAME + "\\.txt", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(ex.getMessage());

				if (matcher.find()) {
					notes = project.getFile(matcher.group());
				}
			}
		}

		if (notes.exists()) {
			IEditorInput notesInput = new FileEditorInput(notes);

			getWindow().getActivePage().openEditor(notesInput, EditorsUI.DEFAULT_TEXT_EDITOR_ID, true);
		}
	}
}
