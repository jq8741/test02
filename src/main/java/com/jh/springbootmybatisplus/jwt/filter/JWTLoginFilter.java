package com.jh.springbootmybatisplus.jwt.filter;

import com.jh.springbootmybatisplus.jwt.jwttoken.JwtToken;
import com.jh.springbootmybatisplus.jwt.jwtutils.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String userName = this.obtainUsername(request);
        String password = this.obtainPassword(request);

        if(StringUtils.isEmpty(userName)){
            AuthenticationServiceException serviceException = new AuthenticationServiceException("用户名不能为空！");
            response.setHeader("ServiceException",serviceException.getMessage());
            throw serviceException;
        }

        if(StringUtils.isEmpty(password)){
            AuthenticationServiceException serviceException = new AuthenticationServiceException("密码不能为空!");
            response.setHeader("ServiceException",serviceException.getMessage());
            throw serviceException;
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName,
                        password,
                        new ArrayList<>()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        String token = Jwts.builder()
                .setSubject(((User)authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 *1000))
                .signWith(SignatureAlgorithm.HS512,"PrivateSecret")
                .compact();

        returnToken(response, JwtUtil.getTokenHeader(token));

    }

    private void returnToken(HttpServletResponse response, String token){

        JwtToken jwtToken = new JwtToken(token);
        JSONObject responseJSONObject = new JSONObject(jwtToken);

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
            if (out !=null){
                out.close();
            }
        }
    }
}
