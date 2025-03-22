package singleton.broken;

import singleton.LazySingleton;

import java.io.*;

/**
 * 序列化破坏单例
 *
 * @author: 小手WA凉
 * @create: 2024-07-06
 */
public class BrokenSingletonTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LazySingleton instance = LazySingleton.getInstance();
        //序列化破坏单例模式
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singletion"));
        oos.writeObject(instance);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("singletion"));
        LazySingleton instance2 = (LazySingleton) ois.readObject();
        System.out.println(instance);
        System.out.println(instance2);
        System.out.println(instance2 == instance);
//        EnumSingleton instance = EnumSingleton.getInstance();
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singletion"));
//        oos.writeObject(instance);
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("singletion"));
//        EnumSingleton instance2 = (EnumSingleton) ois.readObject();
//        System.out.println(instance);
//        System.out.println(instance2);
//        System.out.println(instance==instance2);
    }
}
