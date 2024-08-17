package singleton;

/**静态内部类单例模式
 * @author: 小手WA凉
 * @create: 2024-07-06
 */
public class StaticInnerClassSingleton {
    private static class InnerClass{
        private static StaticInnerClassSingleton staticInnerClassSingleton=new StaticInnerClassSingleton();
    }
    private StaticInnerClassSingleton(){

    }

    public static StaticInnerClassSingleton getInstance(){
        return InnerClass.staticInnerClassSingleton;
    }
}
