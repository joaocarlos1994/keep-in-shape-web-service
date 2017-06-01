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
	public void testSaveActivityInvalid() {
		activityApplicationLayerImpl.saveActivity(null);
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
	public void testFindActivityByIdInvalid() {		
		activityApplicationLayerImpl.findById(-1l);
	}
	
	@Test
	public void testDeleteActivity() {		
		activityApplicationLayerImpl.delteActivity(1l);
		verify(activityRepository, times(1)).delete(1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteActivityIdInvalid() {		
		activityApplicationLayerImpl.delteActivity(-1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteActivityIdNull() {		
		activityApplicationLayerImpl.delteActivity(null);
	}
	
	@Test
	public void testdelteActivityExercise() {
		
		final Exercise exercise = new Exercise.Builder("Supino").weight(50).quantity(2).points(70).build();
		exercise.setId(1l);
		
		final Activity activity = Activity.valueOf("Treino A");
		activity.setId(1l);
		activity.addExercise(exercise);
		
		when(exerciseRepository.findOne(1l)).thenReturn(exercise);
		when(activityApplicationLayerImpl.findById(1l)).thenReturn(activity);
		
		activityApplicationLayerImpl.delteActivityExercise(1l, 1l);
		assertEquals(1l, activity.getId(), 0);
		assertEquals(0, activity.getExercises().size(), 0);
		verify(exerciseRepository, times(1)).findOne(1l);
		verify(activityRepository, times(1)).findOne(1l);
		verify(activityRepository, times(1)).save(any(Activity.class));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithActivityInvalid() {		
		activityApplicationLayerImpl.delteActivityExercise(-1l, 1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithActivityNull() {		
		activityApplicationLayerImpl.delteActivityExercise(null, 1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithExerciseInvalid() {		
		activityApplicationLayerImpl.delteActivityExercise(1l, -1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testdelteActivityExerciseWithExerciseNull() {		
		activityApplicationLayerImpl.delteActivityExercise(1l, null);
	}
}
