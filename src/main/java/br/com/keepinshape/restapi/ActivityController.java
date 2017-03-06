/*
 * @(#)ActivitiesController.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.restapi.applicationlayer.ActivityApplicationLayer;
import br.com.keepinshape.restapi.wrapper.ActivityWrapper;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@BasePathAwareController
public class ActivityController {
	
	private final ActivityApplicationLayer activityApplicationLayer;

	@Autowired
	public ActivityController(@Qualifier("activityApplicationLayerImpl") final ActivityApplicationLayer activityApplicationLayer) {
		super();
		this.activityApplicationLayer = activityApplicationLayer;
	}
	
	@PostMapping(value = "/rest/activity")
	public ResponseEntity<?> save(@RequestBody final ActivityWrapper activityWrapper) {
		activityApplicationLayer.saveActivity(activityWrapper.getActivity());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/rest/activity/{id}")
	public ResponseEntity<Activity> get(@PathVariable("id") final Long id) {
		activityApplicationLayer.delteExercise(id);
		return new ResponseEntity<Activity>(HttpStatus.OK);
	}
}
