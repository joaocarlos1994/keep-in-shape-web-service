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
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@RepositoryRestResource(collectionResourceRel = "/activies", path = "/activies")
public interface ActiviesRepository extends PagingAndSortingRepository<Activities, Long> {

}
