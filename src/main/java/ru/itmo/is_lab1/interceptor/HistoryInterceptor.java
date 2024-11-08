package ru.itmo.is_lab1.interceptor;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.servlet.http.HttpServletRequest;
import ru.itmo.is_lab1.domain.entity.EntityChangeHistory;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.exceptions.service.CanNotCreateHistoryException;
import ru.itmo.is_lab1.interceptor.annotation.HistoryLog;
import ru.itmo.is_lab1.security.filter.JWTFilter;
import ru.itmo.is_lab1.service.EntityChangeHistoryService;


@HistoryLog
@Interceptor
public class HistoryInterceptor {
    @Inject
    private EntityChangeHistoryService entityChangeHistoryService;
    @Inject
    private HttpServletRequest request;

    @AroundInvoke
    public Object checkMethod(InvocationContext context) throws Exception {
        var result = context.proceed();
        String login = getLoginFromRequest();
        if (login == null) throw new CanNotCreateHistoryException("Can not get user login!");
        EntityChangeHistory.OperationType operationType = context.getMethod().getAnnotation(HistoryLog.class).operationType();
        if (operationType == null) throw new CanNotCreateHistoryException("Can not get operation type!");
        Integer entityId = getEntityIdFromResult(operationType, result);
        if (entityId == null) throw new CanNotCreateHistoryException("Can not get entity id!");
        entityChangeHistoryService.createHistory(login, operationType, entityId);
        return result;
    }

    private String getLoginFromRequest(){
        if (request == null) return null;
        return (String)request.getAttribute(JWTFilter.LOGIN_ATTRIBUTE_NAME);
    }

    private Integer getEntityIdFromResult(EntityChangeHistory.OperationType operationType, Object result){
        return switch (operationType){
            case CREATE, UPDATE -> ((MusicBand) result).getId();
            case DELETE -> (Integer) result;
            default -> null;
        };
    }
}
