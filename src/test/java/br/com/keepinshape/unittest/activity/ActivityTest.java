/*
 * @(#)ActivityTest.java 1.0 29/05/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.unittest.activity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.keepinshape.config.UnitTest;
import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.exercise.Exercise;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 29/05/2017
 */
@RunWith(SpringRunner.class)
@Category(UnitTest.class)
public class ActivityTest {
	
	@Test
	public void testCreateActivityWithoutExercise() {
		
		final Activity activity = Activity.valueOf("Treino A");
		assertEquals("Treino A", activity.getName());
	}
	
	@Test(expected = NullPointerException.class)
	public void testCreateActivityInvalidName() {
		
		final Activity activity = Activity.valueOf(null);
		assertEquals("Treino A", activity.getName());
	}
	
	@Test
	public void testCreateActivityWithExercise() {
		
		final Exercise exercise = new Exercise.Builder("Supino").weight(30).quantity(3).points(30).build();
		final Activity activity = Activity.valueOf("Treino A");
		activity.addExercise(exercise);
		
		assertEquals("Treino A", activity.getName());
		assertEquals(30, activity.totalPoints(), 0);
		assertEquals("Supino", activity.getExercises().get(0).getName());
		assertEquals(30, activity.getExercises().get(0).getWeight(), 0);
		assertEquals(3, activity.getExercises().get(0).getQuantity(), 0);
		assertEquals(30, activity.getExercises().get(0).getPoints(), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateActivityWithExerciseDuplicated() {
		
		final Exercise exercise = new Exercise.Builder("Supino").weight(30).quantity(3).points(30).build();
		final Exercise exerciseSame = new Exercise.Builder("Supino").weight(30).quantity(3).points(30).build();
		
		final Activity activity = Activity.valueOf("Treino A");
		activity.addExercise(exercise);
		activity.addExercise(exerciseSame);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateActivityWithExerciseInvalid() {
		
		final Activity activity = Activity.valueOf("Treino A");
		activity.addExercise(null);
	}
	
	@Test
	public void testCreateActivityWithExerciseAndRemoveExercise() {
		
		final Exercise exercise = new Exercise.Builder("Supino").weight(30).quantity(3).points(30).build();
		final Activity activity = Activity.valueOf("Treino A");
		activity.addExercise(exercise);
		
		assertEquals("Treino A", activity.getName());
		assertEquals(30, activity.totalPoints(), 0);
		assertEquals("Supino", activity.getExercises().get(0).getName());
		assertEquals(30, activity.getExercises().get(0).getWeight(), 0);
		assertEquals(3, activity.getExercises().get(0).getQuantity(), 0);
		assertEquals(30, activity.getExercises().get(0).getPoints(), 0);
		
		activity.removeExercise(exercise);
		
		assertEquals("Treino A", activity.getName());
		assertEquals(0, activity.getExercises().size(), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateActivityAndRemoveExerciseNotExists() {
		
		final Exercise exercise = new Exercise.Builder("Supino").weight(30).quantity(3).points(30).build();
		final Exercise exerciseRemove = new Exercise.Builder("Supino Remove").weight(35).quantity(3).points(40).build();
		
		final Activity activity = Activity.valueOf("Treino A");
		activity.addExercise(exercise);
		
		activity.removeExercise(exerciseRemove);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateActivityAndRemoveExerciseWithInconsistentData() {
		final Activity activity = Activity.valueOf("Treino A");
		activity.removeExercise(null);
	}
	
}
