package com.javed.lambda.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.javed.*"})
public class MvcConfig {
}
