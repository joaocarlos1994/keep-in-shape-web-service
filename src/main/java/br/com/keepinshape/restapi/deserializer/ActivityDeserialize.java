/*
 * @(#)ActivityJsonDeserialize.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.deserializer;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.Weekday;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.wrapper.ActivityWrapper;

/**
 * A <code>ActivityDeserialize</code> tem por objetivo
 * deserializar um objeto que representa um objeto
 * da <code>ActivityDeserialize</code>, ela recebe em
 * seu construtor a <code>ExerciseListDeserialize</code>
 * para realizar a deserializacao da <code>Exercise</code>.
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
public class ActivityDeserialize extends AbstractDeserializer<ActivityWrapper> {
	
	private final AbstractDeserializer<List<Exercise>> exerciseListDeserialize;
	
	@Autowired
	public ActivityDeserialize(@Qualifier("exerciseListDeserialize") AbstractDeserializer<List<Exercise>> exerciseListDeserialize) {
		super();
		this.exerciseListDeserialize = exerciseListDeserialize;
	}

	@Override
	public ActivityWrapper deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
			throws IOException {
		
		final ObjectCodec oc = jsonParser.getCodec();
		final JsonNode node = oc.readTree(jsonParser);
		
		final Activity activity = Activity.valueOf(node.get("name").asText());
		activity.setId(getId(node));
		activity.setWeekday(Weekday.valueOf(node.get("weekday").asText()));
		
		final List<Exercise> exercises = exerciseListDeserialize.deserializeNode(node.get("exercises"), deserializationContext);
		
		exercises.forEach(e -> activity.addExercise(e));
		
		return new ActivityWrapper(activity);
	}
}
