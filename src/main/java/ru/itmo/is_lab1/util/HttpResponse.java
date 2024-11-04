package ru.itmo.is_lab1.util;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class HttpResponse {
    public static <T> Response ok(T body) {
        return Response.status(Status.OK)
                .entity(body)
                .build();
    }

    public static Response error(int code) {
        return error(Status.fromStatusCode(code));
    }

    public static Response error(int code, String reason) {
        return error(Status.fromStatusCode(code));
    }

    public static Response error(Status status) {
        return Response.status(status)
                .entity(status.getReasonPhrase())
                .build();
    }

    public static Response error(Status status, String reason) {
        return Response.status(status)
                .entity(reason)
                .build();
    }

    public static Response error() {
        return error(Status.BAD_REQUEST);
    }

    public static Response error(String reason) {
        return error(Status.BAD_REQUEST, reason);
    }
}
