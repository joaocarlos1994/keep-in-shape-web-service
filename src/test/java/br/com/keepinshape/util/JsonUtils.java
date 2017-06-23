/*
 * @(#)JsonConvert.java 1.0 12/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 12/03/2017
 */
public class JsonUtils {

	/**
	 * Metodo pega um wrapper e transforma em um objeto json.
	 * 
	 * @param object
	 *            Um objeto wrapper usado para serializer
	 * @throws JsonProcessingException
	 *             Erro no processo de serializer do wrapper passado
	 */
	public static String convertObjectToJson(final Object object) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	public static JsonNode getNode(final String result, final String nodeFind) {
		try {
			final JsonNode rootNode = new ObjectMapper().readTree(result);
			final JsonNode node = rootNode.findValue(nodeFind);
			return node;
		} catch (final IOException e) {
			final String a = e.getMessage() + "Json: " + result;
			throw new RuntimeException(a);
		}
	}
}
