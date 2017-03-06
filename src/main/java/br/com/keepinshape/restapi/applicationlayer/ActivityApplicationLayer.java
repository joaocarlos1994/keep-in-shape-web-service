/*
 * @(#)ActivitiesApplicationLayer.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi.applicationlayer;

import br.com.keepinshape.domain.activities.Activity;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
public interface ActivityApplicationLayer {
	
	public Activity saveActivity(final Activity activity);
	public void delteExercise(final Long idactivity);
	
}