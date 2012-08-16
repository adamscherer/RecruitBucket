package com.annconia.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.annconia.api.interceptor.AuthenticationEvent;
import com.annconia.api.interceptor.RateLimit;
import com.annconia.api.interceptor.RequireAuthentication;
import com.annconia.api.json.JsonErrorResponse;
import com.annconia.api.util.RequestContextHolder;
import com.annconia.security.SessionManager;
import com.annconia.security.core.context.SessionContext;
import com.annconia.security.core.context.SessionContextHolder;

@Controller
public class SecurityController extends ApiController {

	@Autowired
	SessionManager sessionManager;

	@RequestMapping("/authenticate")
	@ResponseBody
	@RequireAuthentication
	public ResponseEntity<Object> getAuthenticatedUser() {
		SessionContext context = SessionContextHolder.get();

		return new ResponseEntity<Object>(context.getUser(), HttpStatus.OK);
	}

	@RequestMapping(params = { "username", "password" }, value = "/authenticate")
	@ResponseBody
	@RateLimit
	public ResponseEntity<Object> authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {

		try {
			SessionContext sessionContext = sessionManager.authenticate(username, password);

			SessionContextHolder.set(sessionContext);

			RequestContextHolder.get().addSessionCookie(SessionContextHolder.sessionName, sessionContext.getSession().getId());

			getApplicationContext().publishEvent(new AuthenticationEvent(sessionContext));

			return new ResponseEntity<Object>(sessionContext.getUser(), HttpStatus.OK);

		} catch (BadCredentialsException ex) {
			return new ResponseEntity<Object>(new JsonErrorResponse("authentication.fail"), HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping("/logout")
	@ResponseBody
	public ResponseEntity<Object> logout() {
		SessionContext sessionContext = SessionContextHolder.get();

		if (sessionContext != null && sessionContext.getSession() != null) {
			sessionManager.destroySession(sessionContext.getSession().getId());
		}

		return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
	}

}
