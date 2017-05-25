package com.dygstudio.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by newsu on 2017/5/25.
 */
@Configuration
@ComponentScan(basePackages = {"com.dygstudio.web"},excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,value= EnableWebMvc.class)})
public class RootConfig {
}
