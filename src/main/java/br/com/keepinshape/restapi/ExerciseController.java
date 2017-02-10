/*
 * @(#)ExerciseController.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.applicationlayer.ExerciseApplicationLayer;
import br.com.keepinshape.restapi.wrapper.ExerciseWrapper;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@BasePathAwareController
public class ExerciseController {
	
	private final ExerciseApplicationLayer exerciseApplicationLayer;
	
	public ExerciseController(final ExerciseApplicationLayer exerciseApplicationLayer) {
		super();
		this.exerciseApplicationLayer = exerciseApplicationLayer;
	}

	@PostMapping(value = "/rest")
	public ResponseEntity<ExerciseWrapper> save(@RequestBody final ExerciseWrapper exerciseWrapper) {
		final Exercise saveExercise = exerciseApplicationLayer.saveExercise(exerciseWrapper.getExercise());
		return new ResponseEntity<ExerciseWrapper>(new ExerciseWrapper(saveExercise), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/rest/{id}")
	public ResponseEntity<ExerciseWrapper> delete(@PathVariable("id") final Long idExercise) {
		exerciseApplicationLayer.delteExercise(idExercise);
		return new ResponseEntity<ExerciseWrapper>(HttpStatus.OK);
	}
	
}