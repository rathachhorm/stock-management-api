package com.innovisor.quickpos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = {"com.innovisor.quickpos"})
@EnableJpaAuditing
public class PersistenceConfig {

}
