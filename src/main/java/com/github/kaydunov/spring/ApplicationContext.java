package com.github.kaydunov.spring;

import com.github.kaydunov.exception.ApplicationContextException;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;

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
                    if (List.class.isAssignableFrom(field.getType())) {
                        injectListDependency(bean, field);
                    } else {
                        injectFieldDependency(bean, field);
                    }
                }
            }
        }
    }

    private void injectFieldDependency(Object bean, Field field) {
        try {
            Class<?> fieldType = field.getType();
            Object dependency = beanRegistry.get(fieldType);
            if (dependency == null) {
                throw new ApplicationContextException("No bean found for type: " + fieldType.getName());
            }
            field.setAccessible(true);
            field.set(bean, dependency);
        } catch (IllegalAccessException e) {
            throw new ApplicationContextException("Failed to inject dependency for " + field.getName(), e);
        }
    }

    private void injectListDependency(Object bean, Field field) {
        try {
            // Определяем тип элементов в списке
            Class<?> genericType = (Class<?>) ((java.lang.reflect.ParameterizedType) field.getGenericType())
                    .getActualTypeArguments()[0];

            // Находим все бины, которые являются наследниками genericType
            List<Object> dependencies = new ArrayList<>();
            for (Object candidate : beanRegistry.values()) {
                if (genericType.isAssignableFrom(candidate.getClass())) {
                    dependencies.add(candidate);
                }
            }

            field.setAccessible(true);
            field.set(bean, dependencies);
        } catch (IllegalAccessException e) {
            throw new ApplicationContextException("Failed to inject list dependency for " + field.getName(), e);
        }
    }

}
