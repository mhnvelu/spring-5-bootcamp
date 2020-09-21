package com.spring5.projects.springdi;

import com.spring5.projects.springdi.controllers.ConstructorInjectedController;
import com.spring5.projects.springdi.controllers.MyController;
import com.spring5.projects.springdi.controllers.PropertyInjectedController;
import com.spring5.projects.springdi.controllers.SetterInjectedController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDiApplication {

    public static void main(String[] args) {
       ApplicationContext applicationContext = SpringApplication.run(SpringDiApplication.class,
                                                                            args);
       MyController myController = (MyController)applicationContext.getBean("myController");
       String greeting = myController.sayHello();
        System.out.println("--------Primary Bean------");
       System.out.println(greeting);

        System.out.println("--------Property based DI-------");
        PropertyInjectedController propertyInjectedController =
                (PropertyInjectedController)applicationContext.getBean(
                        "propertyInjectedController") ;
        System.out.println(propertyInjectedController.getGreeting());

        System.out.println("--------Setter based DI-------");
        SetterInjectedController setterInjectedController =
                (SetterInjectedController)applicationContext.getBean("setterInjectedController") ;
        System.out.println(setterInjectedController.getGreeting());

        System.out.println("--------Constructor based DI-------");
        ConstructorInjectedController constructorInjectedController =
                (ConstructorInjectedController)applicationContext.getBean(
                        "constructorInjectedController");
        System.out.println(constructorInjectedController.getGreeting());

    }

}
