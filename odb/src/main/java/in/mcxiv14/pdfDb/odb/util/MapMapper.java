package in.mcxiv14.pdfDb.odb.util;

import in.mcxiv.tryCatchSuite.Try;

import java.util.function.Function;

public interface MapMapper<T, R> extends Function<T, R> {
    @Override
    default R apply(T t) {
        return Try.get(() -> apply2(t));
    }

    R apply2(T t) throws Exception;

    static <T, R> Function<T, R> unchecked(MapMapper<T, R> mapper) {
        return mapper;
    }
}
