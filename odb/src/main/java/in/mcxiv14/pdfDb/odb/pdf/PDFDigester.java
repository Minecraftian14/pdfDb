package in.mcxiv14.pdfDb.odb.pdf;

import in.mcxiv.tryCatchSuite.DangerousSupplier;
import in.mcxiv14.pdfDb.odb.objects.Fabric;

import java.util.function.Function;
import java.util.function.Supplier;

public interface PDFDigester {

    //    Fabric digest(File pdfFile);

    Fabric digest(String pdfFile);

    Fabric digest(byte[] pdfData);

}
