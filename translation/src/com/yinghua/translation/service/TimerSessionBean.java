package com.yinghua.translation.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 * TimerBean is a singleton session bean that creates a timer and prints out a
 * message when a timeout occurs.
 */
@Stateless
public class TimerSessionBean {

//	@Schedule(second = "*/10000", minute = "*", hour = "*", persistent = false)
	public void automaticTimeout() {
		Date currentTime = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy.MM.dd G 'at' HH:mm:ss z");
		System.out.println("ScheduleExample.doWork() invoked at "
				+ simpleDateFormat.format(currentTime));
	}

}
