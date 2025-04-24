package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// BIO服务器示例
public class BioServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("BIO服务器启动，监听端口：8080");

        while (true) {
            Socket socket = serverSocket.accept(); // 阻塞等待客户端连接
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    String request;
                    while ((request = in.readLine()) != null) { // 阻塞读取数据
                        System.out.println("收到消息: " + request);
                        out.println("服务器响应: " + request); // 响应客户端
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}