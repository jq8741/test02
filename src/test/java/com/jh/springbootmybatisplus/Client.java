package com.jh.springbootmybatisplus;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Client {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("123");
        System.out.println(pwd);
    }
}
