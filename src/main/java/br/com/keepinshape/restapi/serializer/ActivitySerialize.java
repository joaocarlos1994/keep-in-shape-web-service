/*
 * @(#)ActivitySerialize.java 1.0 06/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.serializer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.wrapper.ActivityWrapper;

/**
 * A <code>ActivitySerialize</code> tem por
 * objetivo serializar um objeto da <code>Activity</code>
 * e devolver ele ao front-end.
 *
 * @author Joao Batista
 * @version 1.0 06/03/2017
 */
public class ActivitySerialize extends JsonSerializer<ActivityWrapper> {
	
	private final JsonSerializer<Exercise> exerciseSerialize;

	@Autowired
	public ActivitySerialize(final JsonSerializer<Exercise> exerciseSerialize) {
		super();
		this.exerciseSerialize = exerciseSerialize;
	}

	@Override
	public void serialize(final ActivityWrapper activityWrapper, final JsonGenerator jsonGenerator, final SerializerProvider serializers)
			throws IOException {
		
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", activityWrapper.getId());
		jsonGenerator.writeStringField("name", activityWrapper.getName());
		jsonGenerator.writeStringField("weekDay", activityWrapper.getWeekday());
		jsonGenerator.writeNumberField("totalPoints", activityWrapper.totalPoints());
		jsonGenerator.writeFieldName("exercices");
		
		jsonGenerator.writeStartArray();
		for (final Exercise exercise : activityWrapper.getExercices()) {
			exerciseSerialize.serialize(exercise, jsonGenerator, serializers);
		}
		jsonGenerator.writeEndArray();
		
		jsonGenerator.writeEndObject();
		
	}
}
