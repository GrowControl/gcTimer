package com.growcontrol.plugins.gctimer.server;

import java.util.Map;
import java.util.Set;

import com.growcontrol.api.serverapi.plugins.apiServerPlugin;
import com.growcontrol.plugins.gctimer.PluginDefines;
import com.growcontrol.plugins.gctimer.server.commands.Commands;
import com.growcontrol.plugins.gctimer.server.configs.DeviceConfig;
import com.growcontrol.plugins.gctimer.server.configs.PluginConfig;
import com.growcontrol.plugins.gctimer.server.devices.TimerDevice;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.xLogger.xLog;


public class gcTimer extends apiServerPlugin {
	public static final String LOG_NAME = "gcTimer";

	private volatile PluginConfig config = null;



	@Override
	protected void onEnable() {
		// load config
		try {
			this.config = (PluginConfig) xConfig.Load(
					getLogger(),
					PluginDefines.CONFIG_PATH,
					PluginDefines.CONFIG_FILE,
					PluginConfig.class,
					gcTimer.class
			);
		} catch (xConfigException e) {
			this.log().trace(e);
			this.config = null;
		}
		if(this.config == null) {
			this.fail("Failed to load "+PluginDefines.CONFIG_FILE);
			return;
		}
		if(this.config.isFromResource())
			xLog.getRoot(LOG_NAME).warning("Created default "+PluginDefines.CONFIG_FILE);
		// start timers
		final Map<String, DeviceConfig> deviceConfigs =
				this.config.getDeviceConfigs();
		final Set<TimerDevice> devices =
				TimerDevice.loadAll(
						deviceConfigs
				);
		if(devices == null) {
			this.fail("Failed to start timer devices!");
			return;
		}
		Keeper.add(devices);
		// register listeners
		this.register(new Commands());
	}



	@Override
	protected void onDisable() {
		this.unregister(Commands.class);
	}



	// logger
	private static volatile xLog _log = null;
	public static xLog getLogger() {
		if(_log == null)
			_log = xLog.getRoot(LOG_NAME);
		return _log;
	}
	public static xLog getLogger(final String name) {
		return getLogger().get(name);
	}



}
