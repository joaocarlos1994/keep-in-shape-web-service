/*
 * @(#)ExerciseWrapper.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.deserializer.ExerciseJsonDeserialize;

/**
 * A <code>ExerciseWrapper</code> e um objeto 
 * de valor, onde tem por responsabilidade receber
 * um json enviado do front-end e encaminhar
 * o mesmo para <code>ExerciseJsonDeserialize</code>.
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@JsonDeserialize(using = ExerciseJsonDeserialize.class)
public class ExerciseWrapper {
	
	private final Exercise exercise;

	public ExerciseWrapper(final Exercise exercise) {
		super();
		this.exercise = exercise;
	}

	public Exercise getExercise() {
		return exercise;
	}
}
