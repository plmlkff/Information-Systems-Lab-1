package ru.itmo.is_lab1.security.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import ru.itmo.is_lab1.exceptions.interceptor.CanNotSendNotificationsException;
import ru.itmo.is_lab1.security.interceptor.annotation.WithWebsocketNotification;

import java.lang.reflect.Method;

@WithWebsocketNotification
@Interceptor
public class WebSocketNotificationsInterceptor {
    private final String NOTIFY_METHOD_NAME = "notifyAllListeners";

    @AroundInvoke
    public Object checkMethod(InvocationContext context) throws Exception {
        try {
            Object res = context.proceed();
            Class<?>[] webSocketClasses = context.getMethod().getAnnotation(WithWebsocketNotification.class).value();
            String message = context.getMethod().getAnnotation(WithWebsocketNotification.class).message();
            for (var webSocketClass : webSocketClasses){
                Method notificationMethod = webSocketClass.getMethod(NOTIFY_METHOD_NAME, String.class);
                notificationMethod.invoke(null, message);
            }
            return res;
        } catch (Exception e){
            throw new CanNotSendNotificationsException(e.getMessage());
        }
    }
}
