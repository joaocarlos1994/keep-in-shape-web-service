/*
 * @(#)ActivityWrapper.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.wrapper;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.deserializer.ActivityDeserialize;
import br.com.keepinshape.restapi.serializer.ActivitySerialize;

/**
 * A <code>ActivityWrapper</code> e um objeto de valor
 * e tem por responsabilidade encaminhar um objeto
 * da <code>Activity</code> para ser serializado e
 * devolvido ao front-end, como receber um json
 * do front-end e encaminhar o mesmo para 
 * <code>ActivityDeserialize</code> para realizar a
 * deserializacao.
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
@JsonDeserialize(using = ActivityDeserialize.class)
@JsonSerialize(using = ActivitySerialize.class)
public class ActivityWrapper {
	
	private final Activity activity;

	public ActivityWrapper(final Activity activity) {
		super();
		this.activity = activity;
	}

	public Activity getActivity() {
		return activity;
	}
	
	public Long getId() {
		return activity.getId();
	}
	
	public String getName() {
		return activity.getName();
	}
	
	public List<Exercise> getExercices() {
		return activity.getExercises();
	}
	
	public String getWeekday() {
		return activity.getWeekday().name();
	}
	
	public double totalPoints() {
		return activity.totalPoints();
	}
}
