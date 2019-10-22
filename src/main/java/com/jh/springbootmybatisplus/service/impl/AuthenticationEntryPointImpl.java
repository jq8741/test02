package com.jh.springbootmybatisplus.service.impl;

import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证异常：用户在认证过程中的异常
 *
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        String exceptionMsg = httpServletResponse.getHeader("ServiceException");
        if(!StringUtils.isEmpty(exceptionMsg)){
            responseServiceException(httpServletResponse,exceptionMsg);
        }
    }

    private void responseServiceException(HttpServletResponse response,String exceptionMsg){

        AuthenticationServiceException serviceError = new AuthenticationServiceException(exceptionMsg);
        JSONObject responseJSONObject = new JSONObject(serviceError);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try{
            out = response.getWriter();
            out.append(responseJSONObject.toString());
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (out!=null) {
                out.close();
            }
        }
    }
}
