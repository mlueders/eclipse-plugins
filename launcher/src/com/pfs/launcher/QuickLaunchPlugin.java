package com.pfs.launcher;

import com.pfs.base.BasePlugin;

import org.eclipse.ui.IStartup;

/**
 * The main plugin class to be used in the desktop.
 */
public class QuickLaunchPlugin extends BasePlugin implements IStartup {

  public static final String PLUGIN_ID = "com.pfs.launcher";
  public static final String PLUGIN_JAR_NAME = "com.pfs.launcher.jar";
  /**
   * The id of the QuickLaunch action set (value
   * <code>"com.pfs.launcher.QuickLaunchActionSet"</code>).
   */
  public static final String ID_ACTION_SET = PLUGIN_ID + ".QuickLaunchActionSet";
  private static QuickLaunchPlugin plugin;

  public static QuickLaunchPlugin getDefault() {
    return plugin;
  }

  public QuickLaunchPlugin() {
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

  public void earlyStartup() {
  }
}
