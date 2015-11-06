package com.growcontrol.plugins.gctimer.server.configs;

import java.util.Map;

import com.growcontrol.common.meta.MetaAddress;
import com.growcontrol.plugins.gctimer.PluginDefines;
import com.growcontrol.plugins.gctimer.server.configs.types.ConfigDeviceType_Cron;
import com.growcontrol.plugins.gctimer.server.configs.types.ConfigDeviceType_Span;
import com.growcontrol.plugins.gctimer.server.configs.types.ConfigDeviceType_Ticker;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xHashable;


public abstract class DeviceConfig extends xConfig implements xHashable {

	protected volatile String key = null;

	private final String name;
	private final String title;
	private final boolean enabled;
	private final String initial;
	private final String outputStr;



	public static DeviceConfig get(final Map<String, Object> datamap)
			throws xConfigException {
		final String typeStr;
		try {
			typeStr = (String) datamap.get(PluginDefines.CONFIG_DEVICE_TYPE);
		} catch (Exception e) {
			throw new xConfigException("Failed to read a timer type from config!", e);
		}
		if(utils.isEmpty(typeStr))
			throw new xConfigException("Failed to read a timer type from config!");
		switch(typeStr.toLowerCase()) {
		case "cron":
		case "crontab":
			return new ConfigDeviceType_Cron(datamap);
		case "span":
			return new ConfigDeviceType_Span(datamap);
		case "tick":
		case "ticker":
			return new ConfigDeviceType_Ticker(datamap);
		}
		return null;
	}
	public DeviceConfig(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		this.name    = this.getString(PluginDefines.CONFIG_DEVICE_NAME);
		this.title   = this.getStr(   PluginDefines.CONFIG_DEVICE_TITLE, null);
		this.enabled = this.getBool(
				PluginDefines.CONFIG_DEVICE_ENABLED,
				false
		);
		this.initial   = this.getStr(   PluginDefines.CONFIG_DEVICE_INITIAL, null);
		this.outputStr = this.getString(PluginDefines.CONFIG_DEVICE_OUTPUT);
	}



	public String getName() {
		return this.name;
	}
	public String getTitle() {
		return utils.isEmpty(this.title)
				? this.name
				: this.title;
	}
	public abstract String getTypeStr();
	public boolean isEnabled() {
		return this.enabled;
	}
	public String getInitial() {
		return this.initial;
	}
	public String getOutputStr() {
		return this.outputStr;
	}
	public MetaAddress getOutput() {
		return MetaAddress.get(this.getOutputStr());
	}



	@Override
	public String toString() {
		return this.key;
	}
	@Override
	public String getKey() {
		return this.key;
	}
	protected abstract String genKey();
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
