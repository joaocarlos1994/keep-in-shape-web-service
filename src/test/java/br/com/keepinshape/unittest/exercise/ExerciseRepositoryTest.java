/*
 * @(#)ExerciseRepositoryTest.java 1.0 14/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.unittest.exercise;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.keepinshape.KeepinshapeWebserviceApplication;
import br.com.keepinshape.config.DbEnvironmentUnitTest;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.domain.exercise.ExerciseRepository;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 14/03/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KeepinshapeWebserviceApplication.class, DbEnvironmentUnitTest.class})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ExerciseRepositoryTest {
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Test
    public void testSaveExercise() throws Exception {
		
		final Exercise exercise = new Exercise.Builder("Supino Test Save").weight(50).quantity(2).points(70).build();
        final Exercise saveExercise = this.exerciseRepository.save(exercise);
        
        assertThat(4L).isEqualTo(saveExercise.getId());
        assertThat(exercise.getName()).isEqualTo(saveExercise.getName());
        assertThat(exercise.getWeight()).isEqualTo(saveExercise.getWeight());
        assertThat(exercise.getQuantity()).isEqualTo(saveExercise.getQuantity());
        assertThat(exercise.getPoints()).isEqualTo(saveExercise.getPoints());
    }
	
	@Test(expected = NullPointerException.class)
    public void testSaveExerciseInconsistData() throws Exception {
		
		final Exercise exercise = new Exercise.Builder(null).weight(50).quantity(2).points(70).build();
        this.exerciseRepository.save(exercise);
    }
	
	@Test
    public void testUpdateExercise() throws Exception {
		
		final Exercise exercise = new Exercise.Builder("Supino Update").weight(20).quantity(3).points(30).build();
        exercise.setId(new Long(1L));
        
		final Exercise updateExercise = this.exerciseRepository.save(exercise);
        assertThat(1L).isEqualTo(updateExercise.getId());
        assertThat(exercise.getName()).isEqualTo(updateExercise.getName());
        assertThat(exercise.getWeight()).isEqualTo(updateExercise.getWeight());
        assertThat(exercise.getQuantity()).isEqualTo(updateExercise.getQuantity());
        assertThat(exercise.getPoints()).isEqualTo(updateExercise.getPoints());
    }
	
	@Test(expected = NullPointerException.class)
    public void testUpdateExerciseInconsistData() throws Exception {
		
		final Exercise exercise = new Exercise.Builder(null).weight(20).quantity(3).points(30).build();
        exercise.setId(new Long(1L));
        
		this.exerciseRepository.save(exercise);
    }
	
	
	@Test
    public void testFindById() throws Exception {
		
		final Exercise exercise = new Exercise.Builder("TESTE FIND ID").weight(50).quantity(3).points(100).build();
		final Exercise expctedExercise = this.exerciseRepository.findOne(new Long(2L));
		
        assertThat(2L).isEqualTo(expctedExercise.getId());
        assertThat(exercise.getName()).isEqualTo(expctedExercise.getName());
        assertThat(exercise.getWeight()).isEqualTo(expctedExercise.getWeight());
        assertThat(exercise.getQuantity()).isEqualTo(expctedExercise.getQuantity());
        assertThat(exercise.getPoints()).isEqualTo(expctedExercise.getPoints());
    }
	
	@Test
    public void testFindByIdNotFounf() throws Exception {
		
		final Exercise expctedExercise = this.exerciseRepository.findOne(new Long(200L));
        assertEquals(null, expctedExercise);
    }
	
	@Test
    public void testDeleteExercise() throws Exception {
		this.exerciseRepository.delete(3L);
		final long countExercise = exerciseRepository.count();
		assertThat(3L).isEqualTo(countExercise);
    }
	
	@Test(expected = Exception.class)
    public void testDeleteNotFoundExercise() throws Exception {
		this.exerciseRepository.delete(200L);
    }

}
