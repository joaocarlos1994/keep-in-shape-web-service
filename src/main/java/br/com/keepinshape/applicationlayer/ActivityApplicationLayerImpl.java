/*
 * @(#)ActivityApplicationLayerImpl.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.applicationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.ActivityRepository;

/**
 * A <code>ActivityApplicationLayerImpl</code> tem por
 * objetivo fornecer metodos comum para infraestrutura 
 * de banco de dados.
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
	public Activity findById(final Long id) {
		if (id != null && id.longValue() > 0) {
			return activityRepository.findOne(id);
		}
		return null;
	}

	@Override
	public void delteActivity(final Long idActivity) {
		if (idActivity != null && idActivity.longValue() > 0L) {
			activityRepository.delete(idActivity);
		} else {
			throw new IllegalArgumentException("Id is invalid");
		}
	}
}
