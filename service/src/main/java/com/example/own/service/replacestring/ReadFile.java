package com.example.own.service.replacestring;

import org.apache.commons.text.StringSubstitutor;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author link.li
 * @date 2023/4/6
 */
public class ReadFile {




        public static String readFileAsString(String fileName) throws Exception{
            String data = "";
            data = new String(Files.readAllBytes(Paths.get(fileName)));



            Map<String, String> valueMap = new HashMap<>(4);
            valueMap.put("methodName", "hhh");
            valueMap.put("apiDesc", "desc");
            StringSubstitutor sub = new StringSubstitutor(valueMap);
            String resolvedString = sub.replace(data);

            return resolvedString;
        }

        public static void main(String[] args) throws Exception{
            String data = readFileAsString("D:\\vim\\file\\new.txt");
            System.out.println(data);
        }

}
