package com.pfs.lueders;

import com.pfs.base.BasePlugin;

/**
 * The main plugin class to be used in the desktop.
 */
public class LuedersPlugin extends BasePlugin {
  
  public static final String PLUGIN_ID = "com.pfs.lueders";
  // The shared instance.
  private static LuedersPlugin plugin;

  /**
   * Returns the shared instance.
   */
  public static LuedersPlugin getDefault() {
    return plugin;
  }

  /**
   * The constructor.
   */
  public LuedersPlugin() {
    super();
    plugin = this;
  }
  
  @Override
  public void onStop() {
    plugin = null;
  }

  @Override
  public String getPluginId() {
    return PLUGIN_ID;
  }
}
