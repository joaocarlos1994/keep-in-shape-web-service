/*
 * @(#)ExerciseApplicationLayerImpl.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.applicationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.domain.exercise.ExerciseRepository;

/**
 * A <code>ExerciseApplicationLayerImpl</code> implementa a
 * <code>ExerciseApplicationLayerImpl</code> para fornecer
 * pontos incomum para os recursos do webservice.
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@Repository
public class ExerciseApplicationLayerImpl implements ExerciseApplicationLayer {

	private final ExerciseRepository exerciseRepository;
	
	@Autowired
	public ExerciseApplicationLayerImpl(final ExerciseRepository exerciseRepository) {
		super();
		this.exerciseRepository = exerciseRepository;
	}

	@Override
	public Exercise saveExercise(final Exercise exercise) {
		return exerciseRepository.save(exercise);
	}

	@Override
	public void delteExercise(final Long idExercise) {
		if (idExercise.longValue() > 0L) {
			exerciseRepository.delete(idExercise);
		} else {
			throw new IllegalArgumentException("Id is invalid");
		}
	}

}
