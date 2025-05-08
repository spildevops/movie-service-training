package com.johanwork;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		DockerImageName docker = DockerImageName.parse("public.ecr.aws/docker/library/postgres:latest")
				.asCompatibleSubstituteFor("postgres");

		return new PostgreSQLContainer<>(docker)
				.withDatabaseName("movie_service_db")
				.withInitScript("init-db.sql");
	}

}
