/*
 * Created on Feb 5, 2005
 * @author mike
 */
package com.pfs.base.preferences;


public class PreferenceNameFactory {
  private String prefix_;
  
  public PreferenceNameFactory( Class type ) {
    prefix_ = type.getName();
  }
  
  public String createPreferenceName( String preference ) {
    return prefix_ + "." + preference;
  }
}
