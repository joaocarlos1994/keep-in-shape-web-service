/*
 * @(#)Deserializer.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
public abstract class AbstractDeserializer<T> extends JsonDeserializer<T> {
	
	public T deserializeNode(final JsonNode jsonNode, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		return null;
		
	}
	
	@Override
	public T deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		return null;
	}
	
	protected Long getId(final JsonNode node) {
		if (node.has("id")) {
			return node.get("id").asLong();
		}
		return null;
	}
	
}
