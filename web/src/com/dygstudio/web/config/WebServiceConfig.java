package com.dygstudio.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;

/**
 * Created by diyaguang on 2017/6/12.
 */
@Configuration
public class WebServiceConfig {
    @Bean
    public SimpleJaxWsServiceExporter jaxWsExporter(){
        return new SimpleJaxWsServiceExporter();
    }
}
