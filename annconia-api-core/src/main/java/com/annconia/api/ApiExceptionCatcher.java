package com.annconia.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.annconia.api.json.JsonErrorResponse;
import com.annconia.api.validation.ValidationException;

/**
 * Handles all exceptions from the API package.
 * 
 * @author Adam Scherer
 *
 */
public class ApiExceptionCatcher implements HandlerExceptionResolver {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * From HandlerExceptionResolver interface. Catches HANDLER exceptions.
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("API exception", ex);

		//if (ex instanceof StaleAsyncWebRequestException) {
		//	return null;
		//}

		ModelAndView mv = new ModelAndView();

		if (ex instanceof ValidationException) {
			ValidationException e = (ValidationException) ex;
			mv.setView(new JsonView(new JsonErrorResponse(e.getErrors())));
			return mv;
		}

		if (ex instanceof BindException) {
			BindException e = (BindException) ex;
			BindingResult result = e.getBindingResult();
			mv.setView(new JsonView(new JsonErrorResponse(result)));
			return mv;
		}

		if (ex instanceof ApiException) {
			mv.setView(new JsonView(new JsonErrorResponse(((ApiException) ex).getMessageCode())));
			return mv;
		}

		mv.setView(new JsonView(new JsonErrorResponse("general.error")));

		return mv;
	}

}
