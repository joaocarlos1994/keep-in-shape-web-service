/*
 * @(#)ActivityControllerTest.java 1.0 12/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.activity;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
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

import com.fasterxml.jackson.databind.JsonNode;

import br.com.keepinshape.KeepinshapeWebserviceApplication;
import br.com.keepinshape.config.DbEnvironment;
import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.Weekday;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.util.JsonUtils;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 12/03/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KeepinshapeWebserviceApplication.class, DbEnvironment.class})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ActivityControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		final DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}
	
	@Test
	public void testPostNewActivityNothingExercise() throws Exception {
		final Activity newActvity = Activity.valueOf("Test Save");
		newActvity.setWeekday(Weekday.SEGUNDA);
		this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(newActvity)).contentType(MediaType.APPLICATION_JSON_VALUE));
		final String result = this.mockMvc.perform(get("/springDataActivity/4")).andReturn().getResponse().getContentAsString();
		
		assertEquals(newActvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(newActvity.getWeekday().name(), JsonUtils.getNode(result, "weekday").asText());
	}
	
	@Test
	public void testPostExistsActivityNothingExercise() throws Exception {
		final Activity newActvity = Activity.valueOf("TESTE ACTIVITY");
		newActvity.setWeekday(Weekday.TERÇA);
		newActvity.setId(new Long(1L));
		
		this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(newActvity)).contentType(MediaType.APPLICATION_JSON_VALUE));
		final String result = this.mockMvc.perform(get("/springDataActivity/1")).andReturn().getResponse().getContentAsString();
		
		assertEquals(newActvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(newActvity.getWeekday().name(), JsonUtils.getNode(result, "weekday").asText());
	}
	
	@Test
	public void testPostNewActivityWithExercise() throws Exception {
		
		final Exercise exercise = new Exercise.Builder("TESTE").weight(30).quantity(3).points(30).build();
		exercise.setId(new Long(1L));
		
		final Activity newActvity = Activity.valueOf("Test Save");
		newActvity.setWeekday(Weekday.SEGUNDA);
		newActvity.addExercise(exercise);
		
		this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(newActvity)).contentType(MediaType.APPLICATION_JSON_VALUE));
		final String result = this.mockMvc.perform(get("/rest/activity/4")).andReturn().getResponse().getContentAsString();
		final JsonNode exerciseNodeArray = JsonUtils.getNode(result, "exercices");
		final JsonNode exerciseNode = exerciseNodeArray.get(0);
		
		assertEquals(newActvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(newActvity.getWeekday().name(), JsonUtils.getNode(result, "weekDay").asText());
		assertEquals(newActvity.totalPoints(), JsonUtils.getNode(result, "totalPoints").asDouble(), 0);
		assertEquals(exercise.getId(), JsonUtils.getNode(exerciseNode.toString(), "id").asLong(), 0);
		assertEquals(exercise.getName(), JsonUtils.getNode(exerciseNode.toString(), "name").asText());
		assertEquals(exercise.getWeight(), JsonUtils.getNode(exerciseNode.toString(), "weight").asDouble(), 0);
		assertEquals(exercise.getQuantity(), JsonUtils.getNode(exerciseNode.toString(), "quantity").asInt(), 0);
		assertEquals(exercise.getPoints(), JsonUtils.getNode(exerciseNode.toString(), "points").asDouble(), 0);
	}
	
	@Test
	public void testPostExistActivityWithExercise() throws Exception {
		
		final Exercise exercise = new Exercise.Builder("TESTE").weight(30).quantity(3).points(30).build();
		exercise.setId(new Long(1L));
		
		final Exercise exercise2 = new Exercise.Builder("TESTE UPDATE").weight(40).quantity(2).points(50).build();
		exercise2.setId(new Long(2L));
		
		final Activity actvity = Activity.valueOf("TESTE ACTIVITY");
		actvity.setWeekday(Weekday.TERÇA);
		actvity.addExercise(exercise);
		actvity.addExercise(exercise2);
		actvity.setId(new Long(2L));
		
		
		this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(actvity)).contentType(MediaType.APPLICATION_JSON_VALUE));
		final String result = this.mockMvc.perform(get("/rest/activity/2")).andReturn().getResponse().getContentAsString();
		final JsonNode exerciseNodeArray = JsonUtils.getNode(result, "exercices");
		final JsonNode exerciseNodeOne = exerciseNodeArray.get(0);
		final JsonNode exerciseNodeTwo = exerciseNodeArray.get(1);
		
		assertEquals(actvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(actvity.getWeekday().name(), JsonUtils.getNode(result, "weekDay").asText());
		assertEquals(actvity.totalPoints(), JsonUtils.getNode(result, "totalPoints").asDouble(), 0);
		
		assertEquals(exercise.getId(), JsonUtils.getNode(exerciseNodeOne.toString(), "id").asLong(), 0);
		assertEquals(exercise.getName(), JsonUtils.getNode(exerciseNodeOne.toString(), "name").asText());
		assertEquals(exercise.getWeight(), JsonUtils.getNode(exerciseNodeOne.toString(), "weight").asDouble(), 0);
		assertEquals(exercise.getQuantity(), JsonUtils.getNode(exerciseNodeOne.toString(), "quantity").asInt(), 0);
		assertEquals(exercise.getPoints(), JsonUtils.getNode(exerciseNodeOne.toString(), "points").asDouble(), 0);
		
		assertEquals(exercise2.getId(), JsonUtils.getNode(exerciseNodeTwo.toString(), "id").asLong(), 0);
		assertEquals(exercise2.getName(), JsonUtils.getNode(exerciseNodeTwo.toString(), "name").asText());
		assertEquals(exercise2.getWeight(), JsonUtils.getNode(exerciseNodeTwo.toString(), "weight").asDouble(), 0);
		assertEquals(exercise2.getQuantity(), JsonUtils.getNode(exerciseNodeTwo.toString(), "quantity").asInt(), 0);
		assertEquals(exercise2.getPoints(), JsonUtils.getNode(exerciseNodeTwo.toString(), "points").asDouble(), 0);
	}
	
	@Test
	public void testPostExistActivityWithExerciseAndRemoveExercise() throws Exception {
		
		final Exercise exercise = new Exercise.Builder("TESTE").weight(30).quantity(3).points(30).build();
		exercise.setId(new Long(1L));
		
		final Activity actvity = Activity.valueOf("TESTE ACTIVITY");
		actvity.setWeekday(Weekday.TERÇA);
		actvity.removeExercise(exercise);
		actvity.setId(new Long(2L));
		
		
		this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(actvity)).contentType(MediaType.APPLICATION_JSON_VALUE));
		final String result = this.mockMvc.perform(get("/rest/activity/2")).andReturn().getResponse().getContentAsString();
		final JsonNode exerciseNodeArray = JsonUtils.getNode(result, "exercices");
		
		assertEquals(actvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(actvity.getWeekday().name(), JsonUtils.getNode(result, "weekDay").asText());
		assertEquals(actvity.totalPoints(), 0, 0);
		assertEquals(0, exerciseNodeArray.size(), 0);
	}
	
	@Test
	public void testGetActivityNotFund() throws Exception {
		final MvcResult getResult = this.mockMvc.perform(get("/springDataActivity/100")).andReturn();		
		assertEquals(404, getResult.getResponse().getStatus());
	}
	
	@Test
	public void testDeleteActivity() throws Exception {
		final MvcResult getResult = this.mockMvc.perform(delete("/rest/activity/3")).andReturn();		
		assertEquals(200, getResult.getResponse().getStatus());
	}
	
	@Test(expected = Exception.class)
	public void testDeleteActivityNotFound() throws Exception {
		this.mockMvc.perform(delete("/rest/activity/0")).andReturn();
	}
}
