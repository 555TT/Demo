package netty.packet;

/**
 * 自定义消息协议
 @author: wanghaoran1
 @create: 2025-04-24
 */
public class MessageProtocol {

    private int length;
    private byte[] content;

    public MessageProtocol(int length, byte[] content) {
        this.length = length;
        this.content = content;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
