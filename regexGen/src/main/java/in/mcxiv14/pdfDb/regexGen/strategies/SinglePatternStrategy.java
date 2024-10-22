package in.mcxiv14.pdfDb.regexGen.strategies;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.regexGen.Strategy;

import java.util.regex.Pattern;

public record SinglePatternStrategy(String pattern) implements Strategy {
    @Override
    public String process(Fabric space) {
        return space.find(Pattern.compile(pattern, Pattern.MULTILINE));
    }
}
