package netty.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 @author: wanghaoran1
 @create: 2025-04-24
 */
@Slf4j
public class MessageDecoder extends ByteToMessageDecoder {

    int len = 0;

    /**
     *
     * @param ctx
     * @param in 客户端发来的byte数据
     * @param out 该解码器处理后放给下一个handler的数据
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        log.info("ByteBuf:{}", in.toString());
        if (in.readableBytes() >= 4) {
            if (len == 0) {
                len = in.readInt();//读取一个四个字节
            }
            if (in.readableBytes() < len) {
                log.info("数据量不够，等待下一次调用");
                return;
            }
            byte[] bytes = new byte[len];
            in.readBytes(bytes);
            MessageProtocol messageProtocol = new MessageProtocol(len, bytes);
            out.add(messageProtocol);
            len = 0;//重置为0
        }
    }
}
