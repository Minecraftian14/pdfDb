package in.mcxiv14.pdfDb;

import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdfDbApplication {

	@Autowired
	PDFDigester pdfDigester;

	public static void main(String[] args) {
		SpringApplication.run(PdfDbApplication.class, args);
	}

}
