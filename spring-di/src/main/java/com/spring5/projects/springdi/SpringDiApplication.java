package com.spring5.projects.springdi;

import com.spring5.projects.springdi.controllers.*;
import com.spring5.projects.springdi.datasource.FakeDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDiApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(SpringDiApplication.class, args);
        MyController myController = (MyController) applicationContext.getBean("myController");
        String greeting = myController.sayHello();
        System.out.println("--------Primary Bean------");
        System.out.println(greeting);

        System.out.println("--------Profiles------");
        I18nController i18nController =
                (I18nController) applicationContext.getBean("i18nController");
        System.out.println(i18nController.sayHello());

        System.out.println("--------Property based DI-------");
        PropertyInjectedController propertyInjectedController =
                (PropertyInjectedController) applicationContext
                        .getBean("propertyInjectedController");
        System.out.println(propertyInjectedController.getGreeting());

        System.out.println("--------Setter based DI-------");
        SetterInjectedController setterInjectedController =
                (SetterInjectedController) applicationContext.getBean("setterInjectedController");
        System.out.println(setterInjectedController.getGreeting());

        System.out.println("--------Constructor based DI-------");
        ConstructorInjectedController constructorInjectedController =
                (ConstructorInjectedController) applicationContext
                        .getBean("constructorInjectedController");
        System.out.println(constructorInjectedController.getGreeting());


        System.out.println("--------Load External Properties file-------");
        FakeDataSource fakeDataSource =
                (FakeDataSource) applicationContext.getBean(FakeDataSource.class);
        System.out.println("Username : " + fakeDataSource.getUsername());
        System.out.println("Password : " + fakeDataSource.getPassword());
        System.out.println("DBUrl : " + fakeDataSource.getDbUrl());
    }

}
