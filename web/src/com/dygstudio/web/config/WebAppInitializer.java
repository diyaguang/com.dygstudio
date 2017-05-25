package com.dygstudio.web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by newsu on 2017/5/25.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings(){
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses(){
        return new Class<?>[]{ RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses(){
        return new Class<?>[]{ WebConfig.class };
    }
}
