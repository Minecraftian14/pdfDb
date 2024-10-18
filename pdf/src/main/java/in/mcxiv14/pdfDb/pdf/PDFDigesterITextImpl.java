package in.mcxiv14.pdfDb.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;

import java.io.IOException;
import java.util.function.Supplier;

import static in.mcxiv14.pdfDb.odb.util.Nuke.nuke;

//@Component
public class PDFDigesterITextImpl implements PDFDigester {

    private final Supplier<TextExtractionStrategy> strategy = nuke(LocationTextExtractionStrategy::new);

    @Override
    public Fabric digest(String pdfFile) {
        return Fabric.fabricate(() -> new PdfReader(pdfFile))
                .apply(this::digest);
    }

    @Override
    public Fabric digest(byte[] pdfData) {
        return Fabric.fabricate(() -> new PdfReader(pdfData))
                .apply(this::digest);
    }

    private Fabric digest(PdfReader reader) throws IOException {
        try {
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                parser.processContent(i, strategy.get());
                builder.append(System.lineSeparator().repeat(3)).append(strategy.get().getResultantText());
            }
            return new Fabric(builder.toString());
        } finally {
            reader.close();
        }
    }
}
