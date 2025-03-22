package singleton.broken;

import singleton.EnumSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射破坏单例模式
 *
 * @author: 小手WA凉
 * @create: 2024-07-06
 */
public class BrokenSingletonTest2 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        LazySingleton instance = LazySingleton.getInstance();
//        Class<LazySingleton> lazySingletonClass = LazySingleton.class;
//        Constructor<LazySingleton> constructor = lazySingletonClass.getDeclaredConstructor();
//        constructor.setAccessible(true);
//        LazySingleton refInstance = constructor.newInstance();
//        System.out.println(instance);
//        System.out.println(refInstance);
//        System.out.println(instance==refInstance);
        EnumSingleton instance = EnumSingleton.getInstance();
        Class<EnumSingleton> singletonClass = EnumSingleton.class;
        Constructor<EnumSingleton> constructor = singletonClass.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        EnumSingleton refInstance = constructor.newInstance();
        System.out.println(instance);
        System.out.println(refInstance);
        System.out.println(instance == refInstance);
    }
}
