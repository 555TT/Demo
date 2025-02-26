package other.JVM;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 小手WA凉
 * @create: 2024-10-11
 */
public class OOMDemo {
    /**
     * 演示OOM
     * @param args
     */

    public static void main(String[] args) {
        // 创建一个列表来保存大对象
        List<byte[]> list = new ArrayList<>();

        int size = 1 * 1024 * 1024; // 每次分配1MB的内存

        try {
            while (true) {
                // 分配1MB的字节数组
                byte[] bytes = new byte[size];
                list.add(bytes); // 将字节数组添加到列表中，防止被垃圾回收

                System.out.println("Allocated " + (list.size() * size / (1024 * 1024)) + " MB");
            }
        } catch (OutOfMemoryError e) {
            System.err.println("OutOfMemoryError caught!");
            e.printStackTrace();
        }
    }
}
