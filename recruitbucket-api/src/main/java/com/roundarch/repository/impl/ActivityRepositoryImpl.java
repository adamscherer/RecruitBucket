package com.roundarch.repository.impl;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Repository;

import com.annconia.api.repository.AfterSaveEvent;
import com.annconia.api.repository.BeforeSaveEvent;
import com.annconia.api.repository.RepositoryEvent;
import com.roundarch.entity.RecruitEntity;
import com.roundarch.repository.ActivityRepositoryExtension;

@Repository("activityRepository")
public class ActivityRepositoryImpl implements ActivityRepositoryExtension, ApplicationListener<RepositoryEvent> {

	public final void onApplicationEvent(RepositoryEvent event) {
		if (!(event.getSource() instanceof RecruitEntity)) {
			return;
		}

		RecruitEntity entity = (RecruitEntity) event.getSource();
		if (event instanceof BeforeSaveEvent) {
			onBeforeSave(entity);
		} else if (event instanceof AfterSaveEvent) {

		}
	}

	/**
	 * Override this method if you are interested in {@literal beforeSave} events.
	 *
	 * @param entity
	 */
	protected void onBeforeSave(RecruitEntity entity) {

	}

}
