package in.mcxiv14.pdfDb.pdf;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;
import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

import static in.mcxiv14.pdfDb.odb.util.Nuke.nuke;

//@Component
public class PDFDigesterSnowTideImpl implements PDFDigester {

    private final Supplier<Object> license = nuke(() -> PDF.loadLicense(getClass().getClassLoader().getResource("pdfxstream.license")));

    @Override
    public Fabric digest(String pdfFile) {
        license.get();
        return Fabric.fabricateAC(() -> PDF.open(pdfFile))
                .apply(this::digest);
    }

    @Override
    public Fabric digest(byte[] pdfData) {
        license.get();
        return Fabric.fabricateAC(() -> PDF.open(ByteBuffer.wrap(pdfData), ""))
                .apply(this::digest);
    }

    private Fabric digest(Document document) {
        var builder = new StringBuilder();
        var target = new OutputTarget(builder);
        document.pipe(target);
        return new Fabric(builder.toString());
    }

}
