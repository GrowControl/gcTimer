package com.growcontrol.plugins.gctimer.server.configs;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.growcontrol.common.gcCommonDefines;
import com.growcontrol.plugins.gctimer.PluginDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utilsObject;


public class PluginConfig extends xConfig {

	private final String version;

	private final Map<String, DeviceConfig> deviceConfigs;



	public PluginConfig(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		this.version = this.getString(gcCommonDefines.CONFIG_VERSION);
		this.deviceConfigs = this.loadDeviceConfigs();
	}
	private Map<String, DeviceConfig> loadDeviceConfigs()
			throws xConfigException {
		final List<Object> datalist = this.getList(
				Object.class,
				PluginDefines.CONFIG_DEVICES
		);
		final Map<String, DeviceConfig> configs = new HashMap<String, DeviceConfig>();
		for(final Object obj : datalist) {
			final Map<String, Object> map = utilsObject.castMap(
					String.class,
					Object.class,
					obj
			);
			final DeviceConfig cfg = DeviceConfig.get(map);
			if(cfg == null)
				throw new xConfigException("Failed to load a timer device config!");
			configs.put(cfg.getKey(), cfg);
		}
		return Collections.unmodifiableMap(configs);
	}



	// plugin version
	public String getVersion() {
		return this.version;
	}
	// timer device configs
	public Map<String, DeviceConfig> getDeviceConfigs() {
		return this.deviceConfigs;
	}



}
