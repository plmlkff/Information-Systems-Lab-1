package ru.itmo.is_lab1.security.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import ru.itmo.is_lab1.security.interceptor.annotation.WithWebsocketNotification;

import java.lang.reflect.Method;

@WithWebsocketNotification
@Interceptor
public class WebSocketNotificationsInterceptor {
    private final String NOTIFY_METHOD_NAME = "notifyAllListeners";

    @AroundInvoke
    public Object checkMethod(InvocationContext context) throws Exception {
        Object res = context.proceed();
        Class<?> webSocketClass = context.getMethod().getAnnotation(WithWebsocketNotification.class).value();
        Method notificationMethod = webSocketClass.getMethod(NOTIFY_METHOD_NAME);
        notificationMethod.invoke(null);
        return res;
    }
}
