package spring.ioc;

import spring.ioc.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 小手WA凉
 * @create: 2024-09-06
 */
public class MyAnnotationApplicationContext implements MyApplicationContext {
    //bean容器
    private Map<Class<?>, Object> beanFactory = new HashMap<>();
    private String rootPath;

    /**
     * 根据包扫描加载bean
     *
     * @param basePackage
     */
    public MyAnnotationApplicationContext(String basePackage) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String packageDirName = basePackage.replaceAll("\\.", "\\\\");
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
            rootPath = filePath.substring(0, filePath.length() - packageDirName.length());
            loadBean(new File(filePath));
        }
    }

    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }

    private void loadBean(File fileParent) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (fileParent.isDirectory()) {//如果是文件夹
            File[] files = fileParent.listFiles();
            for (File file : files) {
                loadBean(file);
            }
        } else {//如果是文件
            String pathWithClass = fileParent.getAbsolutePath().substring(rootPath.length() - 1);
            if (pathWithClass.endsWith(".class")) {
                pathWithClass = pathWithClass.substring(0, pathWithClass.length() - 6);
                //得到类的全类名
                String fullName = pathWithClass.replaceAll("\\\\", ".").replaceAll(".class", "");
                Class<?> aClass = Class.forName(fullName);
                if (!aClass.isInterface()) {//将不是接口的类实例化
                    if (aClass.getAnnotation(Bean.class) != null) {//如果类上包含Bean注解
                        if (aClass.getInterfaces().length > 0) {//如果类有接口，将接口class作为key
                            beanFactory.put(aClass.getInterfaces()[0], aClass.newInstance());
                        } else {
                            beanFactory.put(aClass, aClass.newInstance());
                        }
                    }
                }
            }
        }

    }
}
