package com.example.own.server.ioclient;

import java.net.Socket;
import java.util.Date;

public class IOClient2 {


    public static void main(String[] args) throws Exception {

        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + "hello world2").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {

            }

        }).start();
    }
}
