/*
 * @(#)ExerciseSerialize.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.keepinshape.restapi.wrapper.ExerciseWrapper;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
public class ExerciseSerialize extends JsonSerializer<ExerciseWrapper> {

	@Override
	public void serialize(final ExerciseWrapper exerciseWrapper, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", exerciseWrapper.getId());
		jsonGenerator.writeStringField("name", exerciseWrapper.getName());
		jsonGenerator.writeNumberField("weight", exerciseWrapper.getWeight());
		jsonGenerator.writeNumberField("quantity", exerciseWrapper.getQuantity());
		jsonGenerator.writeNumberField("points", exerciseWrapper.getPoints());
		jsonGenerator.writeEndObject();
		
	}

}
