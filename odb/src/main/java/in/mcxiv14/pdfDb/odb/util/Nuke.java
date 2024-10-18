package in.mcxiv14.pdfDb.odb.util;

import in.mcxiv.tryCatchSuite.DangerousRunnable;
import in.mcxiv.tryCatchSuite.DangerousSupplier;
import in.mcxiv.tryCatchSuite.Try;

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

    public static Supplier<Object> nukeR(DangerousRunnable initializer) {
        return new Nuke<>(() -> {
            initializer.run();
            return new Object();
        });
    }

}
