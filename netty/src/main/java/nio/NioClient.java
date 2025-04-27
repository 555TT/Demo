package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {
    public static void main(String[] args) {
        SocketChannel clientChannel = null;
        try {
            // 1. 创建并配置SocketChannel
            clientChannel = SocketChannel.open();
            clientChannel.configureBlocking(false);

            // 2. 连接服务器
            System.out.println("正在连接服务器...");
            clientChannel.connect(new InetSocketAddress("localhost", 8080));

            // 等待连接完成
            while (!clientChannel.finishConnect()) {
                System.out.println("等待连接建立...");
                Thread.sleep(100);
            }
            System.out.println("已连接到服务器");
            System.in.read();

            // 3. 发送数据
            String message = "Hello, NIO Server!";
            ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes());
            while (writeBuffer.hasRemaining()) {
                clientChannel.write(writeBuffer);
            }
            System.out.println("已发送消息: " + message);

            // 4. 接收响应
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int bytesRead;
            while ((bytesRead = clientChannel.read(readBuffer)) != -1) {
                if (bytesRead > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String response = new String(bytes);
                    System.out.println("收到服务器响应: " + response);
                    break; // 收到响应后退出循环
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 5. 正常关闭连接
            if (clientChannel != null) {
                try {
                    System.out.println("正在关闭客户端连接...");
                    clientChannel.close();
                    System.out.println("客户端已关闭");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}