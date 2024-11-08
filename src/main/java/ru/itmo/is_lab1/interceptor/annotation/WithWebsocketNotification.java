package ru.itmo.is_lab1.interceptor.annotation;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithWebsocketNotification {
    /**
     * WebSocket class with static notifyAllListeners method;
     */
    @Nonbinding
    Class<?>[] value() default {};

    @Nonbinding
    String message() default "Notification";
}
