package com.roundarch.controller;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.annconia.api.controller.ApiPagingAndSortingController;
import com.annconia.api.interceptor.AuthenticationEvent;
import com.annconia.security.core.context.SessionContext;
import com.annconia.security.entity.SecurityUser;
import com.roundarch.entity.InterviewerEntity;
import com.roundarch.repository.InterviewerRepository;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/interviewer")
public class InterviewerController extends ApiPagingAndSortingController<InterviewerEntity, InterviewerRepository> implements ApplicationListener<AuthenticationEvent> {

	@Autowired(required = false)
	private InterviewerRepository repository;

	@PostConstruct
	public void init() {
		setRepository(repository);
	}

	public final void onApplicationEvent(AuthenticationEvent event) {
		if (!(event.getSource() instanceof SessionContext)) {
			return;
		}

		SessionContext context = (SessionContext) event.getSource();
		SecurityUser user = context.getUser();
		if (user == null) {
			return;
		}

		String securityId = (StringUtils.isEmpty(user.getId())) ? user.getUsername() : user.getId();
		InterviewerEntity interviewer = repository.findBySecurityId(securityId);
		if (interviewer != null) {
			return;
		}

		interviewer = new InterviewerEntity();
		interviewer.setSecurityId(securityId);
		interviewer.setUsername(user.getUsername());
		interviewer.setFirstName(user.getFirstName());
		interviewer.setLastName(user.getLastName());
		repository.save(interviewer);
	}

}