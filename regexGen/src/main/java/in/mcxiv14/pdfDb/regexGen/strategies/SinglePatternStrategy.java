package in.mcxiv14.pdfDb.regexGen.strategies;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.regexGen.Strategy;

import java.util.regex.Pattern;

public class SinglePatternStrategy implements Strategy {

    private Pattern pattern;

    public SinglePatternStrategy() {
    }

    public SinglePatternStrategy(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String process(Fabric space) {
        return space.find(pattern);
    }

}
