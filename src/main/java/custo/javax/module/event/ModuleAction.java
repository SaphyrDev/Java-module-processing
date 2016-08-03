package custo.javax.module.event;

import custo.javax.module.model.Module;

import java.util.EventObject;

/**
 * Created by linneya on 30/06/16.
 */
public class ModuleAction extends EventObject {
    public ModuleAction(Module module) {
        super(module);
    }

    public Module getSource() {
        return (Module) super.getSource();
    }
}
