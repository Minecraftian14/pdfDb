package in.mcxiv14.pdfDb.regexGen;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.objects.comm.Hint;
import in.mcxiv14.pdfDb.regexGen.Strategy.StrategyProvider;
import in.mcxiv14.pdfDb.regexGen.strategies.KNVNVStrategyProvider;
import in.mcxiv14.pdfDb.regexGen.strategies.KNVStrategyProvider;
import in.mcxiv14.pdfDb.regexGen.strategies.KVNVStrategyProvider;
import in.mcxiv14.pdfDb.regexGen.strategies.KVStrategyProvider;

import java.util.List;

public class SolutionRegress {

    List<StrategyProvider> strategyProviders = List.of(
            new KVStrategyProvider(),
            new KNVStrategyProvider(),
            new KNVNVStrategyProvider(),
            new KVNVStrategyProvider()
    );

    public Strategy regressUsingKey(Fabric content, String key) {
        throw new UnsupportedOperationException("Local LLM integration no yet implemented in spring application!");
    }

    public Strategy regressUsingValue(Fabric content, String value) {
        throw new UnsupportedOperationException("Local LLM integration no yet implemented in spring application!");
    }

    public Strategy regressUsingGenAI(Fabric content, Hint hints) {
        throw new UnsupportedOperationException("Local LLM integration no yet implemented in spring application!");
    }

    public Strategy regress(Fabric content, String key, String value) {
        for (StrategyProvider provider : strategyProviders) {
            var strategy = provider.apply(content, key, value);
            if (strategy != null) return strategy;
        }
        return null;
    }
}
