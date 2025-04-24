package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 @author: wanghaoran1
 @create: 2025-04-23
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当客户端完成服务器连接时调用的方法：向服务端写数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello server".getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(byteBuf);
    }

    /**
     * 当通道中有读事件发生时调用的方法：读服务端的数据
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("来自服务端的数据：{}", byteBuf.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage());
        ctx.close();
    }
}
