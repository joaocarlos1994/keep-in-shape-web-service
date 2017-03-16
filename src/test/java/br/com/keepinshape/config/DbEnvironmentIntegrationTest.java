/*
 * @(#)DbEnvironment.java 1.0 07/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.config;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 07/03/2017
 */
public class DbEnvironmentIntegrationTest extends AbstractDbEnvironment {

	@Override
	public DataSource getDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.addScript("scripts/create-db.sql")
				.addScript("scripts/integration-test-insert-keep-in-shape.sql")
				.build();
	}
	
}
