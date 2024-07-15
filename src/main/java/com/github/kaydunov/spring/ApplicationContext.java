package com.github.kaydunov.spring;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private final Map<Class<?>, Object> beanRegistry = new HashMap<>();

    public ApplicationContext(String basePackage) {
        scanAndRegisterBeans(basePackage);
        injectDependencies();
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beanRegistry.get(clazz));
    }

    private void scanAndRegisterBeans(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> clazz : componentClasses) {
            registerBean(clazz);
        }
    }

    private void registerBean(Class<?> clazz) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            beanRegistry.put(clazz, instance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bean for " + clazz.getName(), e);
        }
    }

    private void injectDependencies() {
        for (Object bean : beanRegistry.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object dependency = beanRegistry.get(field.getType());
                    if (dependency != null) {
                        field.setAccessible(true);
                        try {
                            field.set(bean, dependency);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Failed to inject dependency for " + field.getName(), e);
                        }
                    }
                }
            }
        }
    }
}


