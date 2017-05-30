/*
 * @(#)KeepInShapeApplicationLayer.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.applicationlayer;

import br.com.keepinshape.domain.exercise.Exercise;

/**
 * A <code>ExerciseApplicationLayer</code> tem por finalidade
 * expor alguns metodo comuns para <code>Exercise</code>,
 * como por exemplo save e deleteExercise.
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
public interface ExerciseApplicationLayer {
	
	public Exercise saveExercise(final Exercise exercise);
	public void deleteExercise(final Long idExercise);	
	
}
