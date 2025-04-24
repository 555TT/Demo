package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 @author: wanghaoran1
 @create: 2025-04-23
 */
@Slf4j
public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(1);
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler((new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }));
            channelFuture = serverBootstrap.bind(9090);
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("服务端启动成功");
                } else {
                    log.info("服务端启动失败");
                }
            });
            log.info("服务端启动了");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("服务端启动失败", e);
        } finally {
            if (channelFuture != null) {
                channelFuture.channel().close();
            }
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            log.info("Netty服务器已关闭");
        }
    }
}
