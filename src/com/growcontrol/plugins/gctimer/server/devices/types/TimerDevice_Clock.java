package com.growcontrol.plugins.gctimer.server.devices.types;

import java.io.IOException;

import com.growcontrol.plugins.gctimer.server.configs.DeviceConfig;
import com.growcontrol.plugins.gctimer.server.devices.TimerDevice;
import com.poixson.commonjava.Utils.xHashable;


public class TimerDevice_Span extends TimerDevice {



	public TimerDevice_Span(final DeviceConfig config) {
		super(config);
	}



	@Override
	public boolean isClosed() {
return false;
	}
	@Override
	public void close() throws IOException {
	}
	@Override
	public String getKey() {
return null;
	}
	@Override
	public boolean matches(final xHashable arg0) {
return false;
	}
//	@Override
//	public String getName() {
//return null;
//	}
	@Override
	public String toString() {
return null;
	}



}
