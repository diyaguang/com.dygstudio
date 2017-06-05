package com.dygstudio.web.config;

import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by newsu on 2017/5/25.
 */
@Configuration
@ImportResource("classpath:WebBeanConfig.xml")    //此处增加了对 在 xml 中进行配置的 Bean 进行引用
@ComponentScan(basePackages = {"com.dygstudio.web"},
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,value= EnableWebMvc.class)})
public class RootConfig {

    //配置  SessionFactoryBean
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan(new String[]{"com.dygstudio.web.entity"});
        Properties props = new Properties();

        props.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        props.setProperty("hibernate.show_sql","true");
        props.setProperty("hibernate.generate_statistics","true");
        props.setProperty("hibernate.connection.release_mode","auto");
        props.setProperty("hibernate.autoReconnect","true");
        props.setProperty("hibernate.hbm2ddl.auto","update");

        sfb.setHibernateProperties(props);
        return sfb;
    }
}
