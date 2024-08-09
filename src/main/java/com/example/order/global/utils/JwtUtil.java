package com.example.order.global.utils;

import com.example.order.domain.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final Long expiration;
    private final SecretKey secretKey;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") Long expiration
    ) {
        this.expiration = expiration;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Customer getCustomerFromToken(String token){
        Claims payload = (Claims) Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long customerId = payload.get("id", Long.class);
        String customerEmail = payload.get("email", String.class);
        String customerNickname = payload.get("nickname", String.class);
        String customerAddress = payload.get("address", String.class);
        double latitude = payload.get("latitude", Double.class);
        double longitude = payload.get("longitude", Double.class);
        return new Customer(customerId, customerNickname, customerEmail, customerAddress, latitude, longitude);
    }



}
