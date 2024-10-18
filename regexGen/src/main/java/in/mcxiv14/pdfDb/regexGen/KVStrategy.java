package in.mcxiv14.pdfDb.regexGen;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.util.Patterns;

import java.util.regex.Pattern;

public class KVStrategy {

    private final Pattern pattern;

    public KVStrategy(Pattern pattern) {
        this.pattern = pattern;
    }

    public static KVStrategy apply(Fabric space, String key, String value) {
        var rgxPattern = Pattern.compile("%s\\s+[:-]\\s+(%s)".formatted(key, Patterns.rgxGenericValue.pattern()));
        var matcher = rgxPattern.matcher(space.getSource());
        if (matcher.find() && value.equals(matcher.group(1)))
            return new KVStrategy(rgxPattern);
        return null;
    }

    public String process(Fabric space) {
        return space.find(pattern);
    }

}
