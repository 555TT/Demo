package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 @author: wanghaoran1
 @create: 2025-04-23
 */
@Slf4j
public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 9090).sync();
            // 等待连接关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
