package in.mcxiv14.pdfDb.odb.util;

import in.mcxiv.tryCatchSuite.Try;

import java.io.BufferedInputStream;

public class Resources {

    public static String readResource(Class<?> clazz, String resourceFile) {
        return Try.get(() -> new String(new BufferedInputStream(clazz.getClassLoader().getResourceAsStream(resourceFile)).readAllBytes()));
    }

}
