package com.spring5.projects.springdi.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

//@Service("i18nService")
public class I18NEnglishGreetingServiceImpl implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Hello World - I18N English Service";
    }
}
