package com.growcontrol.plugins.gctimer.server.configs.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.growcontrol.plugins.gctimer.PluginDefines;
import com.growcontrol.plugins.gctimer.server.configs.DeviceConfig;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsObject;
import com.poixson.commonjava.Utils.xTime;


public class ConfigDeviceType_Cron extends DeviceConfig {

	private final String rangeStr;
	private final xTime  range;
	private final List<Track> tracks;



	public ConfigDeviceType_Cron(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		this.rangeStr = this.getString(PluginDefines.CONFIG_DEVICE_RANGE);
		this.range = xTime.get(this.rangeStr)
				.setFinal();
		this.tracks = this.loadTracks();
		this.key = this.genKey();
	}
	private List<Track> loadTracks() throws xConfigException {
		final List<Object> datalist = this.getList(
				Object.class,
				PluginDefines.CONFIG_DEVICE_TRACKS
		);
		final List<Track> tracks = new ArrayList<Track>();
		for(final Object obj : datalist) {
			final Map<String, Object> map = utilsObject.castMap(
					String.class,
					Object.class,
					obj
			);
			final xConfig cfg = new xConfig(map);
			final String pattern  = cfg.getString(PluginDefines.CONFIG_DEVICE_TRACK_CRON_PATTERN);
			final String duration = cfg.getString(PluginDefines.CONFIG_DEVICE_TRACK_CRON_DURATION);
			if(utils.isEmpty(pattern))  throw new xConfigException("Pattern value is required in cron timer config!");
			if(utils.isEmpty(duration)) throw new xConfigException("Duration value is required in cron timer config!");
			final Track track = new Track(
					pattern,
					duration
			);
			tracks.add(track);
		}
		return Collections.unmodifiableList(tracks);
	}



	@Override
	public String getTypeStr() {
		return "cron";
	}
	public String getRangeStr() {
		return this.rangeStr;
	}
	public xTime getRange() {
		return this.range;
	}
	public List<Track> getTracks() {
		return this.tracks;
	}
	@Override
	protected String genKey() {
		return this.getName();
	}



	public class Track {
		public final String pattern;
		public final String duration;
		public Track(final String pattern, final String duration) {
			this.pattern = pattern;
			this.duration = duration;
		}
	}



}
