package org.maroubra.pemsserver.bindings;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

import java.util.List;

public final class GuiceInjectorHolder {
    private static Injector injector;

    private GuiceInjectorHolder() {
    }

    public static Injector createInjector(final List<Module> bindingsModules) {
        if (injector == null) {
            injector = Guice.createInjector(Stage.PRODUCTION, bindingsModules);
        }

        return injector;
    }

    public static Injector getInjector() {
        if (injector == null) {
            throw new IllegalStateException("No injector available. Please call createInjector() first.");
        }

        return injector;
    }
}
