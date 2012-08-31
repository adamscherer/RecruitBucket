package com.annconia.api.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class PerformanceContext {

	protected static final Logger logger = LoggerFactory.getLogger(PerformanceContext.class);

	StopWatch monitor = new StopWatch();

	public PerformanceContext() {
		this.monitor = new StopWatch();
		monitor.start();
	}

	public PerformanceContext(StopWatch monitor) {
		this.monitor = monitor;
		monitor.start();
	}

	public StopWatch getMonitor() {
		return monitor;
	}

	public void stop() {
		monitor.stop();
	}

	public long totalTime() {
		monitor.stop();

		return monitor.getTotalTimeMillis();
	}

}
