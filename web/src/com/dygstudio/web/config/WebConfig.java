package com.dygstudio.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by newsu on 2017/5/25.
 */
@Configuration
@ImportResource("classpath:WebBeanConfig.xml")    //此处增加了对 在 xml 中进行配置的 Bean 进行引用
@EnableWebMvc
@ComponentScan("com.dygstudio.web")
public class WebConfig extends WebMvcConfigurerAdapter {

    //配置 JSP视图解析器
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    //配置 静态资源的处理
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    //配置  SessionFactoryBean
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan(new String[]{"com.dygstudio.web.domain"});
        Properties props = new Properties();

        props.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        props.setProperty("hibernate.show_sql","true");
        props.setProperty("hibernate.generate_statistics","true");
        props.setProperty("hibernate.connection.release_mode","auto");
        props.setProperty("hibernate.autoReconnect","true");

        sfb.setHibernateProperties(props);
        return sfb;
    }
}
