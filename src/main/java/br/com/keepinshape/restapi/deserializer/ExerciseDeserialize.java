/*
 * @(#)ExerciseDeserialize.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.wrapper.ExerciseWrapper;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
public class ExerciseDeserialize extends JsonDeserializer<ExerciseWrapper> {

	/** {@inheritDoc} */
	@Override
	public ExerciseWrapper deserialize(final JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		
		final ObjectCodec oc = jsonParser.getCodec();
		final JsonNode node = oc.readTree(jsonParser);
		
		final Exercise exercise = new Exercise.Builder(node.get("name").asText())
										.weight(node.get("weight").asDouble())
										.quantity(node.get("quantity").asInt())
										.points(node.get("points").asDouble())
										.build();
		
		return new ExerciseWrapper(exercise);
	}

}
