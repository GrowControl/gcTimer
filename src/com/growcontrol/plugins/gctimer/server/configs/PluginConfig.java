package com.growcontrol.plugins.gctimer.server.configs;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.growcontrol.common.gcCommonDefines;
import com.growcontrol.plugins.gctimer.PluginDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;


public class PluginConfig extends xConfig {

	public final String version;

	private volatile Map<String, DeviceConfig> deviceConfigs = null;
	private final Object configLock = new Object();



	public PluginConfig(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		this.version = this.getString(gcCommonDefines.CONFIG_VERSION);
	}



	// plugin version
	public String getVersion() {
		return this.version;
	}



	// timer device configs
	public Map<String, DeviceConfig> getDeviceConfigs()
			throws xConfigException {
		if(this.deviceConfigs == null) {
			synchronized(this.configLock) {
				if(this.deviceConfigs == null) {
					final List<xConfig> configsList =
						this.getConfigList(
								PluginDefines.CONFIG_DEVICES,
								DeviceConfig.class
					);
					final LinkedHashMap<String, DeviceConfig> devicesMap =
							new LinkedHashMap<String, DeviceConfig>();
					for(final xConfig cfg : configsList) {
						final DeviceConfig d = (DeviceConfig) cfg;
						devicesMap.put(d.getKey(), d);
					}
					this.deviceConfigs = Collections.unmodifiableMap(devicesMap);
				}
			}
		}
		return this.deviceConfigs;
	}



}
