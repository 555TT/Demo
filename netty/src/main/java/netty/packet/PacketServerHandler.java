package netty.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 @author: wanghaoran1
 @create: 2025-04-24
 */
@Slf4j
public class PacketServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) {
        log.info("服务端收到消息，长度为：{}，内容：{}", msg.getLength(), new String(msg.getContent(), StandardCharsets.UTF_8));
    }
}
