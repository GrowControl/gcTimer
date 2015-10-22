package com.growcontrol.plugins.gctimer.server.commands;

import com.poixson.commonjava.xEvents.annotations.xEvent;
import com.poixson.commonjava.xLogger.commands.xCommandEvent;
import com.poixson.commonjava.xLogger.commands.xCommandListener;


public class Commands implements xCommandListener {
//	private static final String LOG_NAME = gcTimer.LOG_NAME;
	private static final String LISTENER_NAME = "gcTimerCommands";



	@Override
	public String getName() {
		return LISTENER_NAME;
	}
	@Override
	public String toString() {
		return this.getName();
	}



	// plugin commands
	@Override
	@xEvent(
			priority=ListenerPriority.NORMAL,
//			async=false,
			filterHandled=true,
			filterCancelled=true)
	public void onCommand(final xCommandEvent event) {

	}



}
