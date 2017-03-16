/*
 * @(#)Exercise.java 1.0 15/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.unittest.exercise;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.Weekday;
import br.com.keepinshape.domain.exercise.Exercise;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 15/03/2017
 */
@RunWith(SpringRunner.class)
public class ExerciseTest {
	
	@Test
	public void testCreateExercise() {
		
		final Exercise exercise = new Exercise.Builder("Supino Test").weight(50).quantity(2).points(70).build();
		
        assertThat("Supino Test").isEqualTo(exercise.getName());
        assertThat(50d).isEqualTo(exercise.getWeight());
        assertThat(2).isEqualTo(exercise.getQuantity());
        assertThat(70d).isEqualTo(exercise.getPoints());
	}
	
	@Test(expected = NullPointerException.class)
	public void testCreateExerciseIncosistData() {	
		@SuppressWarnings("unused")
		final Exercise exercise = new Exercise.Builder(null).weight(50).quantity(2).points(70).build();
	}
	
	@Test
	public void testCreateExerciseWithActivity() {
		
		final Activity actvity = Activity.valueOf("Activity Test");
		actvity.setWeekday(Weekday.SEGUNDA);
		
		final Exercise exercise = new Exercise.Builder("Supino Test").weight(50).quantity(2).points(70).build();
		exercise.addActivity(actvity);
		
		assertThat("Supino Test").isEqualTo(exercise.getName());
        assertThat(50d).isEqualTo(exercise.getWeight());
        assertThat(2).isEqualTo(exercise.getQuantity());
        assertThat(70d).isEqualTo(exercise.getPoints());
	}
	
}
