package ru.itmo.is_lab1.interceptor.annotation;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;
import ru.itmo.is_lab1.domain.entity.EntityChangeHistory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HistoryLog {
    @Nonbinding
    EntityChangeHistory.OperationType operationType() default EntityChangeHistory.OperationType.CREATE;
}
