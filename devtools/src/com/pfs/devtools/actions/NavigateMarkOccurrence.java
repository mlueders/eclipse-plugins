/*
 * Created on Feb 27, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.jface.text.Position;

import java.util.List;

public abstract class NavigateMarkOccurrence extends NavigateAnnotationAction {

	private static final String ANNOTATION_TYPE = "org.eclipse.jdt.ui.occurrences";

	public static class NextMarkOccurrence extends NavigateMarkOccurrence {

		protected Position getPositionToNavigate(List positions, int index) {
			index++;

			return index >= positions.size() ? null : (Position) positions.get(index);
		}
	}

	public static class PreviousMarkOccurrence extends NavigateMarkOccurrence {

		protected Position getPositionToNavigate(List positions, int index) {
			index--;

			return index < 0 ? null : (Position) positions.get(index);
		}
	}

	public NavigateMarkOccurrence() {
		super(ANNOTATION_TYPE);
	}
}
