package com.dygstudio.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by newsu on 2017/6/14.
 */
@Configuration
@PropertySource(value = "classpath:webConfig.properties")
public class WebPropertyConfig {
    @Value("${testVar1}")
    public String testVar1;

    @Value("${testVar2}")
    public String testVar2;
}
