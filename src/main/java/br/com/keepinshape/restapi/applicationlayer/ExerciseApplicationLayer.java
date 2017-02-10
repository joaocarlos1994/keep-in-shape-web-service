/*
 * @(#)KeepInShapeApplicationLayer.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.applicationlayer;

import br.com.keepinshape.domain.exercise.Exercise;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
public interface ExerciseApplicationLayer {
	
	public Exercise saveExercise(final Exercise exercise);
	public void delteExercise(final Long idExercise);	
	
}
