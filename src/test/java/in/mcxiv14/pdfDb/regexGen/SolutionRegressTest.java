package in.mcxiv14.pdfDb.regexGen;

import in.mcxiv14.pdfDb.TestUtil;
import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import in.mcxiv14.pdfDb.pdf.PDFDigesterAsposePDFImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SolutionRegressTest {

    @Test
    void kvTest() {
        PDFDigester digester = new PDFDigesterAsposePDFImpl();

        Fabric contentTrain = digester.digest(TestUtil.locatePdf(2, 1));
        var strategy = KVStrategy.apply(contentTrain, "Customer VAT No", "GB222280111");

        Fabric contentTest = digester.digest(TestUtil.locatePdf(2, 5));
        Assertions.assertEquals("IE2732243D" ,strategy.process(contentTest));
    }

    @Test
    @Disabled
    void knvTest() {
        PDFDigester digester = new PDFDigesterAsposePDFImpl();

        Fabric contentTrain = digester.digest(TestUtil.locatePdf(1, 1));
        var strategy = KNVStrategy.apply(contentTrain, "Invoice Number", "DM1329492VR");

        Fabric contentTest = digester.digest(TestUtil.locatePdf(1, 5));
        Assertions.assertEquals("DM54800008587RL" ,strategy.process(contentTest));
    }

    @Test
    void kknvvTest() {
        PDFDigester digester = new PDFDigesterAsposePDFImpl();

        Fabric contentTrain = digester.digest(TestUtil.locatePdf(1, 1));
        var strategy = KKNVVStrategy.apply(contentTrain, "Invoice Number", "DM1329492VR");

        Fabric contentTest = digester.digest(TestUtil.locatePdf(1, 5));
        Assertions.assertEquals("DM54800008587RL" ,strategy.process(contentTest));
    }

}
