package custo.javax.module.model;

import custo.javax.module.event.ModuleEvent;
import custo.javax.module.event.type.ModuleLoaded;
import custo.javax.module.event.type.ModuleRegistered;
import custo.javax.module.event.type.ModuleUnregistered;
import custo.javax.module.event.type.ModuleUnloaded;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by linneya on 30/06/16.
 */
public class ModuleFactory {

    private final LinkedList<ModuleEvent<ModuleRegistered>> createEvents = new LinkedList<>();
    private final LinkedList<ModuleEvent<ModuleUnregistered>> deleteEvents = new LinkedList<>();
    private final LinkedList<ModuleEvent<ModuleUnloaded>> unloadEvents = new LinkedList<>();
    private final LinkedList<ModuleEvent<ModuleLoaded>> loadEvents = new LinkedList<>();

    private final LinkedList<ModuleEvent<?>> actionEvents = new LinkedList<>();
    private final ArrayList<Module> modules = new ArrayList<>();
    public void register(Module module) {
        this.modules.add(module);
        this.createEvents.forEach(event -> event.onEvent(new ModuleRegistered(module)));
    }
    public void unregister(Module module) {
        this.deleteEvents.forEach(event -> event.onEvent(new ModuleUnregistered(module)));
        this.modules.remove(module);
    }

    /**
     * Do nothing except call all event defined by {@link ModuleFactory#setOnLoad(ModuleEvent)} and {@link ModuleFactory#setOnAction(ModuleEvent)}.
     * @param module the module to load
     */
    public void load(Module module) {
        module.newLoader();
        this.loadEvents.forEach(event -> event.onEvent(new ModuleLoaded(module)));
    }

    public void unload(Module module) {
        this.unloadEvents.forEach(event -> event.onEvent(new ModuleUnloaded(module)));
        module.getLoader().close();
    }

    public void reload(Module module) {
        unload(module);
        load(module);
    }

    public void realoadAll() {
        this.modules.forEach(module -> reload(module));
    }

    public void loadAll() {
        this.modules.forEach(module -> load(module));
    }

    public void setOnAction(ModuleEvent<?> event) {
        this.actionEvents.add(event);
    }

    public void setOnRegister(ModuleEvent<ModuleRegistered> event) {
        this.createEvents.add(event);
    }

    public void setOnUnregister(ModuleEvent<ModuleUnregistered> event) {
        this.deleteEvents.add(event);
    }

    public void setOnLoad(ModuleEvent<ModuleLoaded> event) {
        this.loadEvents.add(event);
    }

    public void setOnUnload(ModuleEvent<ModuleUnloaded> event) {
        this.unloadEvents.add(event);
    }

    public ArrayList<Module> getHandledPlugin() {
        return this.modules;
    }

}
