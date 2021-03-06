/*
 * Copyright (c)  [2011-2016] "Pivotal Software, Inc." / "Neo Technology" / "Graph Aware Ltd."
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 *
 */

package org.springframework.data.neo4j.examples.jsr303;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.examples.jsr303.service.AdultService;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Vince Bickers
 * @author Mark Angrish
 */
@Configuration
@ComponentScan(basePackageClasses = {AdultService.class})
@EnableNeo4jRepositories("org.springframework.data.neo4j.examples.jsr303.repo")
@EnableTransactionManagement
public class JSR303Context {

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new Neo4jTransactionManager(sessionFactory());
	}

	@Bean
	public SessionFactory sessionFactory() {
		return new SessionFactory("org.springframework.data.neo4j.examples.jsr303.domain");
	}
}
