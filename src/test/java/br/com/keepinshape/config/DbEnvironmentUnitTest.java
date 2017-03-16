/*
 * @(#)DbEnvironmentUnitTest.java 1.0 15/03/2017
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
 * @version 1.0 15/03/2017
 */
public class DbEnvironmentUnitTest extends AbstractDbEnvironment {

	@Override
	public DataSource getDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.addScript("scripts/create-db.sql")
				.addScript("scripts/unit-test-insert-keep-in-shape.sql")
				.build();
	}

}
