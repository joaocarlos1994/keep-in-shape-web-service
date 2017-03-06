/*
 * @(#)ActivityApplicationLayerImpl.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.applicationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.ActivityRepository;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
@Repository
public class ActivityApplicationLayerImpl implements ActivityApplicationLayer {
	
	private final ActivityRepository activityRepository;
	
	@Autowired
	public ActivityApplicationLayerImpl(final ActivityRepository activityRepository) {
		super();
		this.activityRepository = activityRepository;
	}

	@Override
	public Activity saveActivity(final Activity activity) {
		if (activity != null) {
			return activityRepository.save(activity);
		}
		return null;
	}

	@Override
	public void delteExercise(final Long idActivity) {
		if (idActivity != null && idActivity.longValue() > 0L) {
			activityRepository.delete(idActivity);
		}
	}
}
