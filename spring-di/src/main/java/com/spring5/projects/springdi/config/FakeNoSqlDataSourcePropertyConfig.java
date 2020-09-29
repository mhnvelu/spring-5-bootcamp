package com.spring5.projects.springdi.config;

import com.spring5.projects.springdi.datasource.FakeNosqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeNoSqlDataSourcePropertyConfig {

    @Value("${project.nosqldb.username}")
    private String username;

    @Value("${project.nosqldb.password}")
    private String password;

    @Value("${project.nosqldb.url}")
    private String dbUrl;

    @Bean
    public FakeNosqlDataSource fakeNosqlDataSource() {
        FakeNosqlDataSource fakeNosqlDataSource = new FakeNosqlDataSource();
        fakeNosqlDataSource.setUsername(username);
        fakeNosqlDataSource.setPassword(password);
        fakeNosqlDataSource.setDbUrl(dbUrl);
        return fakeNosqlDataSource;
    }
}
