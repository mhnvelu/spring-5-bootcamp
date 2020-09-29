package com.spring5.projects.springdi.services;

public class GreetingServiceFactory {

    public GreetingService createGreetingService(String lang) {
        switch (lang) {
            case "en":
                return new I18NEnglishGreetingServiceImpl();
            case "es":
                return new I18NSpanishGreetingServiceImpl();
            default:
                return new PrimaryGreetingServiceImpl();
        }
    }
}
