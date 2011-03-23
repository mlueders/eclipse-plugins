package com.pfs.launcher.launchers;

import com.pfs.launcher.QuickLaunchPlugin;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.junit.launcher.JUnitLaunchShortcut;
import org.eclipse.jface.viewers.StructuredSelection;


/**
 * Launches a JUnit test.
 * - if the cursor is w/in a test method, runs that method
 * - if outside a test method, runs all tests
 * - if the class declares a static method named 'suite' which returns 
 *   junit.framework.Test, executes the tests returned by the suite method.
 */
public class JUnitLauncher extends AbstractLauncher {
  private IMethod _method;
  
  public JUnitLauncher( LauncherConfig config ) { 
    super( config );
    _method = getTestMethod( config.getCompilationUnit(), config.getPosition() );
  }
  
  private IMethod getTestMethod( ICompilationUnit unit, int position ) {
    try {
      IJavaElement element = unit.getElementAt( position );
      if( (element == null) || (element.getElementType() != IJavaElement.METHOD) )
        return null;
      
      IMethod method = (IMethod) element;
      return Flags.isStatic( method.getFlags() ) ? null : method;
    }
    catch( JavaModelException ex ) {
      QuickLaunchPlugin.getDefault().logError( ex );
      return null;
    }
  }

  public void run() {
    JUnitLaunchShortcut shortcut = new JUnitLaunchShortcut();
    
    if( _method == null ) {
      shortcut.launch( getEditor(), getLaunchMode() );
    }
    else {
      StructuredSelection selection = new StructuredSelection( _method );
      
      shortcut.launch( selection, getLaunchMode() );
    }
  }
}
