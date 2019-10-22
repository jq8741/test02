package com.jh.springbootmybatisplus;

import com.google.gson.Gson;
import com.jh.springbootmybatisplus.mapper.UserMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@SpringBootApplication
public class SpringbootmybatisplusApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringbootmybatisplusApplication.class, args);
    }
}
