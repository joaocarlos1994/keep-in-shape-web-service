/*
 * @(#)ActivityWrapper.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.restapi.deserializer.ActivityDeserialize;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
@JsonDeserialize(using = ActivityDeserialize.class)
public class ActivityWrapper {
	
	private final Activity activity;

	public ActivityWrapper(final Activity activity) {
		super();
		this.activity = activity;
	}

	public Activity getActivity() {
		return activity;
	}
}
