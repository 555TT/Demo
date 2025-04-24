package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(8080));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("NIO服务器启动，监听端口：8080");

        while (true) {
            try {
                selector.select(); // 阻塞直到有就绪事件
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iter = keys.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();

                    if (key.isValid()) { // 检查key是否仍然有效
                        if (key.isAcceptable()) {
                            handleAccept(key, selector);
                        }

                        if (key.isReadable()) {
                            handleRead(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                // 可以选择继续运行或退出
            }
        }
    }

    private static void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel client = server.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("客户端连接: " + client.getRemoteAddress());
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            int read = client.read(buffer);

            if (read == -1) {
                // 客户端正常关闭连接
                System.out.println("客户端关闭连接: " + client.getRemoteAddress());
                key.cancel();
                client.close();
                return;
            }

            if (read > 0) {
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String request = new String(bytes);
                System.out.println("收到消息: " + request);

                // 响应客户端
                String response = "服务器响应: " + request;
                ByteBuffer writeBuffer = ByteBuffer.wrap(response.getBytes());
                client.write(writeBuffer);
            }
        } catch (IOException e) {
            // 客户端异常断开
            System.out.println("客户端异常断开: " + client.getRemoteAddress());
            key.cancel();
            client.close();
        }
    }
}