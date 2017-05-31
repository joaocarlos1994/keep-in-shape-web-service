/*
 * @(#)ExerciseControllerTest.java 1.0 30/05/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.unittest.exercise;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.keepinshape.applicationlayer.ExerciseApplicationLayerImpl;
import br.com.keepinshape.config.UnitTest;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.ExerciseController;
import br.com.keepinshape.restapi.wrapper.ExerciseWrapper;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 30/05/2017
 */
@RunWith(SpringRunner.class)
@Category(UnitTest.class)
public class ExerciseControllerTest {
	
	private ExerciseController exerciseController;
	
	@Mock
	private ExerciseApplicationLayerImpl exerciseApplicationLayerImpl;
	
	@Before
	public void setUp(){
	    this.exerciseController = new ExerciseController(exerciseApplicationLayerImpl);
	}
	
	@Test
	public void saveExercise() {
		final Exercise exercise = new Exercise.Builder("Supino Test").weight(50).quantity(2).points(70).build();
		final Exercise exerciseSaved = new Exercise.Builder("Supino Test").weight(50).quantity(2).points(70).build();
		exerciseSaved.setId(1l);
		
		final ExerciseWrapper exerciseWrapper = new ExerciseWrapper(exercise);
		
		when(exerciseApplicationLayerImpl.saveExercise(any(Exercise.class))).thenReturn(exerciseSaved);
		
		final ResponseEntity<ExerciseWrapper> reponseEntity = exerciseController.save(exerciseWrapper);
		final Exercise exerciseResponse = reponseEntity.getBody().getExercise();
		
		assertEquals(201, reponseEntity.getStatusCodeValue());
		assertEquals(1, exerciseResponse.getId(), 0);
		assertEquals("Supino Test", exerciseResponse.getName());
		assertEquals(50, exerciseResponse.getWeight(), 0);
		assertEquals(2, exerciseResponse.getQuantity(), 0);
		assertEquals(70, exerciseResponse.getPoints(), 0);
	}
	
	@Test
	public void deleteExercise() {
		
		final ResponseEntity<ExerciseWrapper> reponseEntity = exerciseController.delete(1l);

		assertEquals(200, reponseEntity.getStatusCodeValue());
		verify(exerciseApplicationLayerImpl, times(1)).deleteExercise(1l);;
	}
	
}
