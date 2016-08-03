package custo.javax.module.event;

import custo.javax.module.model.error.ModuleFormatException;

import java.util.EventListener;

/**
 * Created by linneya on 30/06/16.
 */
@FunctionalInterface
public interface ModuleEvent<T extends ModuleAction> extends EventListener {
    void onEvent(T action);
}
