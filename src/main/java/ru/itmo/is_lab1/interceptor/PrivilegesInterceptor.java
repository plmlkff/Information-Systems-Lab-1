package ru.itmo.is_lab1.interceptor;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.servlet.http.HttpServletRequest;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.entity.UserRole;
import ru.itmo.is_lab1.exceptions.interceptor.AccessDeniedException;
import ru.itmo.is_lab1.exceptions.interceptor.LoginRequiredException;
import ru.itmo.is_lab1.security.filter.JWTFilter;
import ru.itmo.is_lab1.interceptor.annotation.WithPrivileges;

import java.util.Arrays;

@WithPrivileges
@Interceptor
public class PrivilegesInterceptor {
    @Inject
    private HttpServletRequest request;
    @Inject
    private UserDAO userDAO;

    @AroundInvoke
    public Object checkMethod(InvocationContext context) throws Exception {
        String login  = getLoginFromRequest();
        if (login == null) throw new LoginRequiredException("Can not get login from user token!");
        User user = userDAO.findById(login);
        if (isAnnotationContainsUserRole(context, user.getRole())){
            return context.proceed();
        }
        throw new AccessDeniedException("Permission denied!");
    }

    private boolean isAnnotationContainsUserRole(InvocationContext context, UserRole userRole){
        WithPrivileges annotation = context.getMethod().getAnnotation(WithPrivileges.class);
        if (annotation == null) return false;
        return Arrays.asList(annotation.value()).contains(userRole);
    }

    private String getLoginFromRequest(){
        if (request == null) return null;
        return (String)request.getAttribute(JWTFilter.LOGIN_ATTRIBUTE_NAME);
    }
}
