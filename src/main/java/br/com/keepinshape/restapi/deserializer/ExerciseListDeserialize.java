/*
 * @(#)ExerciseListDeserialize.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.keepinshape.domain.exercise.Exercise;

/**
 * A <code>ExerciseListDeserialize</code> tem por
 * objetivo serializar um lista da <code>Exercise</code>
 * para ser devolvida ao front-end.
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
@Component
public class ExerciseListDeserialize extends AbstractDeserializer<List<Exercise>> {
	
	private AbstractDeserializer<Exercise> exerciseDeserialize;
	
	public ExerciseListDeserialize(@Qualifier("exerciseDeserialize") final AbstractDeserializer<Exercise> exerciseDeserialize) {
		super();
		this.exerciseDeserialize = exerciseDeserialize;
	}

	@Override
	public List<Exercise> deserializeNode(final JsonNode jsonNode, final DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		
		final List<Exercise> exercises = new ArrayList<>();
		
		final Iterator<JsonNode> iteratorExercise = jsonNode.iterator();
		while(iteratorExercise.hasNext()) {
			final JsonNode exerciseNode = iteratorExercise.next();
			final Exercise exercise = exerciseDeserialize.deserializeNode(exerciseNode, deserializationContext);
			exercises.add(exercise);
		}
		return Collections.unmodifiableList(exercises);
	}
	
}
