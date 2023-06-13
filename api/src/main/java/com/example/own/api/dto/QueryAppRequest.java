package com.example.own.api.dto;

import com.example.own.api.enumpackage.AppEnum;
import com.example.own.common.annotation.EnumValid;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class QueryAppRequest implements Serializable {

    String id;

    @EnumValid(message = "枚举不符合规范", target = AppEnum.class, field = "code")
    @NotNull
    Integer code;


}
