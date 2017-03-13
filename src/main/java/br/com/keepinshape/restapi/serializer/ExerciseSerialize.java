/*
 * @(#)ExerciseSerialize.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.keepinshape.domain.exercise.Exercise;

/**
 * A <code>ExerciseSerialize</code> tem por responsabilidade
 * serializar um objeto da <code>Exercise</code> em objeto
 * json.
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@Component
public class ExerciseSerialize extends JsonSerializer<Exercise> {

	@Override
	public void serialize(final Exercise exercise, final JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", exercise.getId());
		jsonGenerator.writeStringField("name", exercise.getName());
		jsonGenerator.writeNumberField("weight", exercise.getWeight());
		jsonGenerator.writeNumberField("quantity", exercise.getQuantity());
		jsonGenerator.writeNumberField("points", exercise.getPoints());
		jsonGenerator.writeEndObject();
		
	}
}
