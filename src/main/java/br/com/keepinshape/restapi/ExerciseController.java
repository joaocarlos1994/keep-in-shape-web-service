/*
 * @(#)ExerciseController.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.keepinshape.applicationlayer.ExerciseApplicationLayer;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.wrapper.ExerciseWrapper;

/**
 * A <code>ExerciseController</code> tem por responsabilidade
 * conter os recursos para salver e deletar um exercicio dentro
 * da aplicacao.
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@RestController
@RequestMapping("/rest")
public class ExerciseController {
	
	private final ExerciseApplicationLayer exerciseApplicationLayer;
	
	@Autowired
	public ExerciseController(@Qualifier("exerciseApplicationLayerImpl") final ExerciseApplicationLayer exerciseApplicationLayer) {
		super();
		this.exerciseApplicationLayer = exerciseApplicationLayer;
	}

	/**
	 * Este metodo tem por responsabilidade disponibilizar um end-point para acesso ao keep-in-shape,
	 * onde a mesma recebe a <code>ExerciseWrapper</code> que encapsula a <code>Exercise</code>.
	 * 
	 * @param exerciseWrapper objeto que contem o exercise.
	 * @return ResponseEntity<ExerciseWrapper> com um status http e um json em seu body.
	 * */
	@PostMapping(value = "/exercise")
	public ResponseEntity<ExerciseWrapper> save(@RequestBody final ExerciseWrapper exerciseWrapper) {
		final Exercise saveExercise = exerciseApplicationLayer.saveExercise(exerciseWrapper.getExercise());
		return new ResponseEntity<>(new ExerciseWrapper(saveExercise), HttpStatus.CREATED);
	}
	
	/**
	 * Este metodo tem por responsabilidade expor o end-point para excluso de um id do keep-in-shape.
	 * 
	 * @param idExercise O id do exercicio.
	 * @return ResponseEntity<ExerciseWrapper> com um status http.
	 * */
	@DeleteMapping(value = "/exercise/{id}")
	public ResponseEntity<Class<?>> delete(@PathVariable("id") final Long idExercise) {
		exerciseApplicationLayer.deleteExercise(idExercise);
		return new ResponseEntity<>(Void.TYPE, HttpStatus.OK);
	}
}
