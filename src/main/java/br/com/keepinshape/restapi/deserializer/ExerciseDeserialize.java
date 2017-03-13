/*
 * @(#)ExerciseDeserialize.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.deserializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.keepinshape.domain.exercise.Exercise;

/**
 * A <code>ExerciseDeserialize</code> tem por objetivo
 * deserializar um objeto json que representa a 
 * <code>Exercise</code>.
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
@Component
public class ExerciseDeserialize extends AbstractDeserializer<Exercise> {

	@Override
	public Exercise deserializeNode(final JsonNode jsonNode, DeserializationContext deserializationContext)
			throws IOException {
		
		final Exercise exercise = new Exercise.Builder(getString("name", jsonNode))
										.weight(getDouble("weight", jsonNode))
										.quantity(getInt("quantity", jsonNode))
										.points(getDouble("points", jsonNode))
										.build();
				exercise.setId(getId(jsonNode));
		
		return exercise;
	}

}
