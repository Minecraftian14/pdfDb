package in.mcxiv14.pdfDb;

public class TestUtil {
    public static String locatePdf(int format, int sample) {
        return "src\\test\\resources\\sample pdfs\\Format %d\\Sample%d.pdf".formatted(format, sample);
    }
}
