package practice;

import java.nio.ByteBuffer;

import static utils.ByteBufferUtil.debugAll;

/**
 * @author: 小手WA凉
 * @create: 2024-09-27
 */
public class BufferTest {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        //                     11            24
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);

        source.put("w are you?\nhaha!\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        int len = source.limit();
        for (int i = 0; i < len; i++) {
            if (source.get(i) == '\n') {//得到一个完整字符串
                ByteBuffer target = ByteBuffer.allocate(i - source.position());
                source.limit(i);
                //从source的[position,limit]复制到target，复制完后source的position变为limit
                target.put(source);
                debugAll(target);
                source.limit(len);
                source.position(source.position() + 1);
            }
        }
        source.compact();
    }
}
