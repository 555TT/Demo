package nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static utils.ByteBufferUtil.debugAll;

/**
 * 将接受accept事件和处理具体读写分开
 * 主线程中的selector只负责处理accept连接，具体的read由新线程去处理
 @author: wanghaoran1
 @create: 2025-04-27
 */
@Slf4j
public class MultiThreadNioServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        ssc.register(boss, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        Worker worker = new Worker("worker-0");
        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    //System.out.println(ssc == channel);true
                    //得到客户端的channel
                    SocketChannel sc = channel.accept();
                    log.info("connected:{}", sc.getRemoteAddress());
                    sc.configureBlocking(false);
                    log.info("before register:{}", sc.getRemoteAddress());
                    worker.register(sc);
                    log.info("after register:{}", sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Selector selector;
        private Thread thread;
        private String name;
        private boolean isRegister = false;

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel socketChannel) throws IOException {
            if (!isRegister) {
                thread = new Thread(this, name);
                selector = Selector.open();
                thread.start();
                isRegister = true;
            }
            selector.wakeup();
            socketChannel.register(selector, SelectionKey.OP_READ);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    //selector被select方法阻塞住时，其它线程向selector上注册socketChannel是注册不成功的
                    //在jdk8注册时会被阻塞住，后续jdk中虽然不会阻塞，但注册不成功
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isReadable()) {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            channel.read(byteBuffer);//向byteBuffer里写数据
                            byteBuffer.flip();
                            debugAll(byteBuffer);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
