package in.mcxiv14.pdfDb.regexGen;

import in.mcxiv14.pdfDb.odb.objects.Fabric;

public class SolutionRegress {
    public void regress(Fabric content, String key) {
        // AI Solution
    }

    public void regress(Fabric content, String key, String value) {
        var strategy=KVStrategy.apply(content, key, value);
//        if (strategy!=null) return;
        System.out.println(strategy.process(content));

    }
}
