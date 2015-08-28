package com.growcontrol.plugins.gctimer.configs;

import java.util.Map;
import java.util.Set;

import com.growcontrol.plugins.gctimer.PluginDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xLogger.xLog;


public class PluginConfig extends xConfig {
	private static final String LOG_NAME = "CONFIG";

	private volatile Map<String, TimerConfig> timerConfigs = null;
	private final Object configLock = new Object();



	public PluginConfig(final Map<String, Object> datamap) {
		super(datamap);
	}



	// plugin version
	public String getVersion() {
		final String value = this.getString(PluginDefines.CONFIG_VERSION);
		if(utils.isEmpty(value))
			return null;
		return value;
	}




	// timer configs
	public Map<String, TimerConfig> getTimerConfigs() {
		if(this.timerConfigs == null) {
			synchronized(this.configLock) {
				if(this.timerConfigs == null) {
					final Set<Object> dataset = this.getSet(
							Object.class,
							PluginDefines.CONFIG_TIMERS
					);
					this.timerConfigs = TimerConfig.get(dataset);
				}
			}
		}
		return this.timerConfigs;
	}







	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if(this._log == null)
			this._log = xLog.getRoot(LOG_NAME);
		return this._log;
	}



}
