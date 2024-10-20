package in.mcxiv14.pdfDb.regexGen;

import in.mcxiv14.pdfDb.TestUtil;
import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import in.mcxiv14.pdfDb.pdf.PDFDigesterAsposePDFImpl;
import in.mcxiv14.pdfDb.regexGen.strategies.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SolutionRegressTest {

    @Test
    void kvTest() {
        PDFDigester digester = new PDFDigesterAsposePDFImpl();

        Fabric contentTrain = digester.digest(TestUtil.locatePdf(2, 1));
        var strategy = new KVStrategyProvider().apply(contentTrain, "Customer VAT No", "GB222280111");

        Fabric contentTest = digester.digest(TestUtil.locatePdf(2, 5));
        Assertions.assertEquals("IE2732243D", strategy.process(contentTest));
    }

    @Test
    void knvTest() {
        PDFDigester digester = new PDFDigesterAsposePDFImpl();

        Fabric contentTrain = digester.digest(TestUtil.locatePdf(1, 1));
        var strategy = new KNVStrategyProvider().apply(contentTrain, "Invoice Number", "DM1329492VR");

        Fabric contentTest = digester.digest(TestUtil.locatePdf(1, 5));
        Assertions.assertEquals("DM54800008587RL", strategy.process(contentTest));
    }

    @Test
    void knvnvTest() {
        PDFDigester digester = new PDFDigesterAsposePDFImpl();

        Fabric contentTrain = digester.digest(TestUtil.locatePdf(2, 1));
        var strategy = new KNVNVStrategyProvider().apply(contentTrain, "Bill-to", """
                RB UK HYGIENE HOME
                COMMERCIAL LTD
                9 Wellcroft
                Road/Slough/Berkshire/SL1
                4AQ/UNITED KINGDOM""");

        Fabric contentTest = digester.digest(TestUtil.locatePdf(2, 5));
        Assertions.assertLinesMatch("""
                RECKITT BENCKISER IRELAND
                LTD
                7 Riverwalk/Citywest Business
                Campus/DUBLIN/IRELAND""".lines(), strategy.process(contentTest).lines());
    }

    @Test
    void kvnvTest() {
        PDFDigester digester = new PDFDigesterAsposePDFImpl();

        Fabric contentTrain = digester.digest(TestUtil.locatePdf(1, 1));
        var strategy = new KVNVStrategyProvider().apply(contentTrain, "Sold To", """
                XYZ  FOODS INC
                33308 TREASURY CENTER
                CHICAGO, IL 60675""");

        Fabric contentTest = digester.digest(TestUtil.locatePdf(1, 5));
        System.out.println(contentTest);
        Assertions.assertLinesMatch("""
                KAL KAPKA FOODS INC
                33308 TREASURY CENTER
                CHICAGO, IL 60675""".lines(), strategy.process(contentTest).lines());
    }

    @Test
    @Disabled
    void kknvvTest() {
        PDFDigester digester = new PDFDigesterAsposePDFImpl();

        Fabric contentTrain = digester.digest(TestUtil.locatePdf(1, 1));
        var strategy = KKNVVStrategy.apply(contentTrain, "Invoice Number", "DM1329492VR");

        Fabric contentTest = digester.digest(TestUtil.locatePdf(1, 5));
        Assertions.assertEquals("DM54800008587RL", strategy.process(contentTest));
    }

}
