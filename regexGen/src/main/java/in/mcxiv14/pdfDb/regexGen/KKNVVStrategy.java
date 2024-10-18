package in.mcxiv14.pdfDb.regexGen;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.util.Patterns;

import java.util.Objects;
import java.util.regex.Pattern;

public class KKNVVStrategy {

    private final Pattern rgxValue;
    private final Pattern rgxKey;

    public KKNVVStrategy(Pattern rgxKey, Pattern rgxValue) {
        this.rgxKey = rgxKey;
        this.rgxValue = rgxValue;
    }

    public static KKNVVStrategy apply(Fabric space, String key, String value) {
        var rgxKey = Pattern.compile("^((?: +%1$s)*) +%2$s( +%1$s)* *$".formatted(Patterns.rgxGenericValue.pattern(), key),
                Pattern.MULTILINE);
        var matcherKey = rgxKey.matcher(space.getSource());
        if (!matcherKey.find()) return null;
        var precedingColumns = matcherKey.group(1);
        var precedingColumnsCount = precedingColumns.split(" {2,}").length;
        var remainingLines = space.getSource().substring(matcherKey.end());

        var rgxValue = Pattern.compile("^(?: +%1$s){%2$d} +(%1$s)".formatted(Patterns.rgxGenericValue.pattern(), precedingColumnsCount),
                Pattern.MULTILINE);
        var matcherValue = rgxValue.matcher(remainingLines);
        if (!matcherValue.find() && Objects.equals(matcherValue.group(1), value)) return null;

        return new KKNVVStrategy(rgxKey, rgxValue);
    }

    public String process(Fabric space) {
        var matcherKey = rgxKey.matcher(space.getSource());
        if (!matcherKey.find()) return null;
        var remainingLines = space.getSource().substring(matcherKey.end());

        var matcherValue = rgxValue.matcher(remainingLines);
        return matcherValue.find() ? matcherValue.group(1) : null;
    }

}
