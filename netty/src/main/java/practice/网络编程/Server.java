package practice.网络编程;

import lombok.extern.slf4j.Slf4j;
import utils.ByteBufferUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 小手WA凉
 * @create: 2024-10-02
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(8080));
        List<SocketChannel> channels = new ArrayList<SocketChannel>();
        while(true){
            SocketChannel socketChannel = ssc.accept();//非阻塞
            if(socketChannel != null){
                socketChannel.configureBlocking(false);
                channels.add(socketChannel);
                log.info("接收到客户端：",socketChannel);
            }
            for(SocketChannel channel : channels){
                int write = channel.read(buffer);//阻塞
                if(write > 0){
                    buffer.flip();
                    ByteBufferUtil.debugAll(buffer);
                    buffer.compact();
                }
            }
        }
    }
}
