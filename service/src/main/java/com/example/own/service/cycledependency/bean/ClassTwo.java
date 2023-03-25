package com.example.own.service.cycledependency.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ClassTwo {

    private ClassOne classOne;
}
