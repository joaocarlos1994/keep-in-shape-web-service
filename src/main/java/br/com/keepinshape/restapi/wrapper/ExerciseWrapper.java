/*
 * @(#)ExerciseWrapper.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.deserializer.ExerciseDeserialize;
import br.com.keepinshape.restapi.serializer.ExerciseSerialize;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@JsonDeserialize(using = ExerciseDeserialize.class)
@JsonSerialize(using = ExerciseSerialize.class)
public class ExerciseWrapper {
	
	private final Exercise exercise;

	public ExerciseWrapper(final Exercise exercise) {
		super();
		this.exercise = exercise;
	}
	
	public Long getId() {
		return exercise.getId();
	}
	
	public String getName() {
		return exercise.getName();
	}
	
	public double getWeight() {
		return exercise.getWeight();
	}
	
	public int getQuantity() {
		return exercise.getQuantity();
	}
	
	public double getPoints() {
		return exercise.getPoints();
	}

	public Exercise getExercise() {
		return exercise;
	}
}
