package in.mcxiv14.pdfDb.pdf;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;
import in.mcxiv14.pdfDb.odb.objects.Fabric;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import in.mcxiv14.pdfDb.odb.util.Nuke;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

//@Component
public class PDFDigesterSnowTideImpl implements PDFDigester {

    private final Supplier<Object> license = Nuke.nuke(() -> PDF.loadLicense(getClass().getClassLoader().getResource("snowtide.license")));

    @Override
    public Fabric digest(String pdfFile) {
        license.get();
        return Fabric.fabricateC(() -> PDF.open(pdfFile))
                .apply(this::digest);
    }

    @Override
    public Fabric digest(byte[] pdfData) {
        license.get();
        return Fabric.fabricateC(() -> PDF.open(ByteBuffer.wrap(pdfData), ""))
                .apply(this::digest);
    }

    private Fabric digest(Document document) {
        System.out.println("HALLA");
        var builder = new StringBuilder();
        var target = new OutputTarget(builder);
        document.pipe(target);
        return new Fabric(builder.toString());
    }

}
