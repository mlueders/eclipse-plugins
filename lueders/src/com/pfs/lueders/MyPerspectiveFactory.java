/*
 * Created on Feb 16, 2005
 * @author mike
 */
package com.pfs.lueders;

import com.pfs.launcher.QuickLaunchPlugin;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.internal.junit.ui.TestRunnerViewPart;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;
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
    
    layout.addFastView( "org.eclipse.ant.ui.views.AntView" );
    layout.addFastView( IPageLayout.ID_BOOKMARKS );
//    layout.addFastView( IEditorListConstants.EDITORLIST_ID );
    layout.addFastView( IPageLayout.ID_OUTLINE );
    layout.addFastView( JavaUI.ID_TYPE_HIERARCHY );
    layout.addFastView( JavaUI.ID_PACKAGES );
    
    IFolderLayout folder = layout.createFolder( "top", IPageLayout.TOP, (float)0.25, editorArea ); 
    folder.addView( IPageLayout.ID_PROBLEM_VIEW );
    folder.addView( IConsoleConstants.ID_CONSOLE_VIEW );
    folder.addPlaceholder( NewSearchUI.SEARCH_VIEW_ID );
    folder.addPlaceholder( IProgressConstants.PROGRESS_VIEW_ID );
    folder.addPlaceholder( "org.eclipse.pde.runtime.LogView" );
        
    IPlaceholderFolderLayout junitFolder = layout.createPlaceholderFolder( "right", IPageLayout.RIGHT, (float)0.75, editorArea );
    junitFolder.addPlaceholder( TestRunnerViewPart.NAME );
    
    layout.addActionSet( IDebugUIConstants.LAUNCH_ACTION_SET );
    layout.addActionSet( QuickLaunchPlugin.ID_ACTION_SET );
    layout.addActionSet( IPageLayout.ID_NAVIGATE_ACTION_SET );
    
    layout.addShowViewShortcut( JavaUI.ID_PACKAGES );
    layout.addShowViewShortcut( JavaUI.ID_TYPE_HIERARCHY );
    layout.addShowViewShortcut( NewSearchUI.SEARCH_VIEW_ID );
    layout.addShowViewShortcut( IConsoleConstants.ID_CONSOLE_VIEW );
    layout.addShowViewShortcut( IPageLayout.ID_OUTLINE );
    layout.addShowViewShortcut( IPageLayout.ID_PROBLEM_VIEW );
    layout.addShowViewShortcut( "org.eclipse.pde.runtime.LogView" );
    
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
