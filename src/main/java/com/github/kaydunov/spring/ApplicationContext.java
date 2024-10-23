package com.github.kaydunov.spring;

import com.github.kaydunov.exception.ApplicationContextException;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A simple Spring-like application context.
 */
public class ApplicationContext {
    private final Map<Class<?>, Object> beanRegistry = new HashMap<>();

    /**
     * @param clazz - this class exist in your base package
     */
    public ApplicationContext(Class<?> clazz) {
        scanAndRegisterBeans(clazz.getPackage().getName());
        injectDependencies();
    }

    public <T> T getBean(Class<T> clazz) {
        T t = clazz.cast(beanRegistry.get(clazz));
        if (t == null) {
            throw new NoSuchBeanDefinitionException("Bean not found: " + clazz.getName());
        }
        return t;
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
            throw new ApplicationContextException("Failed to create bean for " + clazz.getName(), e);
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
                            throw new ApplicationContextException("Failed to inject dependency for " + field.getName(), e);
                        }
                    }
                }
            }
        }
    }
}
