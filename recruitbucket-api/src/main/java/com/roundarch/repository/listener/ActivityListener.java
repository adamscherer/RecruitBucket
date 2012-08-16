package com.roundarch.repository.listener;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.annconia.api.repository.AfterSaveEvent;
import com.annconia.api.repository.RepositoryEvent;
import com.annconia.security.core.context.SessionContext;
import com.annconia.security.core.context.SessionContextHolder;
import com.roundarch.entity.ActivityEntity;
import com.roundarch.entity.ActivityType;
import com.roundarch.entity.InterviewerEntity;
import com.roundarch.entity.RecruitEntity;
import com.roundarch.entity.ReviewEntity;
import com.roundarch.repository.ActivityRepository;
import com.roundarch.repository.InterviewerRepository;

@Component
public class ActivityListener implements ApplicationListener<RepositoryEvent> {

	private final ArrayBlockingQueue<DeferredResult<Page<ActivityEntity>>> activeListeners = new ArrayBlockingQueue<DeferredResult<Page<ActivityEntity>>>(10);

	@Autowired(required = false)
	private ActivityRepository repository;

	@Autowired(required = false)
	private InterviewerRepository interviewerRepository;

	public DeferredResult<Page<ActivityEntity>> subscribe(DeferredResult<Page<ActivityEntity>> listener) {
		activeListeners.add(listener);

		return listener;
	}

	public void notify(Page<ActivityEntity> activity) {
		for (Iterator<DeferredResult<Page<ActivityEntity>>> iterator = activeListeners.iterator(); iterator.hasNext();) {
			DeferredResult<Page<ActivityEntity>> listener = iterator.next();
			try {
				listener.trySet(activity);
			} finally {
				iterator.remove();
			}
		}
	}

	public final void onApplicationEvent(RepositoryEvent event) {
		if ((event.getSource() instanceof RecruitEntity)) {
			RecruitEntity entity = (RecruitEntity) event.getSource();
			if (event instanceof AfterSaveEvent) {
				onAfterSave(entity);
			}

			return;
		}

		if ((event.getSource() instanceof ReviewEntity)) {
			ReviewEntity entity = (ReviewEntity) event.getSource();
			if (event instanceof AfterSaveEvent) {
				onAfterSave(entity);
			}

			return;
		}

		if ((event.getSource() instanceof InterviewerEntity)) {
			InterviewerEntity entity = (InterviewerEntity) event.getSource();
			if (event instanceof AfterSaveEvent) {
				onAfterSave(entity);
			}

			return;
		}
	}

	/**
	 * Override this method if you are interested in {@literal afterSave} events.
	 *
	 * @param entity
	 */
	protected void onAfterSave(RecruitEntity entity) {
		ActivityType type = (entity.getId() == null) ? ActivityType.ADD_RECRUIT : ActivityType.UPDATE_RECRUIT;
		saveActivity(type, entity.getId());
	}

	protected void onAfterSave(ReviewEntity entity) {
		ActivityType type = (entity.getId() == null) ? ActivityType.ADD_REVIEW : ActivityType.UPDATE_REVIEW;
		saveActivity(type, entity.getRecruitId());
	}

	protected void onAfterSave(InterviewerEntity entity) {
		saveActivity((entity.getId() == null) ? ActivityType.ADD_INTERVIEWER : ActivityType.UPDATE_INTERVIEWER);
	}

	protected void saveActivity(ActivityType type) {
		saveActivity(type, null);
	}

	protected void saveActivity(ActivityType type, String recruitId) {
		SessionContext context = SessionContextHolder.get();
		if (context != null && context.getUser() != null) {
			InterviewerEntity interviewer = interviewerRepository.findBySecurityId(context.getUser().getId());
			if (interviewer != null) {
				saveActivity(type, recruitId, interviewer.getId());
			}
		}
	}

	protected void saveActivity(ActivityType type, String recruitId, String interviewerId) {
		ActivityEntity activity = new ActivityEntity();
		activity.setActivityType(type);
		activity.setRecruitId(recruitId);
		activity.setInterviewerId(interviewerId);
		repository.save(activity);
		notify(repository.findAll(new PageRequest(0, 10)));
	}

}
