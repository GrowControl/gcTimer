package com.growcontrol.plugins.gctimer;

import com.growcontrol.plugins.gctimer.commands.Commands;
import com.growcontrol.server.plugins.gcServerPlugin;


public class gcTimer extends gcServerPlugin {



	@Override
	protected void onEnable() {
		this.register(new Commands());
	}



	@Override
	protected void onDisable() {
		this.unregister(Commands.class);
	}



}
