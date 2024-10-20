package in.mcxiv14.pdfDb.regexGen.strategies;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.util.Patterns;
import in.mcxiv14.pdfDb.regexGen.Strategy;

import java.util.Objects;
import java.util.regex.Pattern;

public class KNVStrategyProvider implements Strategy.StrategyProvider {

    public record KNVStrategy(Pattern rgxKey, String rgxValue) implements Strategy {
        @Override
        public String process(Fabric space) {
            var matcherKey = rgxKey.matcher(space.getSource());
            if (!matcherKey.find()) return null;
            var keyPosition = matcherKey.group("keyPosition").length();
            var nextLine = matcherKey.group("nextLine");
            var matcherValue = Pattern.compile(rgxValue.formatted(Math.max(0, keyPosition - 2), keyPosition + 2), Pattern.MULTILINE).matcher(nextLine);
            if (!matcherValue.find()) return null;
            return matcherValue.group("value");
        }
    }

    public Strategy apply(Fabric space, String key, String value) {
        var rgxVerify = Pattern.compile(
                "^(?<keyPosition>.*)%s.*[\n\r]+(?<valuePosition>.*)(?<value>%s)"
                        .formatted(key, value),
                Pattern.MULTILINE);

        // Test KNV exists
        var matcherVerify = rgxVerify.matcher(space.getSource());
        if (!matcherVerify.find()) return null;
        var keyPosition = matcherVerify.group("keyPosition").length();
        var valuePosition = matcherVerify.group("valuePosition").length();
        var matchedValue = matcherVerify.group("value");
        if (Math.abs(keyPosition - valuePosition) > 2 || !Objects.equals(value, matchedValue)) return null;

        var rgxKey = Pattern.compile("^(?<keyPosition>.*)%s.*[\n\r]+(?<nextLine>.*)$".formatted(key), Pattern.MULTILINE);
        var rgxValue = "^.{%%d,%%d}?\\b(?<value>%s)".formatted(Patterns.rgxGenericValue.pattern());

        // Test our algo works
        var matcherKey = rgxKey.matcher(space.getSource());
        if (!matcherKey.find()) return null;
        // keyPosition = matcherKey.group("keyPosition").length(); // Will always be same as before
        var nextLine = matcherKey.group("nextLine");
        var matcherValue = Pattern.compile(rgxValue.formatted(Math.max(0, keyPosition - 2), keyPosition + 2), Pattern.MULTILINE).matcher(nextLine);
        if (!matcherValue.find()) return null;
        matchedValue = matcherValue.group("value");
        if (!Objects.equals(value, matchedValue)) return null;

        return new KNVStrategy(rgxKey, rgxValue);
    }

}
