package org.projectbot;

import lombok.Data;
import org.projectbot.inter.Engine;
import org.projectbot.inter.EngineMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public final class EngineManager {
    private final List<Engine> engines = new LinkedList<>();
    private final Map<EngineMeta, Engine> metas = new HashMap<>();

    public void registerEngine(Class<? extends Engine> engine) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        EngineMeta meta = engine.getAnnotation(EngineMeta.class);
        Engine engine1 = engine.getDeclaredConstructor(EngineMeta.class).newInstance(meta);
        engines.add(engine1);
        metas.put(meta, engine1);
    }

    public Engine getEngine() {
        if (engines.size() != 1) throw new IllegalStateException("Only one engine can be registered for this method to work!");
        return this.engines.get(0);
    }

    public Engine getEngineByName(String name) {
        for (EngineMeta engineMeta : metas.keySet()) {
            if (engineMeta.name().equalsIgnoreCase(name)) return metas.get(engineMeta);
        }
        return null;
    }
}
