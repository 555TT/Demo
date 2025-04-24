package netty.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 @author: wanghaoran1
 @create: 2025-04-24
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) {
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
