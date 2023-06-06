package com.example.own.server.ioclient;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @desc: 模拟netty客户端
 * @author:lishuang
 * @date: 2023-05-27 23:45:44
 *
 */
public class IOClient {


    public static void main(String[] args) throws Exception{

        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + "hello world").getBytes());
                        Thread.sleep(2000);
                    }catch (Exception e) {

                    }
                }
            } catch (Exception e) {

            }

        }).start();

















    }









}
