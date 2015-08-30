package com.growcontrol.plugins.gctimer.server;

import com.growcontrol.plugins.gctimer.PluginDefines;
import com.growcontrol.plugins.gctimer.server.commands.Commands;
import com.growcontrol.plugins.gctimer.server.configs.PluginConfig;
import com.growcontrol.server.plugins.gcServerPlugin;
import com.poixson.commonapp.config.xConfigLoader;
import com.poixson.commonjava.xLogger.xLog;


public class gcTimer extends gcServerPlugin {
	public static final String LOG_NAME = "gcTimer";

	private volatile PluginConfig config = null;



	@Override
	protected void onEnable() {
		// load config
		this.config = (PluginConfig) xConfigLoader.Load(
				PluginDefines.CONFIG_PATH,
				PluginDefines.CONFIG_FILE,
				PluginConfig.class,
				gcTimer.class
		);
		if(this.config == null) {
			this.fail("Failed to load "+PluginDefines.CONFIG_FILE);
			return;
		}
		if(this.config.isFromResource())
			xLog.getRoot(LOG_NAME).warning("Created default "+PluginDefines.CONFIG_FILE);
		// register listeners
		this.register(new Commands());
	}



	@Override
	protected void onDisable() {
		this.unregister(Commands.class);
	}



}
