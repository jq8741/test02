package com.jh.springbootmybatisplus.service.impl;

import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 鉴权异常：认证用户访问无权限资源是的异常
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        String exceptionMsg = httpServletResponse.getHeader("ServiceException");
        if(!StringUtils.isEmpty(exceptionMsg)){

        }
    }

    private void responseServiceException(HttpServletResponse response,String exceptionMsg){

        AuthenticationServiceException serviceError = new AuthenticationServiceException(exceptionMsg);
        JSONObject responseJSONObject = new JSONObject(serviceError);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
