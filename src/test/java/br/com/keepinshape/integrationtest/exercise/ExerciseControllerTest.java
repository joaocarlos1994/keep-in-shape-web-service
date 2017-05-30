/*
 * @(#)ExerciseControllerTest.java 1.0 07/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.integrationtest.exercise;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.keepinshape.KeepinshapeWebserviceApplication;
import br.com.keepinshape.config.DbEnvironment;
import br.com.keepinshape.config.IntegrationTest;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.util.JsonUtils;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 07/03/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KeepinshapeWebserviceApplication.class, DbEnvironment.class})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@Category(IntegrationTest.class)
public class ExerciseControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		final DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	@Test
	public void testPostNewExercise() throws Exception {
		final Exercise exercise = new Exercise.Builder("Supino Test Save").weight(30).quantity(3).points(50).build();
		final String result = this.mockMvc.perform(post("/rest/exercise").content(JsonUtils.convertObjectToJson(exercise)).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse().getContentAsString();
		
		assertEquals(exercise.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(exercise.getWeight(), JsonUtils.getNode(result, "weight").asDouble(), 0);
		assertEquals(exercise.getQuantity(), JsonUtils.getNode(result, "quantity").asInt(), 0);
		assertEquals(exercise.getPoints(), JsonUtils.getNode(result, "points").asDouble(), 0);
	}
	
	@Test(expected = Exception.class)
	public void testPostNewExerciseDataIncosist() throws Exception {
		final String exercise = "{\"id\":null,\"name\":null,\"weight\":30.0,\"quantity\":3,\"points\":50.0}";
		this.mockMvc.perform(post("/rest/exercise").content(exercise).contentType(MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	public void testPostExerciseExists() throws Exception {
		
		final String getResult = this.mockMvc.perform(get("/springDataExercise/1")).andReturn().getResponse().getContentAsString();
		final Exercise exercise = new Exercise.Builder("TESTE UPDATE").weight(50).quantity(2).points(50).build();
		exercise.setId(new Long(1L));
		final String postResult = this.mockMvc.perform(post("/rest/exercise").content(JsonUtils.convertObjectToJson(exercise)).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse().getContentAsString();
		
		assertEquals("TESTE", JsonUtils.getNode(getResult, "name").asText());
		assertEquals(30, JsonUtils.getNode(getResult, "weight").asDouble(), 0);
		assertEquals(3, JsonUtils.getNode(getResult, "quantity").asInt(), 0);
		assertEquals(30, JsonUtils.getNode(getResult, "points").asDouble(), 0);
		assertEquals(exercise.getName(), JsonUtils.getNode(postResult, "name").asText());
		assertEquals(exercise.getWeight(), JsonUtils.getNode(postResult, "weight").asDouble(), 0);
		assertEquals(exercise.getQuantity(), JsonUtils.getNode(postResult, "quantity").asInt(), 0);
		assertEquals(exercise.getPoints(), JsonUtils.getNode(postResult, "points").asDouble(), 0);
	}
	
	@Test(expected = Exception.class)
	public void testPostExerciseExistsDataIncosist() throws Exception {
		final String exercise = "{\"id\":1,\"name\":null,\"weight\":50.0,\"quantity\":2,\"points\":50.0}";
		this.mockMvc.perform(post("/rest/exercise").content(JsonUtils.convertObjectToJson(exercise)).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void testGetSpringDataExercise() throws Exception {
		
		final String getResult = this.mockMvc.perform(get("/springDataExercise/1")).andReturn().getResponse().getContentAsString();
		
		assertEquals("TESTE", JsonUtils.getNode(getResult, "name").asText());
		assertEquals(30, JsonUtils.getNode(getResult, "weight").asDouble(), 0);
		assertEquals(3, JsonUtils.getNode(getResult, "quantity").asInt(), 0);
		assertEquals(30, JsonUtils.getNode(getResult, "points").asDouble(), 0);
	}
	
	@Test
	public void testGetExerciseNotFund() throws Exception {
		final MvcResult getResult = this.mockMvc.perform(get("/springDataExercise/100")).andReturn();		
		assertEquals(404, getResult.getResponse().getStatus());
	}
	
	@Test
	public void testDeleteExercise() throws Exception {
		final MvcResult getResult = this.mockMvc.perform(delete("/rest/exercise/3")).andReturn();		
		assertEquals(200, getResult.getResponse().getStatus());
	}
	
	@Test(expected = Exception.class)
	public void testDeleteExerciseNotFound() throws Exception {
		this.mockMvc.perform(delete("/rest/exercise/0")).andReturn();		
	}
}
