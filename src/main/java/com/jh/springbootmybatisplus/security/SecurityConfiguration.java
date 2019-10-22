package com.jh.springbootmybatisplus.security;

import com.jh.springbootmybatisplus.web.filter.JWTAuthenticationFilter;
import com.jh.springbootmybatisplus.web.filter.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String DEV_ENVIRONMENT = "dev";

    /**
     * 运行环境：dev/prod/test
     */
    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 密码加密及校验方式
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * web资源权限控制
     *
     * */
    @Override
    public void configure(WebSecurity web) throws Exception{

        super.configure(web);
        web.ignoring().antMatchers("/config/**", "/css/**", "/fonts/**", "/img/**", "/js/**");

        web.ignoring().antMatchers(HttpMethod.GET,"/login");

        web.ignoring().antMatchers("/","/console", "/console/**","/static/**","/*.png","/*.js","/*.css");

        //swagger-ui start
        web.ignoring().antMatchers("/v2/api-docs/**");
        web.ignoring().antMatchers("/swagger.json");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/webjars/**");
        //swagger-ui end
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        //本地开发环境关闭权限控制，方便测试
        if(DEV_ENVIRONMENT.equals(active)){
            http.cors().and().csrf().disable().authorizeRequests()
                    .antMatchers("*/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(new JWTLoginFilter(authenticationManager()))
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()));
        }else{
            http.cors().and().csrf().disable().authorizeRequests()
                    .antMatchers("user-login/verify-account").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(new JWTLoginFilter(authenticationManager()))
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()));
        }

    }

}
