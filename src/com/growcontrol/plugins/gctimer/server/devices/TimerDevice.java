package com.growcontrol.plugins.gctimer.server.devices;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.growcontrol.plugins.gctimer.PluginDefines;
import com.growcontrol.plugins.gctimer.server.gcTimer;
import com.growcontrol.plugins.gctimer.server.configs.DeviceConfig;
import com.growcontrol.plugins.gctimer.server.devices.types.TimerDevice_Cron;
import com.growcontrol.plugins.gctimer.server.devices.types.TimerDevice_Span;
import com.growcontrol.plugins.gctimer.server.devices.types.TimerDevice_Tick;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xCloseable;
import com.poixson.commonjava.Utils.xHashable;
import com.poixson.commonjava.xLogger.xLog;


public abstract class TimerDevice implements xCloseable, xHashable {
	private static final String LOG_NAME = gcTimer.LOG_NAME;

	private final DeviceConfig config;

	private static final Map<String, TimerDevice> devices =
			new ConcurrentHashMap<String, TimerDevice>();



	public static TimerDevice get(final DeviceConfig config) {
		final String key = config.getKey();
		// existing timer device
		{
			final TimerDevice device = devices.get(key);
			if(device != null)
				return device;
		}
		// new timer device
		synchronized(devices) {
			if(devices.containsKey(key))
				return devices.get(key);
			final TimerDevice device = load(config);
			if(device == null)
				return null;
			devices.put(key, device);
			return device;
		}
	}
	private static TimerDevice load(final DeviceConfig config) {
//TODO: this exists in ArduinoGC
//		if(gcTimer.isStopping())
//			return null;
		switch(config.getTypeStr().toLowerCase()) {
		// cron
		case "cron":
		case "crontab":
			return new TimerDevice_Cron(config);
		// span
		case "span":
			return new TimerDevice_Span(config);
		// ticker
		case "tick":
		case "ticker":
			return new TimerDevice_Tick(config);
		}
		throw new RuntimeException("Unknown timer device type: "+
				config.getClass().getName());
	}
	public static Set<TimerDevice> loadAll(
			final Map<String, DeviceConfig> configs) {
		if(utils.isEmpty(configs)) {
			log().fine("No timer devices to load in "+PluginDefines.CONFIG_FILE);
			return null;
		}
		// load from configs
		log().info("Starting timers..");
		final Set<TimerDevice> devices = new HashSet<TimerDevice>();
		int count  = 0;
		int failed = 0;
		for(final DeviceConfig cfg : configs.values()) {
			if(!cfg.isEnabled()) {
				log().finer("Timer device is disabled: "+cfg.getKey()+" - "+cfg.getName());
				continue;
			}
			TimerDevice device = null;
			try {
				device = get(cfg);
			} catch (Exception e) {
				device = null;
				log().trace(e);
			}
			if(device == null) {
				failed++;
				log().warning("Failed to start timer: "+cfg.getKey());
			} else {
				count++;
				log().info("Successfully started timer: "+cfg.getKey()+" - "+cfg.getName());
				devices.add(device);
			}
		}
		if(count > 0)
			log().info("Started [ "+Integer.toString(count)+" ] timer devices..");
		if(failed > 0)
			log().warning("Failed to start [ "+Integer.toString(failed)+" ] timer devices!");
		return devices;
	}



	protected TimerDevice(final DeviceConfig config) {
		this.config = config;
	}
//TODO: this is from ArduinoGC used by listener interface
//	@Override
//	public abstract String getName();
	@Override
	public abstract String toString();



	// logger
	private static volatile xLog _log = null;
	public static xLog log() {
		if(_log == null)
			_log = xLog.getRoot(LOG_NAME);
		return _log;
	}



}
