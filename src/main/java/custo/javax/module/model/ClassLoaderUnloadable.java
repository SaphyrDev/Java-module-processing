package custo.javax.module.model;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by linneya on 30/06/16.
 */
public class ClassLoaderUnloadable extends URLClassLoader {

    private static ClassLoaderUnloadable mkInstance(URLClassLoader classLoader) {
        return new ClassLoaderUnloadable(classLoader.getURLs(), classLoader.getParent());
    }

    public ClassLoaderUnloadable(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static ClassLoaderUnloadable newInstance(URL[] urls, ClassLoader parent) {
        return ClassLoaderUnloadable.mkInstance(URLClassLoader.newInstance(urls, parent));
    }

    public void close() {
        try {
            super.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
