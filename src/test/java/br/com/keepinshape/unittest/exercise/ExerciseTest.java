/*
 * @(#)Exercise.java 1.0 15/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.unittest.exercise;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.keepinshape.config.UnitTest;
import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.Weekday;
import br.com.keepinshape.domain.exercise.Exercise;

/**
 * A <code>ExerciseTest</code> tem por responsabilidade
 * testar os metodos da <code>Exercise</code> e
 * suas regras de dominio.
 *
 * @author Joao Batista
 * @version 1.0 15/03/2017
 */
@RunWith(SpringRunner.class)
@Category(UnitTest.class)
public class ExerciseTest {
	
	@Test
	public void testCreateExercise() {
		
		final Exercise exercise = new Exercise.Builder("Supino Test").weight(50).quantity(2).points(70).build();
		
		assertEquals("Supino Test", exercise.getName());
		assertEquals(50, exercise.getWeight(), 0);
		assertEquals(2, exercise.getQuantity(), 0);
		assertEquals(70, exercise.getPoints(), 0);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCreateExerciseWithNameInconsistent() {	
		@SuppressWarnings("unused")
		final Exercise exercise = new Exercise.Builder(null).weight(50).quantity(2).points(70).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateExerciseWithWeightInconsistent() {	
		@SuppressWarnings("unused")
		final Exercise exercise = new Exercise.Builder("Supino").weight(-1).quantity(2).points(70).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateExerciseWithQuantityInconsistent() {	
		@SuppressWarnings("unused")
		final Exercise exercise = new Exercise.Builder("Supino").weight(50).quantity(-1).points(70).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateExerciseWithPointsInconsistent() {	
		@SuppressWarnings("unused")
		final Exercise exercise = new Exercise.Builder("Supino").weight(50).quantity(2).points(-1).build();
	}
	
	@Test
	public void testCreateExerciseWithActivity() {
		
		final Activity actvity = Activity.valueOf("Activity Test");
		actvity.setWeekday(Weekday.SEGUNDA);
		
		final Exercise exercise = new Exercise.Builder("Supino Test").weight(50).quantity(2).points(70).build();
		exercise.addActivity(actvity);
		
		assertEquals("Supino Test", exercise.getName());
		assertEquals(50, exercise.getWeight(), 0);
		assertEquals(2, exercise.getQuantity(), 0);
		assertEquals(70, exercise.getPoints(), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateExerciseWithActivityDuplicated() {
		
		final Activity actvity = Activity.valueOf("Activity Test");
		actvity.setWeekday(Weekday.SEGUNDA);
		
		final Activity actvitySame = Activity.valueOf("Activity Test");
		actvitySame.setWeekday(Weekday.SEGUNDA);
		
		final Exercise exercise = new Exercise.Builder("Supino Test").weight(50).quantity(2).points(70).build();
		exercise.addActivity(actvity);
		exercise.addActivity(actvitySame);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateExerciseWithActivityNull() {
		
		final Exercise exercise = new Exercise.Builder("Supino Test").weight(50).quantity(2).points(70).build();
		exercise.addActivity(null);
	}
}
