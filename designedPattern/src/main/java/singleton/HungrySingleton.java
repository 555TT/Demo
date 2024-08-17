package singleton;

/**饿汉式单例模式
 * @author: 小手WA凉
 * @create: 2024-07-06
 */
public class HungrySingleton {
    static final HungrySingleton hungrySingleton=new HungrySingleton();
    private HungrySingleton(){

    }
    //特点:类加载时就初始化,线程安全
    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }
}
