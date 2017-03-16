/*
 * @(#)AbstractDbEnvironment.java 1.0 15/03/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import br.com.keepinshape.domain.activities.ActivityRepository;
import br.com.keepinshape.domain.exercise.ExerciseRepository;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 15/03/2017
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = { ActivityRepository.class, ExerciseRepository.class })
public abstract class AbstractDbEnvironment {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource,
			final JpaVendorAdapter jpaVendorAdapter) {
		final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(jpaVendorAdapter);
		emf.setPackagesToScan("br.com.keepinshape.domain");
		return emf;
	}

	@Bean
	public abstract DataSource getDataSource();

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.HSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
		return adapter;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

}
