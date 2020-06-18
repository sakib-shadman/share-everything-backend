package com.shareeverything.interceptor;

import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class LoggingServiceInterceptor extends HandlerInterceptorAdapter {
    public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "traceid";
    private final String TRACE_ID_HEADER = "X-TRACE-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String id = UUID.randomUUID().toString();
        response.setHeader(TRACE_ID_HEADER, id);
        MDC.put(DEFAULT_MDC_UUID_TOKEN_KEY, id);
        return true;
    }
}

