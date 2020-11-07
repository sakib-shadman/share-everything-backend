package com.shareeverything.Util;

import com.shareeverything.interceptor.HeaderRequestInterceptor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class SessionHelper {

    public static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession();
    }

    public static List<ClientHttpRequestInterceptor> getInterceptor() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", String.format("Bearer %s", getToken())));
        return interceptors;
    }

    public static String getUserId() {
        return (String) getSession().getAttribute("USER-ID");
    }

    public static void setUserId(String userId) {
        getSession().setAttribute("USER-ID", userId);
    }

    public static String getToken() {
        return (String) getSession().getAttribute("JWT");
    }

    public static void setToken(String token) {
        getSession().setAttribute("JWT", token);
    }

    public static String getName() {
        return (String) getSession().getAttribute("NAME");
    }

    public static void setName(String name) {
        getSession().setAttribute("NAME", name);
    }


}
