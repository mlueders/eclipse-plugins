/*
 * Created on Feb 5, 2005
 * @author mike
 */
package com.pfs.launcher;


public interface ILauncher {
  String getLauncherName();
  
  String getLaunchMode();
  
  boolean supportsLaunchMode();
  
  void run();
}
