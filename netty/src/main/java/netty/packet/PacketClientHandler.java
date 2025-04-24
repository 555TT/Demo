package netty.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 @author: wanghaoran1
 @create: 2025-04-24
 */
public class PacketClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 100; i++) {
            String msg = "哈哈哈哈";
            byte[] bytes = msg.getBytes();
            MessageProtocol messageProtocol = new MessageProtocol(bytes.length, bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }
}
