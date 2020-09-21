package com.spring5.projects.springdi;

import com.spring5.projects.springdi.controllers.MyController;
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
       System.out.println(greeting);
    }

}
