package in.mcxiv14.pdfDb.regexGen;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.mcxiv.tryCatchSuite.Try;
import in.mcxiv14.pdfDb.odb.objects.Fabric;

public interface Strategy {

    ObjectMapper MAPPER = new ObjectMapper() {{
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }};

    String process(Fabric space);

    default String serialize() {
        var writer = MAPPER.writer().withDefaultPrettyPrinter();
        return Try.get(() -> writer.writeValueAsString(new StrategyContainer(this)));
    }

    interface StrategyProvider {

        Strategy apply(Fabric space, String key, String value);

        default Strategy deserialize(String strategy) {
            var reader = MAPPER.reader();
            var tree = Try.get(() -> reader.readTree(strategy));
            var clazz = Try.get(() -> Class.forName(tree.get("strategyName").asText()));
            return Try.get(() -> reader.forType(clazz).readValue(tree.get("strategy")));
        }

    }

    class StrategyContainer {
        String strategyName;
        Strategy strategy;

        StrategyContainer(Strategy strategy) {
            this.strategyName = strategy.getClass().getCanonicalName();
            this.strategy = strategy;
        }
    }

}
