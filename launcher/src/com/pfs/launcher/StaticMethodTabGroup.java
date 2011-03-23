/*
 * Created on Feb 8, 2005
 * @author mike
 */
package com.pfs.launcher;

import org.eclipse.debug.ui.*;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaClasspathTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaJRETab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab;


public class StaticMethodTabGroup extends AbstractLaunchConfigurationTabGroup {

  public void createTabs( ILaunchConfigurationDialog dialog, String mode ) {
    ILaunchConfigurationTab[] tabs= new ILaunchConfigurationTab[] {
        new JavaMainTab(),
        new JavaClasspathTab(),
        new JavaArgumentsTab(),
        new JavaJRETab(),
        new SourceLookupTab(),
        new EnvironmentTab(),
        new CommonTab()
      };
      setTabs(tabs);
  }
  
  /*
  private static class JavaStaticMethodMainTab extends JavaMainTab {
    private Text _methodNameText;
    
    public void createControl( Composite parent ) {
      super.createControl( parent );
      
      parent = (Composite) getControl();
      
      Group group= new Group( parent, SWT.NONE );
      group.setText( "Method name" );
      group.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
      
      _methodNameText = new Text( group, SWT.SINGLE | SWT.BORDER);
      _methodNameText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
      _methodNameText.addModifyListener( new ModifyListener() {
        public void modifyText(ModifyEvent e) {
          updateLaunchConfigurationDialog();
        }
      });
    }
    
    public void initializeFrom( ILaunchConfiguration config ) {
      super.initializeFrom( config );
      
      updateMethodNameFromConfig( config );
    }
    
    private void updateMethodNameFromConfig( ILaunchConfiguration config ) {
      String projectName;
      try {
        projectName = config.getAttribute( StaticMethodLaunchConfiguration.ID_METHOD_NAME, EMPTY_STRING );
      }
      catch( CoreException ce ) {
        LauncherPlugin.getDefault().logInfo( ce );
        projectName = "";
      }
      _methodNameText.setText( projectName );
    }
    
    public void performApply(ILaunchConfigurationWorkingCopy config) {
      super.performApply( config );
      
      config.setAttribute( StaticMethodLaunchConfiguration.ID_METHOD_NAME, _methodNameText.getText() );
    }
  }
  */
}
