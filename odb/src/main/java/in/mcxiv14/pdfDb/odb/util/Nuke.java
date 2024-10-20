package in.mcxiv14.pdfDb.odb.util;

import in.mcxiv.tryCatchSuite.DangerousSupplier;
import in.mcxiv.tryCatchSuite.Try;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Nuke<Type> implements Supplier<Type> {

    Type instance = null;
    DangerousSupplier<Type> constructor;

    private Nuke(DangerousSupplier<Type> constructor) {
        this.constructor = constructor;
    }

    @Override
    public Type get() {
        if (instance == null) instance = Try.get(constructor);
        return instance;
    }

    public static <T> Supplier<T> nuke(DangerousSupplier<T> constructor) {
        return new Nuke<>(constructor);
    }

    private static final Map<Class<?>, Object> madScientistsLab = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T remember(Class<?> clazz, Supplier<T> constructor) {
        return (T) madScientistsLab.computeIfAbsent(clazz, __ -> constructor.get());
    }
}
