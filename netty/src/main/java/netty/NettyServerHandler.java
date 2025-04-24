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
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当客户端发来数据时该方法就会被调用
     * @param ctx channel上下文
     * @param msg 客户端发来消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("客户端发来的数据：{}", byteBuf.toString(StandardCharsets.UTF_8));
    }

    /**
     * 读完数据调用的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello client".getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(byteBuf);
    }

    /**
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.toString());
        ctx.close();
    }
}
