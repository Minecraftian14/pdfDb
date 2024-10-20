package in.mcxiv14.pdfDb.regexGen.strategies;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.util.CharGeometry;
import in.mcxiv14.pdfDb.odb.util.Patterns;
import in.mcxiv14.pdfDb.regexGen.Strategy;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KNVNVStrategyProvider implements Strategy.StrategyProvider {

    public record KNVNVStrategy(Pattern rgxKey, String rgxValue) implements Strategy {
        @Override
        public String process(Fabric space) {
            var matcherKey = rgxKey.matcher(space.getSource());
            if (!matcherKey.find()) return null;
            var keyPosition = matcherKey.group("keyPosition").length(); // Will always be same as before
            var remainingLines = space.getSource().substring(matcherKey.end());
            var matcherValue = Pattern.compile(rgxValue.formatted(Math.max(0, keyPosition - 2), keyPosition + 2), Pattern.MULTILINE).matcher(remainingLines);
            if (!matcherValue.find()) return null;
            StringBuilder builder = new StringBuilder(matcherValue.group("value"));
            var offset = matcherValue.group("valuePosition").length() - keyPosition;
            if (Math.abs(offset) > 2) return null;
            while (matcherValue.find() && keyPosition + offset == matcherValue.group("valuePosition").length())
                builder.append(System.lineSeparator()).append(matcherValue.group("value"));
            return builder.toString();
        }
    }

    @Override
    public Strategy apply(Fabric space, String key, String value) {
        var lines = value.split("[\n\r]+");
        var decomposedValue = Arrays.stream(lines)
                .map("[\\n\\r]+(.*)%s.*"::formatted)
                .collect(Collectors.joining());
        var rgxVerify = Pattern.compile(
                ("^(?<keyPosition>.*)%s.*" + decomposedValue)
                        .formatted(key),
                Pattern.MULTILINE);

        // Test KNVNV exists
        var matcherVerify = rgxVerify.matcher(space.getSource());
        if (!matcherVerify.find()) return null;
        if (matcherVerify.groupCount() != 1 + lines.length) return null;
        var keyPosition = matcherVerify.group("keyPosition").length();
        int offset = -1;
        for (int i = 2; i <= matcherVerify.groupCount(); i++) {
            var valuePosition = matcherVerify.group(i).length();
            if (offset == -1) {
                offset = valuePosition - keyPosition; // Get the first offset, all others should match
                if (Math.abs(offset) > 2) return null;
            }
            if (keyPosition + offset != valuePosition) return null;
        }

        var rgxKey = Pattern.compile("^(?<keyPosition>.*)%s.*[\\n\\r]+".formatted(key), Pattern.MULTILINE);
        var rgxValue = "^(?<valuePosition>.{%%d,%%d}?)\\b(?<value>%s)\\b.*[\\n\\r]+".formatted(Patterns.rgxGenericValue.pattern());

        // Test our algo works
        var matcherKey = rgxKey.matcher(space.getSource());
        if (!matcherKey.find()) return null;
        // keyPosition = matcherKey.group("keyPosition").length(); // Will always be same as before
        var remainingLines = space.getSource().substring(matcherKey.end());
        var matcherValue = Pattern.compile(rgxValue.formatted(Math.max(0, keyPosition - 2), keyPosition + 2), Pattern.MULTILINE).matcher(remainingLines);
        if (!matcherValue.find()) return null;
        var builder = new StringBuilder(matcherValue.group("value"));
        offset = matcherValue.group("valuePosition").length() - keyPosition;
        if (Math.abs(offset) > 2) return null;
        while (matcherValue.find() && keyPosition + offset == matcherValue.group("valuePosition").length())
            builder.append(System.lineSeparator()).append(matcherValue.group("value"));
        var matchedValue = builder.toString();
        if (!CharGeometry.equalsIgnoringLineSeparators(value, matchedValue)) return null;

        return new KNVNVStrategy(rgxKey, rgxValue);
    }

}
