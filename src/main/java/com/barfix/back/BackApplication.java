package com.barfix.back;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication
public class BackApplication {
    public static SecretKey secretKey = Keys.hmacShaKeyFor("lksadjsknvcsklavnwiroufhuipwuwhifuhkadkljnvrtshsgffgnlkjxzncvzxncvxzcnaldkfnaialknv".getBytes());


    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

}
