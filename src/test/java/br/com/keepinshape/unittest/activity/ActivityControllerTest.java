/*
 * @(#)ActivityControllerTest.java 1.0 01/06/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.unittest.activity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
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

import br.com.keepinshape.applicationlayer.ActivityApplicationLayerImpl;
import br.com.keepinshape.config.UnitTest;
import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.Weekday;
import br.com.keepinshape.restapi.ActivityController;
import br.com.keepinshape.restapi.wrapper.ActivityWrapper;

/**
 * A <code>ActivityControllerTest</code> tem por responsabilidade testar a
 * <code>ActivityController</code> ontem a mesma disponibiliza os end-points
 * para usar a aplicacao.
 *
 * @author Joao Batista
 * @version 1.0 01/06/2017
 */
@RunWith(SpringRunner.class)
@Category(UnitTest.class)
public class ActivityControllerTest {

	private ActivityController activityController;

	@Mock
	private ActivityApplicationLayerImpl activityApplicationLayerImpl;

	@Before
	public void setUp() {
		this.activityController = new ActivityController(activityApplicationLayerImpl);
	}

	@Test
	public void testSaveActivity() {

		final Activity activity = Activity.valueOf("Treino A");
		activity.setWeekday(Weekday.SEGUNDA);
		
		
		final Activity activitySaved = Activity.valueOf("Treino A");;
		activitySaved.setWeekday(Weekday.SEGUNDA);
		activitySaved.setId(1l);

		final ActivityWrapper activityWrapper = new ActivityWrapper(activity);

		when(activityApplicationLayerImpl.saveActivity(activity)).thenReturn(activitySaved);

		final ResponseEntity<ActivityWrapper> reponseEntity = activityController.save(activityWrapper);
		final ActivityWrapper activityWrapperResponse = reponseEntity.getBody();

		assertEquals(201, reponseEntity.getStatusCodeValue());
		assertEquals(1, activityWrapperResponse.getId(), 0);
		assertEquals("Treino A", activityWrapperResponse.getName());
		assertEquals(Weekday.SEGUNDA.name(), activityWrapperResponse.getWeekday());
		assertEquals(0, activityWrapperResponse.totalPoints(), 0);
		assertEquals(0, activityWrapperResponse.getExercices().size(), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveActivityNull() {
		
		final ActivityWrapper activityWrapper = new ActivityWrapper(null);
		doThrow(new IllegalArgumentException()).when(activityApplicationLayerImpl).saveActivity(null);
		
		activityController.save(activityWrapper);
	}
	
	@Test
	public void testGetActivityById() {		
		
		final Activity activity = Activity.valueOf("Treino A");;
		activity.setWeekday(Weekday.SEGUNDA);
		activity.setId(1l);

		when(activityApplicationLayerImpl.findById(1l)).thenReturn(activity);

		final ResponseEntity<ActivityWrapper> reponseEntity = activityController.get(1l);
		final ActivityWrapper activityWrapperResponse = reponseEntity.getBody();

		assertEquals(200, reponseEntity.getStatusCodeValue());
		assertEquals(1, activityWrapperResponse.getId(), 0);
		assertEquals("Treino A", activityWrapperResponse.getName());
		assertEquals(Weekday.SEGUNDA.name(), activityWrapperResponse.getWeekday());
		assertEquals(0, activityWrapperResponse.totalPoints(), 0);
		assertEquals(0, activityWrapperResponse.getExercices().size(), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetActivityByIdInvalid() {		
		when(activityApplicationLayerImpl.findById(-1l)).thenCallRealMethod();
		activityController.get(-1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetActivityByIdNull() {		
		when(activityApplicationLayerImpl.findById(null)).thenCallRealMethod();
		activityController.get(null);
	}
	
	@Test
	public void testDeleteActivityById() {		
		
		final ResponseEntity<Class<?>> reponseEntity = activityController.delete(1l);
		assertEquals(200, reponseEntity.getStatusCodeValue());
		verify(activityApplicationLayerImpl, times(1)).deleteActivity(1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteActivityByIdInvalid() {		
		doThrow(new IllegalArgumentException()).when(activityApplicationLayerImpl).deleteActivity(-1l);
		activityController.delete(-1l);
		verify(activityApplicationLayerImpl, times(1)).deleteActivity(-1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteActivityByIdNull() {		
		doThrow(new IllegalArgumentException()).when(activityApplicationLayerImpl).deleteActivity(null);
		activityController.delete(null);
		verify(activityApplicationLayerImpl, times(1)).deleteActivity(null);
	}
	
	
	@Test
	public void testActivityDeleteExercise() {		
		
		final ResponseEntity<Class<?>> reponseEntity = activityController.deleteExercise(1l, 1l);
		assertEquals(200, reponseEntity.getStatusCodeValue());
		verify(activityApplicationLayerImpl, times(1)).deleteActivityExercise(1l, 1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testActivityDeleteExerciseInvalidIdActivity() {		
		
		doThrow(new IllegalArgumentException()).when(activityApplicationLayerImpl).deleteActivityExercise(-1l, 1l);
		activityController.deleteExercise(-1l, 1l);
		verify(activityApplicationLayerImpl, times(1)).deleteActivityExercise(-1l, 1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testActivityDeleteExerciseNullIdActivity() {		
		
		doThrow(new IllegalArgumentException()).when(activityApplicationLayerImpl).deleteActivityExercise(null, 1l);
		activityController.deleteExercise(null, 1l);
		verify(activityApplicationLayerImpl, times(1)).deleteActivityExercise(null, 1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testActivityDeleteExerciseInvalIdExerciseId() {		
		
		doThrow(new IllegalArgumentException()).when(activityApplicationLayerImpl).deleteActivityExercise(1l, -1l);
		activityController.deleteExercise(1l, -1l);
		verify(activityApplicationLayerImpl, times(1)).deleteActivityExercise(1l, -1l);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testActivityDeleteExerciseNullIdExercise() {		
		
		doThrow(new IllegalArgumentException()).when(activityApplicationLayerImpl).deleteActivityExercise(1l, null);
		activityController.deleteExercise(1l, null);
		verify(activityApplicationLayerImpl, times(1)).deleteActivityExercise(1l, null);
	}

}
