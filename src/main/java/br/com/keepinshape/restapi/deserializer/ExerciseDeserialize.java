/*
 * @(#)ExerciseDeserialize.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.deserializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.keepinshape.domain.exercise.Exercise;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
@Component
public class ExerciseDeserialize extends AbstractDeserializer<Exercise> {

	@Override
	public Exercise deserializeNode(final JsonNode jsonNode, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		
		final Exercise exercise = new Exercise.Builder(jsonNode.get("name").asText())
										.weight(jsonNode.get("weight").asDouble())
										.quantity(jsonNode.get("quantity").asInt())
										.points(jsonNode.get("points").asDouble())
										.build();
				exercise.setId(getId(jsonNode));
		
		return exercise;
	}

}
