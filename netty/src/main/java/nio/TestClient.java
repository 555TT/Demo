package nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 @author: wanghaoran1
 @create: 2025-04-27
 */
@Slf4j
public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        socketChannel.write(Charset.defaultCharset().encode("aabb"));
        System.in.read();
    }
}
