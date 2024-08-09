package com.example.order.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer  {

    private Long customerId;

    private String customerNickname;

    private String customerEmail;

    private String customerAddress;

    private double customerLatitude;

    private double customerLongitude;

//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(()->"ROLE_USER");
//    }
}