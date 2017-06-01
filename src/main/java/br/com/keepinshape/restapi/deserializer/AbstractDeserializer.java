/*
 * @(#)Deserializer.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * A <code>AbstractDeserializer</code> tem por
 * objetivo conter metodos comum a todos deserializer
 * fornecendo assim uma pequena API para suas
 * subclasses.
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
public abstract class AbstractDeserializer<T> extends JsonDeserializer<T> {
	
	public T deserializeNode(final JsonNode jsonNode, DeserializationContext deserializationContext) throws IOException {
		return null;
	}
	
	@Override
	public T deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
		return null;
	}
	
	protected Long getId(final JsonNode node) {
		if (node.has("id")) {
			return node.get("id").asLong();
		}
		return null;
	}
	
	protected Double getDouble(final String field, final JsonNode node) {
		if (node.has(field)) {
			return node.get(field).asDouble();
		}
		return null;
	}
	
	protected String getString(final String field, final JsonNode node) {
		if (node.has(field)) {
			return node.get(field).textValue();
		}
		return null;
	}
	
	protected Integer getInt(final String field, final JsonNode node) {
		if (node.has(field)) {
			return node.get(field).asInt();
		}
		return null;
	}
}
