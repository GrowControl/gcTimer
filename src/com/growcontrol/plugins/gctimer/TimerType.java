package com.growcontrol.plugins.gctimer;

import com.poixson.commonjava.Utils.utils;

public enum TimerType {

	SPAN,
	TICKER,
	CRON;



	public static TimerType get(final String str) {
		if(utils.isEmpty(str))
			return null;
		switch(str.toLowerCase()) {
		// span timer
		case "span":
		case "repeat":
		case "repeating":
			return TimerType.SPAN;
		// ticker timer
		case "ticker":
		case "tick":
			return TimerType.TICKER;
		// cron timer
		case "cron":
		case "crontab":
		case "clock":
			return TimerType.CRON;
		}
		return null;
	}



}
