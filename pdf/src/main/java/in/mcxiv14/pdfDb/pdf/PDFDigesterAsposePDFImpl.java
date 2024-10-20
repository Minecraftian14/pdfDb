package in.mcxiv14.pdfDb.pdf;

import com.aspose.pdf.Document;
import com.aspose.pdf.TextAbsorber;
import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import org.springframework.stereotype.Component;

@Component
public class PDFDigesterAsposePDFImpl implements PDFDigester {

    private Fabric digest(Document document) {
        var absorber = new TextAbsorber();
        document.getPages().accept(absorber);
        return new Fabric(absorber.getText());
    }

    @Override
    public Fabric digest(String pdfFile) {
        return Fabric.fabricateAC(() -> new Document(pdfFile))
                .apply(this::digest);
    }

    @Override
    public Fabric digest(byte[] pdfData) {
        return Fabric.fabricateAC(() -> new Document(pdfData))
                .apply(this::digest);
    }
}
