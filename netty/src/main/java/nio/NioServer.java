package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 这其实不是纯粹的NIO，而是IO多路复用
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(8080));
        //将channel注册到这个selector上，并interest accept事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("NIO服务器启动，监听端口：8080");

        while (true) {
            try {
                // 阻塞到所有事件上，不单单是accept
                selector.select();
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
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel client = channel.accept();
        //将accept到的channel设为非阻塞
        client.configureBlocking(false);
        //将accept到的channel注册到selector上
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("客户端连接: " + client.getRemoteAddress());
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            int read = channel.read(buffer);

            if (read == -1) {
                // 客户端正常关闭连接，服务端会read到-1
                System.out.println("客户端关闭连接: " + channel.getRemoteAddress());
                key.cancel();
                channel.close();
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
                channel.write(writeBuffer);
            }
        } catch (IOException e) {
            // 客户端异常断开也会发送一个数据，客户端read
            System.out.println("客户端异常断开: " + channel.getRemoteAddress());
            key.cancel();
            channel.close();
        }
    }
}