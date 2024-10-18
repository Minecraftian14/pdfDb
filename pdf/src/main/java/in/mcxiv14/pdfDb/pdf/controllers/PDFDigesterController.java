package in.mcxiv14.pdfDb.pdf.controllers;

import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/digestor")
public class PDFDigesterController {

    @Autowired
    PDFDigester digester;

    @GetMapping("/file")
    public String get(String pdfFile) {
        var file = new File(pdfFile);
        if (!file.exists() || !file.isFile()) return "Not a file on server";
        return "<pre>%s</pre>".formatted(digester.digest(pdfFile).toString());
    }

}