package in.mcxiv14.pdfDb;

import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PdfDbApplicationTests {

	@Autowired
	PDFDigester pdfDigester;

	@Test
	void contextLoads() {
	}

	@Test
	void simpleTestThatRuns() {
		System.out.println("Hello World!");
		Assertions.assertTrue(true);
	}

	@Test
	void testPDFDigester() {
		var text = pdfDigester.digest("src\\test\\resources\\sample pdfs\\Format 1\\Sample1.pdf");
		System.out.println(pdfDigester.getClass().getSimpleName());
		System.out.println(text);
	}

}
