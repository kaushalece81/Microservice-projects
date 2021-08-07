package com.in28minutes.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.microservices.limitsservice.beans.Configuration;
import com.in28minutes.microservices.limitsservice.beans.LimitConfiguration;

@RestController
public class LimitConfigurationController {
	
	@Autowired
	Configuration configuration;

	@GetMapping("/limits")
	public LimitConfiguration retriveLimitsFromConfiguration() {
		return new LimitConfiguration(1000,1);
	}
	@GetMapping("/limits-from-properties")
	public LimitConfiguration retriveLimitsFromConfigurationPropertiesFile() {
		return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
	}
}
