package com.annconia.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.annconia.util.PerfLogger;
import com.annconia.util.PerfLogger.LogType;

public class WebPerformanceInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		PerformanceContext context = new PerformanceContext();
		PerformanceContextHolder.set(context);

		return true;
	}

	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj, Exception exception) throws Exception {

		long totalTime = PerformanceContextHolder.get().totalTime();
		PerfLogger.log(httpservletrequest.getRequestURI(), LogType.WEB, totalTime);
		PerformanceContextHolder.reset();
	}

}
