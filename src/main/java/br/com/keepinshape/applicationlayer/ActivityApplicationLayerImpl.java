/*
 * @(#)ActivityApplicationLayerImpl.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.applicationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.ActivityRepository;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.domain.exercise.ExerciseRepository;

/**
 * A <code>ActivityApplicationLayerImpl</code> tem por objetivo fornecer metodos
 * comum para infraestrutura de banco de dados.
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
@Component
public class ActivityApplicationLayerImpl implements ActivityApplicationLayer {

	private final ActivityRepository activityRepository;
	private final ExerciseRepository exerciseRepository;

	@Autowired
	public ActivityApplicationLayerImpl(final ActivityRepository activityRepository,
			final ExerciseRepository exerciseRepository) {
		super();
		this.activityRepository = activityRepository;
		this.exerciseRepository = exerciseRepository;
	}

	@Override
	public Activity saveActivity(final Activity activity) {
		if (activity != null) {
			return activityRepository.save(activity);
		}
		throw new IllegalArgumentException("Activity is invalid");
	}

	@Override
	public Activity findById(final Long id) {
		if (id != null && id.longValue() > 0) {
			return activityRepository.findOne(id);
		}
		throw new IllegalArgumentException("Id Activity is invalid");
	}

	@Override
	public void delteActivity(final Long idActivity) {
		if (idActivity != null && idActivity.longValue() > 0L) {
			activityRepository.delete(idActivity);
		} else {
			throw new IllegalArgumentException("Id is invalid");
		}
	}

	@Override
	public void delteActivityExercise(final Long id, final Long idExercise) {
		if (idExercise != null && idExercise > 0L) {
			final Exercise exerciseExclued = exerciseRepository.findOne(idExercise);
			final Activity activity = findById(id);
			activity.removeExercise(exerciseExclued);
			activityRepository.save(activity);
		} else {
			throw new IllegalArgumentException("Id Exercise invalid");
		}
	}
}
