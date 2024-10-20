package in.mcxiv14.pdfDb.pdf;

import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import static in.mcxiv14.pdfDb.odb.util.Nuke.nuke;

//@Component
public class PDFDigesterPDFBoxImpl implements PDFDigester {

    private final Supplier<PDFTextStripper> stripper = nuke(() -> {
        var x = new PDFTextStripper();
        x.setSortByPosition(true);
        x.setAddMoreFormatting(true);
        return x;
    });

    @Override
    public Fabric digest(String pdfFile) {
        return Fabric.fabricateAC(() -> Loader.loadPDF(new File(pdfFile)))
                .apply(this::digest);
    }

    @Override
    public Fabric digest(byte[] pdfData) {
        return Fabric.fabricateAC(() -> Loader.loadPDF(pdfData))
                .apply(this::digest);
    }

    public Fabric digest(PDDocument document) throws IOException {
        return new Fabric(stripper.get().getText(document));
    }

}
