package com.growcontrol.plugins.gctimer.server.configs;

import java.util.Map;

import com.growcontrol.plugins.gctimer.PluginDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.xHashable;


public class DeviceConfig extends xConfig implements xHashable {

	protected volatile String key = null;

	private final boolean enabled;



	public DeviceConfig(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		this.enabled = this.getBool(
				PluginDefines.CONFIG_DEVICE_ENABLED,
				false
		);
		this.key = this.genKey();
	}



	public boolean isEnabled() {
		return this.enabled;
	}



	@Override
	public String toString() {
		return this.key;
	}
	@Override
	public String getKey() {
		return this.key;
	}
	private String genKey() {
		final StringBuilder str = new StringBuilder();
//		final String host = this.getHost();
//		str.append(host == null ? "*" : host.toLowerCase());
//		str.append(":").append(this.port);
//		if(this.ssl) str.append("<ssl>");
		return str.toString();
	}
	@Override
	public boolean matches(final xHashable hashable) {
		if(hashable == null || !(hashable instanceof DeviceConfig) )
			return false;
		final DeviceConfig config = (DeviceConfig) hashable;
		if(this.enabled != config.enabled)
			return false;
		return this.getKey().equalsIgnoreCase(config.getKey());
	}



}
