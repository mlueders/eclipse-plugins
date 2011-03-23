/*
 * Created on Feb 3, 2005
 * @author mike
 */
package com.pfs.launcher;

import com.pfs.base.PluginSupport;
import com.pfs.launcher.launchers.AntLauncher;
import com.pfs.launcher.launchers.JUnitLauncher;
import com.pfs.launcher.launchers.LauncherConfig;
import com.pfs.launcher.launchers.MainLauncher;
import com.pfs.launcher.launchers.StaticMethodLauncher;

import org.eclipse.ant.internal.ui.editor.text.AntEditorDocumentProvider;
import org.eclipse.ant.internal.ui.model.AntElementNode;
import org.eclipse.ant.internal.ui.model.AntModel;
import org.eclipse.ant.internal.ui.model.AntProjectNode;
import org.eclipse.ant.internal.ui.model.AntTargetNode;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.junit.util.TestSearchEngine;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;


public class LauncherFactory {

  public static ILauncher createLauncher( ITextEditor editor, String launchMode ) {
    LauncherConfig config = new LauncherConfig();
    config.setEditor( editor );
    config.setLaunchMode( launchMode );
    config.setPosition( PluginSupport.getCursorPosition( config.getEditor() ) );
    
    if( isJavaEditor( editor ) ) {
      ICompilationUnit unit = PluginSupport.getCompilationUnit( config.getEditor() );
      String launcherName = getLauncherName ( unit );
      
      config.setCompilationUnit( unit );
      config.setLauncherName( launcherName );
      
      return createJavaLauncher( config );
    }
    else {
      return createAntLauncher( config );
    }
  }
  
  private static ILauncher createJavaLauncher( LauncherConfig config ) {
    if( isJUnit( config ) ) {
      return new JUnitLauncher( config );
    }
    else {
      String staticMethodName = getStaticMethodName( config );
      
      if( (staticMethodName == null) || "main".equals( staticMethodName ) )
        return new MainLauncher( config );
      else
        return new StaticMethodLauncher( config, staticMethodName );
    }
  }
  
  private static String getLauncherName( ICompilationUnit unit ) {
    String launcherName = unit.getElementName();
    
    return launcherName.substring( 0, launcherName.indexOf( ".java" ) );
  }
  
  private static boolean isJavaEditor( IEditorPart editor ) {
    return editor.getEditorInput().getName().endsWith( ".java" );
  }
  
  private static boolean isJUnit( LauncherConfig config ) {
    try {
      ICompilationUnit unit = config.getCompilationUnit();
      IType type = unit.getTypes()[0];
      
      return TestSearchEngine.isTestOrTestSuite( type );
    } 
    catch( Exception e ) {
      QuickLaunchPlugin.getDefault().logError( e );
      return false;
    }
  }
  
  private static String getStaticMethodName( LauncherConfig config ) {
    try {
      ICompilationUnit unit = config.getCompilationUnit();
      IJavaElement element = unit.getElementAt( config.getPosition() );
      if( (element instanceof IMethod) == false )
        return null;
      
      IMethod method = (IMethod) element;
      if( Flags.isStatic( method.getFlags() ) == false )
        return null;
      
      return method.getElementName();
    }
    catch( Exception e ) {
      QuickLaunchPlugin.getDefault().logError( e );
      return null;
    }
  }
  
  private static AntLauncher createAntLauncher( LauncherConfig config ) {
    AntModel model = LauncherFactory.createAntModel( config.getEditor() );
    
    if( model == null )
      return null;
    
    AntElementNode antNode = model.getNode( config.getPosition(), true );
    String targetName = LauncherFactory.resolveTargetName( antNode );
    AntLauncher launcher = new AntLauncher( config, model.getFile(), targetName );
    
    model.dispose();
    return launcher;
  }
  
  private static String resolveTargetName( AntElementNode node ) {
    for( ; node != null; node = node.getParentNode() ) {
      if( node instanceof AntTargetNode )
        return ((AntTargetNode) node).getTargetName();
      
      if( node instanceof AntProjectNode )
        return "";
    }
    return "";
  }
  
  private static AntModel createAntModel( ITextEditor editor ) {
    AntModelFactory factory = new AntModelFactory();
    
    try {
      return factory.createAndModel( editor.getEditorInput() );
    }
    catch( CoreException ex ) {
      QuickLaunchPlugin.getDefault().showError( ex );
      return null;
    }
  }
  
  static class AntModelFactory extends AntEditorDocumentProvider {
    protected AntModel createAndModel(Object element) throws CoreException {
      FileInfo info = super.createFileInfo(element);
      
      if( info instanceof AntFileInfo )
        return ((AntFileInfo) info).fAntModel;
      return null;
    }
  }
}
