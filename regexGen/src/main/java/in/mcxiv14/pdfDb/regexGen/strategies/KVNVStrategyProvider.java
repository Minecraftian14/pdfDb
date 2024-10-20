package in.mcxiv14.pdfDb.regexGen.strategies;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.util.CharGeometry;
import in.mcxiv14.pdfDb.odb.util.Patterns;
import in.mcxiv14.pdfDb.regexGen.Strategy;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KVNVStrategyProvider implements Strategy.StrategyProvider {

    public record KVNVStrategy(Pattern rgxKey, String rgxSecondValue, String rgxRemainingValue) implements Strategy {
        @Override
        public String process(Fabric space) {
            var matcherKey = rgxKey.matcher(space.getSource());
            if (!matcherKey.find()) return null;
            var keyPosition = matcherKey.group("keyPosition").length();
            var remainingLines = space.getSource().substring(matcherKey.end());
            var builder = new StringBuilder(matcherKey.group("firstValue"));
            var matcherValue = Pattern.compile(rgxSecondValue.formatted(keyPosition), Pattern.MULTILINE).matcher(remainingLines);
            if (!matcherValue.find()) return null;
            var offset = matcherValue.group("valuePosition").length();
            matcherValue = Pattern.compile(rgxRemainingValue.formatted(offset), Pattern.MULTILINE).matcher(remainingLines);
            while (matcherValue.find())
                builder.append(System.lineSeparator()).append(matcherValue.group("value"));
            return builder.toString();
        }
    }

    @Override
    public Strategy apply(Fabric space, String key, String value) {
        var headTails = value.split("[\n\r]+", 2);
        var tails = headTails[1].split("[\n\r]+");
        var decomposedTails = Arrays.stream(tails)
                .map("[\\n\\r]+(.*)%s.*"::formatted)
                .collect(Collectors.joining());
        var rgxVerify = Pattern.compile(
                ("^(?<keyPosition>.*)%s\\s{0,5}[:-]\\s{0,5}%s.*" + decomposedTails)
                        .formatted(key, headTails[0]),
                Pattern.MULTILINE);

        // Test KVNV exists
        var matcherVerify = rgxVerify.matcher(space.getSource());
        if (!matcherVerify.find()) return null;
        if (matcherVerify.groupCount() != 1 + tails.length) return null;
        // int keyPosition = matcherVerify.group("keyPosition").length();
        int offset = -1;
        for (int i = 2; i <= matcherVerify.groupCount(); i++) {
            var valuePosition = matcherVerify.group(i).length();
            if (offset == -1) {
                offset = valuePosition; // Get the first offset, all others should match
                // if (offset < keyPosition) return null; // Doubtful
            }
            if (offset != valuePosition) return null;
        }

        var rgxKey = Pattern.compile(
                "^(?<keyPosition>.*)%s\\s{0,5}[:-]\\s{0,5}(?<firstValue>%s).*[\\n\\r]+"
                        .formatted(key, Patterns.rgxGenericValue.pattern()),
                Pattern.MULTILINE);
        var rgxSecondValue = "^(?<valuePosition>.{%%d,}?)\\b(?<value>%s)\\b.*[\\n\\r]+"
                .formatted(Patterns.rgxGenericValue.pattern());
        var rgxRemainingValue = "^.{%%d}\\b(?<value>%s)\\b.*[\\n\\r]+"
                .formatted(Patterns.rgxGenericValue.pattern());

        // Test our algo works
        var matcherKey = rgxKey.matcher(space.getSource());
        if (!matcherKey.find()) return null;
        var keyPosition = matcherKey.group("keyPosition").length();
        var remainingLines = space.getSource().substring(matcherKey.end());
        var builder = new StringBuilder(matcherKey.group("firstValue"));
        var matcherValue = Pattern.compile(rgxSecondValue.formatted(keyPosition), Pattern.MULTILINE).matcher(remainingLines);
        // Note: kv is a subset of kvnv - which will fail here due to the next condition
        //  But we would rather have kv be matched by the kc strategy, so this condition
        //  will only be removed in the processing/extraction part.
        if (!matcherValue.find()) return null;
        offset = matcherValue.group("valuePosition").length();
        matcherValue = Pattern.compile(rgxRemainingValue.formatted(offset), Pattern.MULTILINE).matcher(remainingLines);
        while (matcherValue.find())
            builder.append(System.lineSeparator()).append(matcherValue.group("value"));
        var matchedValue = builder.toString();
        if (!CharGeometry.equalsIgnoringLineSeparators(value, matchedValue)) return null;

        return new KVNVStrategy(rgxKey, rgxSecondValue, rgxRemainingValue);
    }
}
