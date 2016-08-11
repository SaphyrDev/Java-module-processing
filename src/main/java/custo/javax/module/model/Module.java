package custo.javax.module.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.jar.JarFile;

/**
 * Created by linneya on 30/06/16.
 */
public class Module {
    private Path path = null;
    private final HashMap<String, Object> properties = new HashMap<>();
    private ClassLoaderUnloadable loader;// = (ClassLoaderUnloadable) ClassLoaderUnloadable.newInstance(new URL[]{this.path.toUri().toURL()}, Module.class.getClassLoader());

    public Module(JarFile jarFile) throws IOException {
        this(Paths.get(jarFile.getName()));
    }

    public Module(Path path) throws MalformedURLException {
        try {
            new JarFile(path.toFile());
        } catch (IOException e) {
            System.out.println("Not a jarfile's path");
        }
        this.path = path;
    }



    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public JarFile getJarFile() {
        try {
            return new JarFile(this.path.toFile());
        } catch (IOException e) {
            System.out.println("How did you do this?");
            return null;
        }
    }

    /**
     * An Unmodifiable access to the ClassLoader
     * @return
     */
    public final ClassLoaderUnloadable getLoader() {
        return  loader;
    }

    public void newLoader() {
        try {
            this.loader = ClassLoaderUnloadable.newInstance(new URL[]{this.path.toUri().toURL()}, Module.class.getClassLoader());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
