package com.growcontrol.plugins.gctimer.server.configs.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.growcontrol.plugins.gctimer.PluginDefines;
import com.growcontrol.plugins.gctimer.server.configs.DeviceConfig;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utilsObject;
import com.poixson.commonjava.Utils.xTime;


public class ConfigDeviceType_Ticker extends DeviceConfig {

	private final int   rangeInt;
	private final xTime range;
	private final List<Track> tracks;



	public ConfigDeviceType_Ticker(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		this.rangeInt = this.getInteger(PluginDefines.CONFIG_DEVICE_RANGE).intValue();
		this.range = xTime.get(this.rangeInt)
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
			final Integer onInt  = cfg.getInteger(PluginDefines.CONFIG_DEVICE_TRACK_TICK_ON);
			final Integer offInt = cfg.getInteger(PluginDefines.CONFIG_DEVICE_TRACK_TICK_OFF);
			if(onInt  == null) throw new xConfigException("Invalid On value for ticker timer track!");
			if(offInt == null) throw new xConfigException("Invalid Off value for ticker timer track!");
			final int on  = onInt.intValue();
			final int off = offInt.intValue();
			if(on  < 0 || on  > this.getRange().value) throw new xConfigException("Invalid On value for ticker timer track! "+Integer.toString(on));
			if(off < 0 || off > this.getRange().value) throw new xConfigException("Invalid Off value for ticker timer track! "+Integer.toString(off));
			final Track track = new Track(
					on,
					off
			);
			tracks.add(track);
		}
		return Collections.unmodifiableList(tracks);
	}



	@Override
	public String getTypeStr() {
		return "ticker";
	}
	public int getRangeStr() {
		return this.rangeInt;
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
		public final int on;
		public final int off;
		public Track(final int on, final int off) {
			this.on  = on;
			this.off = off;
		}
	}



}
