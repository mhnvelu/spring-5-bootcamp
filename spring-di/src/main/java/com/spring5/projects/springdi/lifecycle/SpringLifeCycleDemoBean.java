package com.spring5.projects.springdi.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class SpringLifeCycleDemoBean implements InitializingBean, DisposableBean, BeanNameAware,
                                                BeanFactoryAware, ApplicationContextAware {

    public SpringLifeCycleDemoBean() {
        System.out.println("## LifeCycle Bean Constructor ##");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("## LifeCycle Bean after properties set ##");

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("## LifeCycle Bean Destroy ##");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("## LifeCycle Bean Name ## : " + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("## Bean Factory has been set ##");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("## ApplicationContext has been set ##");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("## PostConstruct annotated method has been called ##");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("##  PreDestroy annotated method has been called ##");
    }

    public void beforeInit(){
        System.out.println("## beforeInit - called by BeanPostProcessor ##");
    }

    public void afterInit(){
        System.out.println("##  afterInit - called by BeanPostProcessor ##");
    }
}
