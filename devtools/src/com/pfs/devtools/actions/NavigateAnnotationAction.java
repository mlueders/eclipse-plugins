/*
 * Created on Feb 27, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class NavigateAnnotationAction extends DevToolsAction {

	private static final Comparator<Position> POSITION_COMPARATOR = new Comparator<Position>() {
		public int compare(Position p1, Position p2) {
			if (p1.getOffset() < p2.getOffset()) {
				return -1;
			} else if (p1.getOffset() > p2.getOffset()) {
				return 1;
			} else {
				return 0;
			}
		}
	};

	protected abstract Position getPositionToNavigate(List<Position> positions, int currentIndex);

	private String annotationType;

	public NavigateAnnotationAction(String annotationType) {
		this.annotationType = annotationType;
	}

	private List<Position> getAnnotationPositions(String annotationType, IAnnotationModel model) {
		ArrayList<Position> positions = new ArrayList<Position>();
		for (Iterator<?> i = model.getAnnotationIterator(); i.hasNext();) {
			Object item = i.next();

			if (item instanceof Annotation) {
				Annotation annotation = (Annotation) item;

				if (annotationType.equals(annotation.getType())) {
					Position position = model.getPosition(annotation);
					positions.add(position);
				}
			}
		}

		return positions;
	}

	protected void onRun(IAction action) throws Exception {
		IEditorPart editor = getActiveEditor();

		if (editor instanceof ITextEditor) {
			ITextEditor tEditor = (ITextEditor) editor;
			IDocumentProvider provider = tEditor.getDocumentProvider();

			IAnnotationModel model = provider.getAnnotationModel(editor.getEditorInput());
			List<Position> positions = getAnnotationPositions(annotationType, model);
			Collections.sort(positions, POSITION_COMPARATOR);

			ISelection selection = tEditor.getSelectionProvider().getSelection();
			if (selection instanceof ITextSelection) {
				onNavigate(tEditor, (ITextSelection) selection, positions);
			}
		}
	}

	private void onNavigate(ITextEditor editor, ITextSelection selection, List<Position> positions) {
		ITextSelection tSelection = (ITextSelection) selection;
		int rangeOffset = tSelection.getOffset();
		int rangeLength = 1;
		// takes care of the case when the cursor is just of the selection
		if (rangeOffset > 0) {
			rangeOffset--;
			rangeLength++;
		}

		for (int i = 0; i < positions.size(); i++) {
			Position position = (Position) positions.get(i);

			if (position.overlapsWith(rangeOffset, rangeLength)) {
				onNavigate(editor, positions, i);
				break;
			}
		}
	}

	private void onNavigate(ITextEditor editor, List<Position> positions, int currentIndex) {
		Position toNavigate = getPositionToNavigate(positions, currentIndex);

		if (toNavigate != null) {
			editor.setHighlightRange(toNavigate.getOffset(), toNavigate.getLength(), true);
		}
	}
}
