/*
 * Created on Feb 6, 2005
 * @author mike
 */
package org.easyexplore.actions;

import org.easyexplore.EasyExplorePlugin;
import org.easyexplore.preferences.EasyExplorePreferencePage;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import java.io.File;
import java.text.MessageFormat;


public abstract class ActionExecutor {
  public static final ActionExecutor COMMAND = new ActionExecutor() {
    public String getTarget() {
      // TODO: error checking?
      return EasyExplorePlugin.getPreferenceString( EasyExplorePreferencePage.P_CMD_TARGET );
    }
  };
  
  public static final ActionExecutor EXPLORE = new ActionExecutor() {
    public String getTarget() {
      String target = EasyExplorePlugin.getPreferenceString( EasyExplorePreferencePage.P_EXPLORE_TARGET );
      
      if ( target.indexOf("{0}") == -1 ) {
        target = target.trim() + " {0}";
      }
      return target;
    }
  };
  
  
  protected abstract String getTarget();
   
  public void execute( Object selection ) {
    File directory = null;
    if ( selection instanceof IResource ) {
      directory= new File(((IResource)selection).getLocation().toOSString());
    } else if ( selection instanceof File ) {
      directory = (File) selection;
    } 
    if ( selection instanceof IFile ) {
      directory = directory.getParentFile();
    }     
    if ( selection instanceof File ) {
      directory = directory.getParentFile();
    } 
    
    if( directory != null ) {
      String executable = MessageFormat.format(getTarget(), directory.toString());
      
      try {
        Runtime.getRuntime().exec( executable );
      }
      catch( Exception ex ) {
        EasyExplorePlugin.log( ex );
      }
    }
  }
}
