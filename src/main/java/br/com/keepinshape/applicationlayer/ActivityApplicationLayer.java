/*
 * @(#)ActivitiesApplicationLayer.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.applicationlayer;

import br.com.keepinshape.domain.activities.Activity;

/**
 * A <code>ActivityApplicationLayer</code> contem
 * metodos incomum da <code>ActivityApplicationLayerImpl</code>
 * cujo o objetivo e promover uma maior abstracao.
 *
 * @author Joao Batista
 * @version 1.0 05/03/2017
 */
public interface ActivityApplicationLayer {
	
	public Activity saveActivity(final Activity activity);
	public Activity findById(final Long id);
	public void deleteActivity(final Long idactivity);
	public void deleteActivityExercise(final Long id, final Long idExercise);
	
}
