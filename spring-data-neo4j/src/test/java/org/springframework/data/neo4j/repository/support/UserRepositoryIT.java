package org.springframework.data.neo4j.repository.support;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.testutil.MultiDriverTestClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.domain.sample.Role;
import org.springframework.data.neo4j.domain.sample.User;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.sample.UserRepository;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by markangrish on 25/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserRepositoryIT.Config.class)
@Transactional
public class UserRepositoryIT extends MultiDriverTestClass {

	@Autowired UserRepository userRepository;

	@Test
	public void invokesQueryWithWrapperType() {

		User firstUser = userRepository.save(new User("Mark", "Angrish", "test@user.com.au", new Role("Admin")));

		Optional<User> result = userRepository.findOptionalByEmailAddress("test@user.com.au");

		assertThat(result.isPresent(), is(true));
		assertThat(result.get(), is(firstUser));
	}

	@Configuration
	@EnableNeo4jRepositories("org.springframework.data.neo4j.repository.sample")
	@EnableTransactionManagement
	public static class Config {

		@Bean
		public Neo4jTransactionManager transactionManager() throws Exception {
			return new Neo4jTransactionManager(sessionFactory());
		}

		@Bean
		public SessionFactory sessionFactory() {
			return new SessionFactory("org.springframework.data.neo4j.domain.sample");
		}
	}
}
