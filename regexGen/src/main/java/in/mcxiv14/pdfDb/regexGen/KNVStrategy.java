package in.mcxiv14.pdfDb.regexGen;

import in.mcxiv14.pdfDb.odb.objects.Fabric;

import java.util.Objects;
import java.util.regex.Pattern;

public class KNVStrategy {

    private final Pattern rgxKey;
    private final Pattern rgxValue;

    public KNVStrategy(Pattern rgxKey, Pattern rgxValue) {
        this.rgxKey = rgxKey;
        this.rgxValue = rgxValue;
    }

    public static KNVStrategy apply(Fabric space, String key, String value) {
        var rgxKey = Pattern.compile("^(?<previousColumns>.*)\\b%s\\b(?:(?<padding> +).*)?[\\n\\r]+(?<nextLine>.*)$".formatted(key),
                Pattern.MULTILINE);

        var matcherKey = rgxKey.matcher(space.getSource());
        if (!matcherKey.find()) return null;
        var columnPosition = matcherKey.group("previousColumns").length();
        var columnWidth = matcherKey.group("padding").length() + key.length() ;
        var nextLine = matcherKey.group("nextLine");

        var rgcValue = Pattern.compile("^.{%d}\\b(.{%d})\\b".formatted(columnPosition, columnWidth));
        var matcherValue = rgcValue.matcher(nextLine);
        if (!matcherValue.find()) return null;
        var match = matcherValue.group(1).stripTrailing();

        if (!Objects.equals(match, value)) return null;

        return new KNVStrategy(rgxKey, rgcValue);
    }

    public String process(Fabric space) {
        var matcherKey = rgxKey.matcher(space.getSource());
        if (!matcherKey.find()) return null;
        var nextLine = matcherKey.group("nextLine");
        var matcherValue = rgxValue.matcher(nextLine);
        if (!matcherValue.find()) return null;
        return matcherValue.group(1).stripTrailing();
    }

}
