package singleton;

import java.io.Serializable;

/**
 * 懒汉式单例模式
 *
 * @author: 小手WA凉
 * @create: 2024-07-06
 */
public class LazySingleton implements Serializable {
    private static LazySingleton lazySingleton = null;

    private LazySingleton() {

    }

    //特点:第一次调用才初始化，避免内存浪费。
    public synchronized static LazySingleton getInstance() {
        if (lazySingleton == null) {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }

    private Object readResolve() {
        return lazySingleton;
    }
}
