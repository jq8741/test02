package com.jh.springbootmybatisplus.jwt.filter;

import com.jh.springbootmybatisplus.jwt.jwtutils.JwtUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(JwtUtil.getAuthorizationHeaderPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getUsernamePasswordAuthenticationToken(header);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token){
        String user = Jwts.parser()
                .setSigningKey("PrivateSecret")
                .parseClaimsJws(token.replace(JwtUtil.getAuthorizationHeaderPrefix(),""))
                .getBody()
                .getSubject();

        if(user !=null){
            return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
        }
        return  null;
    }
}

