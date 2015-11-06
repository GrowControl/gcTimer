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


public class ConfigDeviceType_Span extends DeviceConfig {

	private final String rangeStr;
	private final xTime  range;
	private final List<Track> tracks;



	public ConfigDeviceType_Span(final Map<String, Object> datamap)
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
			final String onStr  = cfg.getString(PluginDefines.CONFIG_DEVICE_TRACK_SPAN_ON);
			final String offStr = cfg.getString(PluginDefines.CONFIG_DEVICE_TRACK_SPAN_OFF);
			if(utils.isEmpty(onStr))  throw new xConfigException("On value is required in span timer config!");
			if(utils.isEmpty(offStr)) throw new xConfigException("Off value is required in span timer config!");
			final Track track = new Track(
					onStr,
					offStr
			);
			tracks.add(track);
		}
		return Collections.unmodifiableList(tracks);
	}



	@Override
	public String getTypeStr() {
		return "span";
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
		public final String onStr;
		public final String offStr;
		public final xTime on;
		public final xTime off;
		public Track(final String onStr, final String offStr) {
			this.onStr  = onStr;
			this.offStr = offStr;
			this.on  = xTime.get(this.onStr).setFinal();
			this.off = xTime.get(this.offStr).setFinal();
		}
	}



}
