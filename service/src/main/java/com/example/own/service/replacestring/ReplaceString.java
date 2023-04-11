package com.example.own.service.replacestring;

import java.text.MessageFormat;

/**
 * @author link.li
 * @date 2023/4/6
 */
public class ReplaceString {


    public static final String template = "hello,{0}, {} my name is {1}";

    public static void main(String[] args) {


        String newString = MessageFormat.format(template, "world", "lishuang");


        System.out.println(newString);
    }
}
