package com.github.kaydunov.spring;

public class NoSuchBeanDefinitionException extends RuntimeException{
    public NoSuchBeanDefinitionException(String s) {
        super(s);
    }
}
