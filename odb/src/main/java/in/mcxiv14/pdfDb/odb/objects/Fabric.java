package in.mcxiv14.pdfDb.odb.objects;

import in.mcxiv.tryCatchSuite.DangerousFunction;
import in.mcxiv.tryCatchSuite.DangerousSupplier;
import lombok.Data;

import java.util.function.Function;
import java.util.regex.Pattern;

@Data
public class Fabric {
    String source;
    String[] vLines;
    String[] hLines;

    public Fabric(String source) {
        this.source = source.replace('\u00A0', ' ');
    }

    @Override
    public String toString() {
        return "<Fabric>%n%s%n</Fabric>".formatted(source);
    }

    public static <T extends AutoCloseable> Function<DangerousFunction<T, Fabric>, Fabric> fabricateC(DangerousSupplier<T> supplier) {
        return action -> {
            try (T t = supplier.get()) {
                return action.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <T> Function<DangerousFunction<T, Fabric>, Fabric> fabricate(DangerousSupplier<T> supplier) {
        return action -> {
            try {
                return action.apply(supplier.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public String find(Pattern pattern) {
        var matcher = pattern.matcher(this.source);
        if (matcher.find()) return matcher.group(1);
        return null;
    }
}
