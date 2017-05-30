/*
 * @(#)ExerciseRepository.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.domain.exercise;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * A <code>ExerciseRepository</code> tem a responsabilidade
 * de recurperar e persistir da entidade dominio da
 * <code>Exercise</code>
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@RepositoryRestResource(collectionResourceRel = "/springDataExercise", path = "/springDataExercise")
public interface ExerciseRepository extends PagingAndSortingRepository<Exercise, Long> {
	List<Exercise> findAll();
}
