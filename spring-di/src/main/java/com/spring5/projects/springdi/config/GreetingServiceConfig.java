package com.spring5.projects.springdi.config;

import com.spring5.projects.springdi.services.GreetingService;
import com.spring5.projects.springdi.services.GreetingServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class GreetingServiceConfig {

    @Bean
    GreetingServiceFactory greetingServiceFactory() {
        return new GreetingServiceFactory();
    }

    @Bean
    @Primary
    @Profile("default")
    GreetingService primaryGreetingService(GreetingServiceFactory greetingServiceFactory) {
        return greetingServiceFactory.createGreetingSetvice("en");
    }

    @Bean
    @Profile({"ES"})
    GreetingService spanishGreetingService(GreetingServiceFactory greetingServiceFactory){
        return greetingServiceFactory.createGreetingSetvice("es");
    }

    @Bean
    @Profile({"EN"})
    GreetingService englishGreetingService(GreetingServiceFactory greetingServiceFactory){
        return greetingServiceFactory.createGreetingSetvice("en");
    }
}
