/*
 * Created on Feb 16, 2005
 * @author mike
 */
package com.pfs.lueders;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.internal.junit.ui.TestRunnerViewPart;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;

import com.pfs.launcher.QuickLaunchPlugin;
//import org.eclipse.ui.views.editorlist.IEditorListConstants;


public class MyPerspectiveFactory implements IPerspectiveFactory {
  
  /**
   * Constructs a new Default layout engine.
   */
  public MyPerspectiveFactory() {
    super();
  }

  public void createInitialLayout(IPageLayout layout) {
    String editorArea = layout.getEditorArea();
    
    IFolderLayout leftFolder = layout.createFolder("left", IPageLayout.LEFT, (float)0.25, editorArea);
    leftFolder.addView(JavaUI.ID_PACKAGES);
    leftFolder.addView(JavaUI.ID_TYPE_HIERARCHY);
    leftFolder.addView(TestRunnerViewPart.NAME);
    
    IFolderLayout rightFolder = layout.createFolder("right",  IPageLayout.RIGHT, (float)0.25, editorArea);
    rightFolder.addView(IPageLayout.ID_OUTLINE);
    rightFolder.addView(IPageLayout.ID_BOOKMARKS);
    rightFolder.addView("org.eclipse.ant.ui.views.AntView");
    
    IFolderLayout bottomFolder = layout.createFolder( "bottom", IPageLayout.BOTTOM, (float)0.25, editorArea ); 
    bottomFolder.addView(IPageLayout.ID_PROBLEM_VIEW);
    bottomFolder.addView(IConsoleConstants.ID_CONSOLE_VIEW);
    bottomFolder.addView(NewSearchUI.SEARCH_VIEW_ID);
    bottomFolder.addView("org.eclipse.pde.runtime.LogView");
    bottomFolder.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);
    
    layout.addActionSet( IDebugUIConstants.LAUNCH_ACTION_SET );
    layout.addActionSet( QuickLaunchPlugin.ID_ACTION_SET );
    layout.addActionSet( IPageLayout.ID_NAVIGATE_ACTION_SET );
    
    // add shortcuts for the Window->Show View menu item
    layout.addShowViewShortcut( NewSearchUI.SEARCH_VIEW_ID );
    layout.addShowViewShortcut( IConsoleConstants.ID_CONSOLE_VIEW );
    layout.addShowViewShortcut( IPageLayout.ID_OUTLINE );
    layout.addShowViewShortcut( IPageLayout.ID_PROBLEM_VIEW );
    layout.addShowViewShortcut( "org.eclipse.pde.runtime.LogView" );
    
    // add shortcuts for the Window->Open Perspective menu item
    layout.addPerspectiveShortcut( "org.eclipse.jdt.ui.JavaPerspective" );
    layout.addPerspectiveShortcut( "org.eclipse.debug.ui.DebugPerspective" );
    layout.addPerspectiveShortcut( "org.eclipse.team.ui.TeamSynchronizingPerspective" );
    layout.addPerspectiveShortcut( "org.eclipse.team.cvs.ui.cvsPerspective" );
    
    // new actions - Java project creation wizard
    layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewPackageCreationWizard"); 
    layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewClassCreationWizard"); 
    layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewInterfaceCreationWizard"); 
    layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewSourceFolderCreationWizard");   
    layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewSnippetFileCreationWizard"); 
    layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
    layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");
  }
}
