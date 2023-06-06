package com.example.own.server.ioserver;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @desc: 模拟netty服务端
 * @author:lishuang
 * @date: 2023-05-27 23:26:40
 *
 */
public class IOServer {


    public static void main(String[] args) throws Exception{

        ServerSocket serverSocket = new ServerSocket(8000);

        new Thread( () -> {

            while (true) {
                try {
                    //(1)、阻塞方法获取新链接
                    Socket socket = serverSocket.accept();

                    //(2)、为每一个新连接都创建一个新线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            //(3)、按字节流方式读取数据
                            while ((len = inputStream.read(data)) !=-1) {
                                System.out.println(new String(data, 0, len));
                                System.out.println(Thread.currentThread().getName());
                            }
                        } catch (Exception e) {
                        }
                    }
                    ).start();
                } catch (Exception e) {
                }
            }
        }
        ).start();
    }
}
