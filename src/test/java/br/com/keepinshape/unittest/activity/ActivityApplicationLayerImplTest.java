/*
 * @(#)ActivityApplicationLayerImplTest.java 1.0 30/05/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.unittest.activity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.keepinshape.applicationlayer.ActivityApplicationLayerImpl;
import br.com.keepinshape.config.UnitTest;
import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.ActivityRepository;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.domain.exercise.ExerciseRepository;

/**
 * A <code>ActivityApplicationLayerImplTest</code> tem por responsablidade
 * a <code>ActivityApplicationLayerImpl</code> onde a mesma contem alguns
 * metodos de validacoes de campo, como proproposto por Evans.
 *
 * @author Joao Batista
 * @version 1.0 30/05/2017
 */
@RunWith(SpringRunner.class)
@Category(UnitTest.class)
public class ActivityApplicationLayerImplTest {
	
	private ActivityApplicationLayerImpl activityApplicationLayerImpl;
	
	@Mock
	private ActivityRepository activityRepository;
	
	@Mock
	private ExerciseRepository exerciseRepository;
	
	@Before
	public void setUp(){
	    this.activityApplicationLayerImpl = new ActivityApplicationLayerImpl(activityRepository,
	    																	 exerciseRepository);
	}
	
	@Test
	public void testSaveActivity() {
		final Activity newActivity = Activity.valueOf("Treino A");
		final Activity activity = Activity.valueOf("Treino A");
		activity.setId(1l);
		
		when(activityRepository.save(newActivity)).thenReturn(activity);
		final Activity savedActivity = activityApplicationLayerImpl.saveActivity(newActivity);
		
		assertEquals(1l, savedActivity.getId(), 0);
		assertEquals("Treino A", savedActivity.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveActivityExists() {
		final Activity newActivity = Activity.valueOf("Treino A");
		
		final Activity activity = Activity.valueOf("Treino A");
		activity.setId(1l);
		
		when(activityRepository.findAll()).thenReturn(Arrays.asList(activity));
		when(activityRepository.save(newActivity)).thenReturn(activity);
		activityApplicationLayerImpl.saveActivity(newActivity);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveActivityInvalid() {
		activityApplicationLayerImpl.saveActivity(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFindActivityByIdNegative() {		
		activityApplicationLayerImpl.findById(-1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFindActivityByIdZero() {		
		activityApplicationLayerImpl.findById(0l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFindActivityByIdNull() {		
		activityApplicationLayerImpl.findById(null);
	}
	
	@Test
	public void testFindActivityById() {
		final Activity activityMock = Activity.valueOf("Treino A");
		activityMock.setId(1l);
		
		when(activityRepository.findOne(any(Long.class))).thenReturn(activityMock);
		final Activity activity = activityApplicationLayerImpl.findById(1l);
		
		assertEquals(1l, activity.getId(), 0);
		assertEquals("Treino A", activity.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteActivityIdNegative() {		
		activityApplicationLayerImpl.deleteActivity(-1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteActivityIdZero() {		
		activityApplicationLayerImpl.deleteActivity(0l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteActivityIdNull() {		
		activityApplicationLayerImpl.deleteActivity(null);
	}
	
	@Test
	public void testDeleteActivity() {		
		activityApplicationLayerImpl.deleteActivity(1l);
		verify(activityRepository, times(1)).delete(1l);
	}
	
	@Test
	public void testDelteActivityExercise() {
		
		final Exercise exercise = new Exercise.Builder("Supino").weight(50).quantity(2).points(70).build();
		exercise.setId(1l);
		
		final Activity activity = Activity.valueOf("Treino A");
		activity.setId(1l);
		activity.addExercise(exercise);
		
		when(exerciseRepository.findOne(1l)).thenReturn(exercise);
		when(activityApplicationLayerImpl.findById(1l)).thenReturn(activity);
		
		activityApplicationLayerImpl.deleteActivityExercise(1l, 1l);
		assertEquals(1l, activity.getId(), 0);
		assertEquals(0, activity.getExercises().size(), 0);
		verify(exerciseRepository, times(1)).findOne(1l);
		verify(activityRepository, times(1)).findOne(1l);
		verify(activityRepository, times(1)).save(any(Activity.class));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithActivityInvalid() {		
		activityApplicationLayerImpl.deleteActivityExercise(-1l, 1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithIdActivityZero() {		
		activityApplicationLayerImpl.deleteActivityExercise(0l, 1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithActivityNull() {		
		activityApplicationLayerImpl.deleteActivityExercise(null, 1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithExerciseInvalid() {		
		activityApplicationLayerImpl.deleteActivityExercise(1l, -1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithExerciseIdZero() {		
		activityApplicationLayerImpl.deleteActivityExercise(1l, 0l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithExerciseNull() {		
		activityApplicationLayerImpl.deleteActivityExercise(1l, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithIDsZero() {		
		activityApplicationLayerImpl.deleteActivityExercise(0l, 0l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithActivityNegativeAndExerciseNull() {		
		activityApplicationLayerImpl.deleteActivityExercise(-1l, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithActivityNullAndExerciseZero() {		
		activityApplicationLayerImpl.deleteActivityExercise(null, 0l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithActivityNullAndExerciseNegative() {		
		activityApplicationLayerImpl.deleteActivityExercise(null, -1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithIDsNull() {		
		activityApplicationLayerImpl.deleteActivityExercise(null, null);
	}
}
