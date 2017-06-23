/*
 * @(#)ExerciseControllerTest.java 1.0 07/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.integrationtest.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
 * A <code>ExerciseControllerTest</code> e responsavel por testar os end-points
 * exposto pelo keep-in-shape, em especialmente ela testa todos end-points do
 * aggregated <code>Exercise</code>.
 * 
 * @author Joao Batista
 * @version 1.0 07/03/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { KeepinshapeWebserviceApplication.class, DbEnvironment.class })
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
		final Exercise exercise = new Exercise.Builder("Supino Test Save").weight(30l).quantity(3).points(50l).build();
		final String result = this.mockMvc.perform(post("/rest/exercise")
				.content(JsonUtils.convertObjectToJson(exercise)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getContentAsString();

		assertEquals(exercise.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(exercise.getWeight(), JsonUtils.getNode(result, "weight").asDouble(), 0);
		assertEquals(exercise.getQuantity(), JsonUtils.getNode(result, "quantity").asInt(), 0);
		assertEquals(exercise.getPoints(), JsonUtils.getNode(result, "points").asDouble(), 0);
	}

	@Test
	public void testPostNewExerciseDataIncosist() throws Exception {
		final String exercise = "{\"id\":null,\"name\":null,\"weight\":30.0,\"quantity\":3,\"points\":50.0}";
		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/exercise").content(exercise).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Name is null", message);
	}
	
	@Test
	public void testPostNewExerciseWithWeightZero() throws Exception {
		final String exercise = "{\"id\":null,\"name\":\"Supino Save\",\"weight\":0,\"quantity\":3,\"points\":50.0}";
		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/exercise").content(exercise).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Weight is invalid", message);
	}
	
	@Test
	public void testPostNewExerciseWithWeightNegative() throws Exception {
		final String exercise = "{\"id\":null,\"name\":\"Supino Save\",\"weight\":-1,\"quantity\":3,\"points\":50.0}";
		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/exercise").content(exercise).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Weight is invalid", message);
	}
	
	@Test
	public void testPostNewExerciseWithQuantityZero() throws Exception {
		final String exercise = "{\"id\":null,\"name\":\"Supino Save\",\"weight\":30.0,\"quantity\":0,\"points\":50.0}";
		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/exercise").content(exercise).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Quantity is invalid", message);
	}
	
	@Test
	public void testPostNewExerciseWithQuantityNegative() throws Exception {
		final String exercise = "{\"id\":null,\"name\":\"Supino Save\",\"weight\":30.0,\"quantity\":-1,\"points\":50.0}";
		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/exercise").content(exercise).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Quantity is invalid", message);
	}
	
	@Test
	public void testPostNewExerciseWithPointsZero() throws Exception {
		final String exercise = "{\"id\":null,\"name\":\"Supino Save\",\"weight\":30.0,\"quantity\":3,\"points\":0}";
		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/exercise").content(exercise).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Points is invalid", message);
	}
	
	@Test
	public void testPostNewExerciseWithPointsNegative() throws Exception {
		final String exercise = "{\"id\":null,\"name\":\"Supino Save\",\"weight\":30.0,\"quantity\":3,\"points\":-1}";
		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/exercise").content(exercise).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Points is invalid", message);
	}

	@Test
	public void testPostUpdateExerciseExists() throws Exception {

		final String getResult = this.mockMvc.perform(get("/springDataExercise/2")).andReturn().getResponse()
				.getContentAsString();
		final Exercise exercise = new Exercise.Builder("TESTE UPDATE EXERCISE").weight(70l).quantity(2).points(100l)
				.build();
		exercise.setId(new Long(2L));
		final String postResult = this.mockMvc.perform(post("/rest/exercise")
				.content(JsonUtils.convertObjectToJson(exercise)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getContentAsString();

		assertEquals("TESTE UPDATE", JsonUtils.getNode(getResult, "name").asText());
		assertEquals(40, JsonUtils.getNode(getResult, "weight").asDouble(), 0);
		assertEquals(2, JsonUtils.getNode(getResult, "quantity").asInt(), 0);
		assertEquals(50, JsonUtils.getNode(getResult, "points").asDouble(), 0);
		assertEquals(exercise.getName(), JsonUtils.getNode(postResult, "name").asText());
		assertEquals(exercise.getWeight(), JsonUtils.getNode(postResult, "weight").asDouble(), 0);
		assertEquals(exercise.getQuantity(), JsonUtils.getNode(postResult, "quantity").asInt(), 0);
		assertEquals(exercise.getPoints(), JsonUtils.getNode(postResult, "points").asDouble(), 0);
	}

	@Test
	public void testPostExerciseExistsDataIncosist() throws Exception {
		final String exercise = "{\"id\":1,\"name\":null,\"weight\":50.0,\"quantity\":2,\"points\":50.0}";
		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/exercise").content(JsonUtils.convertObjectToJson(exercise))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Name is null", message);
	}

	@Test
	public void testGetSpringDataExercise() throws Exception {

		final String getResult = this.mockMvc.perform(get("/springDataExercise/1")).andReturn().getResponse()
				.getContentAsString();

		assertEquals("TESTE", JsonUtils.getNode(getResult, "name").asText());
		assertEquals(30, JsonUtils.getNode(getResult, "weight").asDouble(), 0);
		assertEquals(3, JsonUtils.getNode(getResult, "quantity").asInt(), 0);
		assertEquals(30, JsonUtils.getNode(getResult, "points").asDouble(), 0);
	}

	@Test
	public void testPostExerciseExists() throws Exception {
		final Exercise exercise = new Exercise.Builder("TESTE").weight(30l).quantity(3).points(30l).build();
		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/exercise")
				.content(JsonUtils.convertObjectToJson(exercise)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		final String message = mvcResult.getResponse().getContentAsString();

		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Already exists exercise", message);
	}

	@Test
	public void testGetExerciseNotFund() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get("/springDataExercise/100")).andReturn();
		assertEquals(404, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testDeleteExercise() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(delete("/rest/exercise/3")).andReturn();
		final String messageBody = mvcResult.getResponse().getContentAsString();
		final String message = this.mockMvc.perform(get("/springDataExercise")).andReturn().getResponse()
				.getContentAsString();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals("\"void\"", messageBody);
		assertNotNull(messageBody);
		assertEquals(2, JsonUtils.getNode(message, "totalElements").asLong());
	}

	@Test
	public void testDeleteExerciseNotFound() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(delete("/rest/exercise/0")).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();

		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Id is invalid", message);
	}

	@Test
	public void testDeleteExerciseNotFoundWithIdNegative() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(delete("/rest/exercise/-1")).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();

		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Id is invalid", message);
	}
}
