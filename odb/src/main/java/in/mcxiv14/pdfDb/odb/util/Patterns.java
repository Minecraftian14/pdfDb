package in.mcxiv14.pdfDb.odb.util;

import java.util.regex.Pattern;

public class Patterns {

    public static final Pattern rgxGenericToken = Pattern.compile("[a-zA-Z0-9_#$,./\\-]+");
    public static final Pattern rgxGenericValue = Pattern.compile("%1$s(?:  ?%1$s)*".formatted(rgxGenericToken.pattern()));

}
