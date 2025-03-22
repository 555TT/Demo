package singleton;

/**
 * @author: 小手WA凉
 * @create: 2024-07-06
 */
public class Test {
    public static void main(String[] args) {
        LazySingleton instance = LazySingleton.getInstance();
        LazySingleton instance1 = LazySingleton.getInstance();
        System.out.println(instance1 == instance);
    }
}
