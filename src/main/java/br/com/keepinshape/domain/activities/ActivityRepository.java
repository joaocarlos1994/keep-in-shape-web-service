/*
 * @(#)ActiviesRepository.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.domain.activities;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * A <code>ActivityRepository</code> tem por responsabilidade
 * recuperar objeto de dominicio da <code>Activities</code>.
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@RepositoryRestResource(collectionResourceRel = "/spring-data-activity", path = "/spring-data-activity")
public interface ActivityRepository extends PagingAndSortingRepository<Activity, Long> {

}
