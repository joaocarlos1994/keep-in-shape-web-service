/*
 * @(#)ExerciseApplicationLayerImpl.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.applicationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.domain.exercise.ExerciseRepository;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@Component
public class ExerciseApplicationLayerImpl implements ExerciseApplicationLayer {

	private final ExerciseRepository exerciseRepository;
	
	@Autowired
	public ExerciseApplicationLayerImpl(final ExerciseRepository exerciseRepository) {
		super();
		this.exerciseRepository = exerciseRepository;
	}

	@Override
	public Exercise saveExercise(final Exercise exercise) {
		final Exercise saveExercise = exerciseRepository.save(exercise);
		return saveExercise;
	}

	@Override
	public void delteExercise(final Long idExercise) {
		exerciseRepository.delete(idExercise);
	}

}
