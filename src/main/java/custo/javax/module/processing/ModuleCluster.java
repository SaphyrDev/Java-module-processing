package custo.javax.module.processing;

import custo.java.nio.Directory;
import custo.javax.module.model.Module;
import custo.javax.module.model.ModuleFactory;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.HashMap;

/**
 * Created by linneya on 30/06/16.
 */
public class ModuleCluster {
    private final static HashMap<String, ModuleFactory> factories = new HashMap<>();
    private final static HashMap<ModuleFactory, Directory> poolModules = new HashMap<>();

    public static HashMap<String, ModuleFactory> getFactories() {
        return factories;
    }

    public static void generatePoolModule(ModuleFactory factory, Directory dir) {
        try {
            dir.getSubPaths().forEach(path -> {
                try {
                    factory.register(new Module(path));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        dir.addDirectoryListener((key, events) -> events.forEach(event -> {
            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                try {
                    factory.register(new Module((Path) event.context()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                try {
                    factory.unregister(new Module((Path) event.context()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public static void generatePoolModule(ModuleFactory factory, String dir) {
        try {
            ModuleCluster.generatePoolModule(factory, new Directory(dir));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}