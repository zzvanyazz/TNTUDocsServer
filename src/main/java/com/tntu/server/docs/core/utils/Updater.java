package com.tntu.server.docs.core.utils;

import javax.validation.constraints.NotNull;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Updater<T> {

    private final T target;
    private T source;

    public Updater(T target) {
        this.target = target;
    }

    public Updater(T target, @NotNull T source) {
        this.source = source;
        this.target = target;
    }

    public <D> void update(@NotNull Function<T, D> getter, @NotNull BiConsumer<T, D> setter) {
        var sourceValue = getter.apply(source);
        if (sourceValue == null)
            return;
        setter.accept(target, sourceValue);
    }

    public <D> void update(@NotNull BiConsumer<T, D> setter, D data) {
        setter.accept(target, data);
    }
}
