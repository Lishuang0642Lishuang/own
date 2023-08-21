package com.example.own.api.controller;

import lombok.Data;

import java.util.List;

@Data
public class EnumJson {

    String defaultValue;

    List<EnumBean> enums;


}
