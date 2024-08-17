package singleton;

/**枚举形单例模式
 * @author: 小手WA凉
 * @create: 2024-07-06
 */
public enum EnumSingleton {
    INSTANCE;
    //特点:自动支持序列化机制，绝对防止多次实例化
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
