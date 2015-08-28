package com.growcontrol.plugins.gctimer.commands;

import com.poixson.commonjava.EventListener.xEvent;
import com.poixson.commonjava.EventListener.xEvent.Priority;
import com.poixson.commonjava.xLogger.handlers.xCommandEvent;
import com.poixson.commonjava.xLogger.handlers.xCommandListener;


public class Commands implements xCommandListener {
//	private static final String LOG_NAME = gcTimer.LOG_NAME;



	// plugin commands
	@Override
	@xEvent(
			priority=Priority.NORMAL,
			threaded=false,
			filterHandled=true,
			filterCancelled=true)
	public void onCommand(final xCommandEvent event) {

	}



}
