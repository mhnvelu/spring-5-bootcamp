package com.spring5.projects.springdi.config;

import com.spring5.projects.springdi.datasource.FakeDataSource;
import com.spring5.projects.springdi.datasource.FakeJmsBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({"classpath:datasource.properties", "classpath:jms.properties"})
public class FakeDataSourcePropertyConfig {

    @Autowired
    Environment environment;

    @Value("${project.db.username}")
    private String username;

    @Value("${project.db.password}")
    private String password;

    @Value("${project.db.url}")
    private String dbUrl;

    @Value("${project.jms.username}")
    private String jmsUsername;

    @Value("${project.jms.password}")
    private String jmsPassword;

    @Value("${project.jms.url}")
    private String jmsUrl;


    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
                new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public FakeDataSource fakeDataSource() {
        FakeDataSource fakeDataSource = new FakeDataSource();
        fakeDataSource.setUsername(environment.getProperty("DB_USERNAME"));
        fakeDataSource.setPassword(password);
        fakeDataSource.setDbUrl(dbUrl);
        return fakeDataSource;
    }

    @Bean
    public FakeJmsBroker fakeJmsBroker() {
        FakeJmsBroker fakeJmsBroker = new FakeJmsBroker();
        fakeJmsBroker.setUsername(jmsUsername);
        fakeJmsBroker.setPassword(jmsPassword);
        fakeJmsBroker.setJmsUrl(jmsUrl);
        return fakeJmsBroker;
    }
}
