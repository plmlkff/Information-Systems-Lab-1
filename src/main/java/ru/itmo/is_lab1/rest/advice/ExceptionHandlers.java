package ru.itmo.is_lab1.rest.advice;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.is_lab1.exceptions.interceptor.AccessDeniedException;
import ru.itmo.is_lab1.exceptions.interceptor.CanNotSendNotificationsException;
import ru.itmo.is_lab1.exceptions.interceptor.LoginRequiredException;
import ru.itmo.is_lab1.exceptions.service.CanNotCreateHistoryException;

import static ru.itmo.is_lab1.util.HttpResponse.*;

public class ExceptionHandlers {
    @Provider
    @Priority(Priorities.USER)
    public static class CanNotCreateHistoryExceptionMapper implements ExceptionMapper<CanNotCreateHistoryException> {
        @Override
        public Response toResponse(CanNotCreateHistoryException exception) {
            return error(exception.getMessage());
        }
    }

    @Provider
    @Priority(Priorities.USER)
    public static class LoginRequiredExceptionMapper implements ExceptionMapper<LoginRequiredException> {
        @Override
        public Response toResponse(LoginRequiredException exception) {
            return error(exception.getMessage());
        }
    }

    @Provider
    @Priority(Priorities.USER)
    public static class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {
        @Override
        public Response toResponse(AccessDeniedException exception) {
            return error(exception.getMessage());
        }
    }

    @Provider
    @Priority(Priorities.USER)
    public static class CanNotSendNotificationsExceptionMapper implements ExceptionMapper<CanNotSendNotificationsException> {
        @Override
        public Response toResponse(CanNotSendNotificationsException exception) {
            return error(exception.getMessage() + " All changes have been saved!");
        }
    }

    @Provider
    @Priority(Priorities.USER)
    public static class GlobalExceptionMapper implements ExceptionMapper<Exception> {
        @Override
        public Response toResponse(Exception exception) {
            return error(exception.getMessage());
        }
    }

    @Provider
    @Priority(Priorities.USER)
    public static class GlobalRuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
        @Override
        public Response toResponse(RuntimeException exception) {
            return error(exception.getMessage());
        }
    }
}
