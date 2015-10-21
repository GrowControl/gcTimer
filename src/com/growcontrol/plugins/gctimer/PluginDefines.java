package com.growcontrol.plugins.gctimer;

import com.poixson.commonjava.Utils.utilsDirFile;


public final class PluginDefines {
	private PluginDefines() {}


	// plugin config
	public static final String CONFIG_PATH = utilsDirFile.mergePaths("plugins", "gcTimer");
	public static final String CONFIG_FILE = "gcTimer.yml";
	// config keys
	// timers
	public static final String CONFIG_TIMERS  = "Timers";
	public static final String CONFIG_TIMER_ENABLED = "Enabled";


}
