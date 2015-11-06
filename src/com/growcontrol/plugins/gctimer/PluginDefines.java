package com.growcontrol.plugins.gctimer;

import com.poixson.commonjava.Utils.utilsDirFile;


public final class PluginDefines {
	private PluginDefines() {}


	// plugin config
	public static final String CONFIG_PATH = utilsDirFile.mergePaths("plugins", "gcTimer");
	public static final String CONFIG_FILE = "gcTimer.yml";
	// config keys
	// timers
	public static final String CONFIG_DEVICES  = "Devices";
	public static final String CONFIG_DEVICE_NAME    = "Name";
	public static final String CONFIG_DEVICE_TITLE   = "Title";
	public static final String CONFIG_DEVICE_TYPE    = "Type";
	public static final String CONFIG_DEVICE_ENABLED = "Enabled";
	public static final String CONFIG_DEVICE_INITIAL = "Initial";
	public static final String CONFIG_DEVICE_OUTPUT  = "Output";
	public static final String CONFIG_DEVICE_RANGE   = "Range";
	// tracks
	public static final String CONFIG_DEVICE_TRACKS  = "Tracks";
	// cron
	public static final String CONFIG_DEVICE_TRACK_CRON_PATTERN  = "Pattern";
	public static final String CONFIG_DEVICE_TRACK_CRON_DURATION = "Duration";
	// span
	public static final String CONFIG_DEVICE_TRACK_SPAN_ON       = "Time On";
	public static final String CONFIG_DEVICE_TRACK_SPAN_OFF      = "Time Off";
	// tick
	public static final String CONFIG_DEVICE_TRACK_TICK_ON       = "Tick On";
	public static final String CONFIG_DEVICE_TRACK_TICK_OFF      = "Tick Off";


}
