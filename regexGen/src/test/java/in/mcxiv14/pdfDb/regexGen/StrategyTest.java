package in.mcxiv14.pdfDb.regexGen;

import in.mcxiv14.pdfDb.regexGen.strategies.SinglePatternStrategy;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class StrategyTest {

    @Test
    void serializationTest() {
        var strategy = new SinglePatternStrategy(Pattern.compile("Hello"));
        var json = strategy.serialize();
        assertLinesMatch("""
                {
                  "strategyName" : "in.mcxiv14.pdfDb.regexGen.SinglePatternStrategy",
                  "strategy" : {
                    "pattern" : "Hello"
                  }
                }""".lines(), json.lines());
        var provider = ((Strategy.StrategyProvider) (a, b, c) -> null);
        var deserialized = provider.deserialize(json);
        assertEquals(strategy.serialize(), deserialized.serialize());
    }

}