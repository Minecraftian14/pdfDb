package in.mcxiv14.pdfDb.regexGen.strategies;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.util.Patterns;
import in.mcxiv14.pdfDb.regexGen.Strategy;

import java.util.regex.Pattern;

public class KVStrategyProvider implements Strategy.StrategyProvider {

    public Strategy apply(Fabric space, String key, String value) {
        var rgxPattern = Pattern.compile("%s\\s+[:-]\\s+(%s)".formatted(key, Patterns.rgxGenericValue.pattern()));
        var matcher = rgxPattern.matcher(space.getSource());
        if (matcher.find() && value.equals(matcher.group(1)))
            return new SinglePatternStrategy(rgxPattern);
        return null;
    }

}
