/*
 * @(#)ExerciseDeserialize.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.deserializer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.wrapper.ExerciseWrapper;

/**
 * A <code>ExerciseJsonDeserialize</code> e um objeto 
 * de valor utilizado pela <code>ExerciseWrapper</code>
 * e tem por objetivo deserializar um objeto de
 * <code>Exercise</code> ela recebe em seu construto a
 * <code>exerciseDeserialize</code> que tem a mesma funcao.
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
public class ExerciseJsonDeserialize extends AbstractDeserializer<ExerciseWrapper> {
	
	private AbstractDeserializer<Exercise> exerciseDeserialize;
	
	@Autowired
	public ExerciseJsonDeserialize(@Qualifier("exerciseDeserialize") final AbstractDeserializer<Exercise> exerciseDeserialize) {
		super();
		this.exerciseDeserialize = exerciseDeserialize;
	}

	@Override
	public ExerciseWrapper deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
			throws IOException {
		
		final ObjectCodec oc = jsonParser.getCodec();
		final JsonNode node = oc.readTree(jsonParser);
		
		final Exercise exercise = exerciseDeserialize.deserializeNode(node, deserializationContext);
		
		return new ExerciseWrapper(exercise);
	}
}
