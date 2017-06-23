/*
 * @(#)ExerciseApplicationLayerImplTest.java 1.0 16/03/2017
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.keepinshape.applicationlayer.ExerciseApplicationLayerImpl;
import br.com.keepinshape.config.UnitTest;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.domain.exercise.ExerciseRepository;

/**
 * A <code>ExerciseApplicationLayerImplTest</code> tem por
 * responsabilidade testar a <code>ExerciseApplicationLayerImpl</code>,
 * onde mesma possui algumas regras relacionado a validacoes de campos.
 *
 * @author Joao Batista
 * @version 1.0 16/03/2017
 */
@RunWith(SpringRunner.class)
@Category(UnitTest.class)
public class ExerciseApplicationLayerImplTest {
	
	private ExerciseApplicationLayerImpl exerciseApplicationLayerImpl;
	
	@Mock
	private ExerciseRepository exerciseRepository;
	
	@Before
	public void setUp(){
	    this.exerciseApplicationLayerImpl = new ExerciseApplicationLayerImpl(exerciseRepository);
	}
	
	@Test
	public void saveExercise() {
		final List<Exercise> exercises = new LinkedList<>();
		final Exercise exercise = new Exercise.Builder("Supino Test Save").weight(50l).quantity(2).points(70l).build();
		when(exerciseRepository.findAll()).thenReturn(exercises);
		when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);
		
		final Exercise actual = exerciseApplicationLayerImpl.saveExercise(exercise);
		assertEquals(exercise.getName(), actual.getName());
		assertEquals(exercise.getWeight(), actual.getWeight(), 0);
		assertEquals(exercise.getQuantity(), actual.getQuantity(), 0);
		assertEquals(exercise.getPoints(), actual.getPoints(), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void saveExerciseExists() {
		
		final Exercise exercise = new Exercise.Builder("Supino Test Save").weight(50l).quantity(2).points(70l).build();
		when(exerciseRepository.findAll()).thenReturn(Arrays.asList(exercise));
		
		exerciseApplicationLayerImpl.saveExercise(exercise);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteExerciseByIdNegative() {		
		exerciseApplicationLayerImpl.deleteExercise(-1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteExerciseByIdZero() {		
		exerciseApplicationLayerImpl.deleteExercise(0l);
	}
	
	@Test
	public void deleteExerciseById() {
		exerciseApplicationLayerImpl.deleteExercise(1l);
		verify(exerciseRepository, times(1)).delete(1l);
	}
}
