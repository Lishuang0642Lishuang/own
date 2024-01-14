package com.example.own.service.htmltrans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser2 {


    public static void main(String[] args) {
        // 文件路径
        String filePath = "C:\\Users\\link.li\\IdeaProjects\\own\\service\\src\\main\\java\\com\\example\\own\\service\\htmltrans\\TestReport.html";

        Set<String> urlSet = new HashSet<>();

        // 读取文件内容
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // 定义正则表达式
            String regex = "https://api-sit.ecoflow.com/*";
            Pattern pattern = Pattern.compile(regex);

            // 遍历每一行并进行匹配
            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);

                // 判断是否匹配
                while (matcher.find()) {
//                    System.out.println("Match found: " + matcher.group());
                    System.out.println(line);
                    urlSet.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(urlSet);
    }
}
