package com.example.own.server.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Session {

    private String userId;

    private String userName;
}
