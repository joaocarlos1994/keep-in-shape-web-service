/*
 * @(#)ActivityControllerTest.java 1.0 12/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.integrationtest.activity;

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

import com.fasterxml.jackson.databind.JsonNode;

import br.com.keepinshape.KeepinshapeWebserviceApplication;
import br.com.keepinshape.config.DbEnvironment;
import br.com.keepinshape.config.IntegrationTest;
import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.domain.activities.Weekday;
import br.com.keepinshape.domain.exercise.Exercise;
import br.com.keepinshape.restapi.wrapper.ActivityWrapper;
import br.com.keepinshape.util.JsonUtils;

/**
 * A <code>ActivityControllerTest</code> e responsavel por testar os end-points
 * exposto pelo keep-in-shape, em especialmente ela testa todos end-points do
 * aggregated <code>Activity</code>.
 *
 * @author Joao Batista
 * @version 1.0 12/03/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { KeepinshapeWebserviceApplication.class, DbEnvironment.class })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@Category(IntegrationTest.class)
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
		
		final Activity newActvity = Activity.valueOf("Test Save Without Exercise");
		newActvity.setWeekday(Weekday.SEGUNDA);
		
		final String activityJson = JsonUtils.convertObjectToJson(new ActivityWrapper(newActvity));
		
		final String response = this.mockMvc.perform(post("/rest/activity")
				.content(activityJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getContentAsString();
		final Long id = JsonUtils.getNode(response, "id").asLong();
		final String result = this.mockMvc.perform(get("/springDataActivity/" + id)).andReturn().getResponse()
				.getContentAsString();

		assertEquals(null, JsonUtils.getNode(result, "id"));
		assertEquals(newActvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(newActvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(newActvity.getWeekday().name(), JsonUtils.getNode(result, "weekday").asText());
	}

	@Test
	public void testPostUpateActivityNothingExercise() throws Exception {
		final Activity newActvity = Activity.valueOf("TESTE ACTIVITY");
		newActvity.setId(new Long(1L));
		newActvity.setWeekday(Weekday.TERÇA);

		this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(new ActivityWrapper(newActvity)))
				.contentType(MediaType.APPLICATION_JSON_VALUE));
		final String result = this.mockMvc.perform(get("/springDataActivity/1")).andReturn().getResponse()
				.getContentAsString();

		assertEquals(newActvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(newActvity.getWeekday().name(), JsonUtils.getNode(result, "weekday").asText());
	}

	@Test
	public void testPostNewActivityWithExercise() throws Exception {

		final Exercise exercise = new Exercise.Builder("TESTE").weight(30l).quantity(3).points(30l).build();
		exercise.setId(new Long(1L));

		final Activity newActvity = Activity.valueOf("Test Save with exercise");
		newActvity.setWeekday(Weekday.SEXTA);
		newActvity.addExercise(exercise);

		final String response = this.mockMvc.perform(post("/rest/activity")
				.content(JsonUtils.convertObjectToJson(new ActivityWrapper(newActvity))).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getContentAsString();
		final Long id = JsonUtils.getNode(response, "id").asLong();
		final String result = this.mockMvc.perform(get("/rest/activity/" + id)).andReturn().getResponse()
				.getContentAsString();
		final JsonNode exerciseNodeArray = JsonUtils.getNode(result, "exercises");
		final JsonNode exerciseNode = exerciseNodeArray.get(0);

		assertEquals(newActvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(newActvity.getWeekday().name(), JsonUtils.getNode(result, "weekday").asText());
		assertEquals(newActvity.totalPoints(), JsonUtils.getNode(result, "totalPoints").asLong(), 0);
		assertEquals(exercise.getId(), JsonUtils.getNode(exerciseNode.toString(), "id").asLong(), 0);
		assertEquals(exercise.getName(), JsonUtils.getNode(exerciseNode.toString(), "name").asText());
		assertEquals(exercise.getWeight(), JsonUtils.getNode(exerciseNode.toString(), "weight").asDouble(), 0);
		assertEquals(exercise.getQuantity(), JsonUtils.getNode(exerciseNode.toString(), "quantity").asInt(), 0);
		assertEquals(exercise.getPoints(), JsonUtils.getNode(exerciseNode.toString(), "points").asDouble(), 0);
	}

	@Test(expected = Exception.class)
	public void testPostNewActivityWithSameExercise() throws Exception {

		final Exercise exercise = new Exercise.Builder("TESTE").weight(30l).quantity(3).points(30l).build();
		exercise.setId(new Long(1L));

		final Exercise exerciseSame = new Exercise.Builder("TESTE").weight(30l).quantity(3).points(30l).build();
		exercise.setId(new Long(1L));

		final Activity newActvity = Activity.valueOf("Test Save with exercise");
		newActvity.setWeekday(Weekday.SEXTA);
		newActvity.addExercise(exercise);
		newActvity.addExercise(exerciseSame);

		this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(new ActivityWrapper(newActvity)))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse().getContentAsString();
	}

	@Test
	public void testPostExistActivityWithExercise() throws Exception {

		final Exercise exercise = new Exercise.Builder("TESTE").weight(30l).quantity(3).points(30l).build();
		exercise.setId(new Long(1L));

		final Exercise exercise2 = new Exercise.Builder("TESTE UPDATE").weight(40l).quantity(2).points(50l).build();
		exercise2.setId(new Long(2L));

		final Activity actvity = Activity.valueOf("TESTE ACTIVITY EXERCISE");
		actvity.setId(new Long(2L));
		actvity.setWeekday(Weekday.TERÇA);

		actvity.addExercise(exercise);
		actvity.addExercise(exercise2);

		this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(new ActivityWrapper(actvity)))
				.contentType(MediaType.APPLICATION_JSON_VALUE));
		final String result = this.mockMvc.perform(get("/rest/activity/2")).andReturn().getResponse()
				.getContentAsString();
		final JsonNode exerciseNodeArray = JsonUtils.getNode(result, "exercises");
		final JsonNode exerciseNodeOne = exerciseNodeArray.get(0);
		final JsonNode exerciseNodeTwo = exerciseNodeArray.get(1);

		assertEquals(actvity.getName(), JsonUtils.getNode(result, "name").asText());
		assertEquals(actvity.getWeekday().name(), JsonUtils.getNode(result, "weekday").asText());
		assertEquals(actvity.totalPoints(), JsonUtils.getNode(result, "totalPoints").asLong(), 0);

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
	public void testPostActivityExist() throws Exception {

		final Exercise exercise = new Exercise.Builder("TESTE").weight(30l).quantity(3).points(30l).build();
		exercise.setId(new Long(1l));

		final Activity actvity = Activity.valueOf("TESTE ACTIVITY EXERCISE EXISTS");
		actvity.setWeekday(Weekday.SEGUNDA);

		actvity.addExercise(exercise);

		final MvcResult mvcResult = this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(new ActivityWrapper(actvity)))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Activity is invalid or exists", message);
		
	}

	@Test(expected = Exception.class)
	public void testPostExistActivityWithSameExercise() throws Exception {

		final Exercise exercise = new Exercise.Builder("TESTE").weight(30l).quantity(3).points(30l).build();
		exercise.setId(new Long(1L));

		final Exercise exerciseSame = new Exercise.Builder("TESTE").weight(30l).quantity(3).points(30l).build();
		exercise.setId(new Long(1L));

		final Activity actvity = Activity.valueOf("TESTE ACTIVITY EXERCISE");
		actvity.setId(new Long(2L));
		actvity.setWeekday(Weekday.TERÇA);

		actvity.addExercise(exercise);
		actvity.addExercise(exerciseSame);

		this.mockMvc.perform(post("/rest/activity").content(JsonUtils.convertObjectToJson(new ActivityWrapper(actvity)))
				.contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void testPostExistActivityWithExerciseAndRemoveExercise() throws Exception {

		final MvcResult mvcResult = this.mockMvc.perform(delete("/rest/activity/4/exercise/1")).andReturn();
		final String result = this.mockMvc.perform(get("/rest/activity/4")).andReturn().getResponse()
				.getContentAsString();
		final String message = mvcResult.getResponse().getContentAsString();
		final JsonNode exerciseNodeArray = JsonUtils.getNode(result, "exercises");

		assertEquals("TESTE ACTIVITY EXERCISE DELETE", JsonUtils.getNode(result, "name").asText());
		assertEquals("SEGUNDA", JsonUtils.getNode(result, "weekday").asText());
		assertEquals("\"void\"", message);
		assertNotNull(message);
		assertEquals(0, 0, 0);
		assertEquals(0, exerciseNodeArray.size(), 0);
	}
	
	@Test
	public void testPostExistActivityWithActivityIdZero() throws Exception {

		MvcResult mvcResult = this.mockMvc.perform(delete("/rest/activity/0/exercise/1")).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Id Activity is invalid", message);
	}
	
	@Test
	public void testPostExistActivityWithExerciseIdZero() throws Exception {

		MvcResult mvcResult = this.mockMvc.perform(delete("/rest/activity/1/exercise/0")).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Id Exercise invalid", message);
	}

	@Test
	public void testPostExistActivityWithExerciseAndRemoveExerciseNotExists() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(delete("/rest/activity/1/exercise/1")).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("No exists exercise or exercise invalid", message);
		
	}
	
	@Test
	public void testGetActivityNotFundWithIdNegative() throws Exception {
		final MvcResult getResult = this.mockMvc.perform(get("/rest/activity/-1")).andReturn();
		final String message = getResult.getResponse().getContentAsString();
		
		assertEquals(500, getResult.getResponse().getStatus());
		assertEquals("Id Activity is invalid", message);
	}
	
	@Test
	public void testGetActivityNotFundWithIdZero() throws Exception {
		final MvcResult getResult = this.mockMvc.perform(get("/rest/activity/0")).andReturn();
		final String message = getResult.getResponse().getContentAsString();
		
		assertEquals(500, getResult.getResponse().getStatus());
		assertEquals("Id Activity is invalid", message);
	}

	@Test
	public void testDeleteActivity() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(delete("/rest/activity/3")).andReturn();
		final MvcResult mvcResultGet = this.mockMvc.perform(get("/springDataActivity/3")).andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals("\"void\"", mvcResult.getResponse().getContentAsString());
		assertNotNull(mvcResult.getResponse().getContentAsString());
		assertEquals(404, mvcResultGet.getResponse().getStatus());
	}

	@Test
	public void testDeleteActivityNotFound() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(delete("/rest/activity/0")).andReturn();
		final String message = mvcResult.getResponse().getContentAsString();
		
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertEquals("Id is invalid", message);
	}
}
