package com.github.kaydunov.spring;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private final Map<Class<?>, Object> beanRegistry = new HashMap<>();

    public void registerBean(Class<?> clazz) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            beanRegistry.put(clazz, instance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bean for " + clazz.getName(), e);
        }
    }

    public void scanAndRegisterBeans(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> clazz : componentClasses) {
            registerBean(clazz);
        }
    }
}

