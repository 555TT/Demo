package netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 @author: wanghaoran1
 @create: 2025-04-24
 */
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //存放channel的容器
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String msg = "客户端" + channel.remoteAddress() + "于" + simpleFormatter.format(new Date()) + "上线了";
        log.info(msg);
        channelGroup.writeAndFlush(msg);
        channelGroup.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String msg = "客户端" + channel.remoteAddress() + "于" + simpleFormatter.format(new Date()) + "下线了";
        log.info(msg);
        channelGroup.writeAndFlush(msg);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("客户端" + ch.remoteAddress() + "于" + simpleFormatter.format(new Date()) + "说：" + msg + "\n");
            } else {
                ch.writeAndFlush("我自己于" + simpleFormatter.format(new Date()) + "说：" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage());
        ctx.close();
    }
}
