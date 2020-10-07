package com.boroday.userstore.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceLocator {
    private static final ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext(new String[]{"/context/context.xml"});

    public static <T> T getBean(Class<T> clazz) {
        return APPLICATION_CONTEXT.getBean(clazz);
    }

    public static <T> T getBean(String className) {
        return (T) APPLICATION_CONTEXT.getBean(className);
    }
}
