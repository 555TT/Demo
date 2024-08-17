package singleton;

/**双重检查单例模式
 * @author: 小手WA凉
 * @create: 2024-07-06
 */
public class DoubleCheckSingletion {
    private static DoubleCheckSingletion doubleCheckSingletion=null;
    private DoubleCheckSingletion(){

    }
    //特点:安全且在多线程情况下能保持高性能
    public static DoubleCheckSingletion getInstance(){
        if(doubleCheckSingletion==null){
            synchronized (DoubleCheckSingletion.class){
                if(doubleCheckSingletion==null){
                    doubleCheckSingletion=new DoubleCheckSingletion();
                }
            }
        }
        return doubleCheckSingletion;
    }
}
