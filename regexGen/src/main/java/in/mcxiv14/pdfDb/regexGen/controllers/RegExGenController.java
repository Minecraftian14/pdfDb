package in.mcxiv14.pdfDb.regexGen.controllers;

import in.mcxiv14.pdfDb.odb.objects.comm.GenerateRequest;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import in.mcxiv14.pdfDb.odb.util.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

import static in.mcxiv14.pdfDb.odb.util.MapMapper.map;

@RestController
@RequestMapping("/regexgen")
public class RegExGenController {

    @Autowired
    PDFDigester digester;

    @GetMapping
    public String uploadPDFUserInterface() {
        return Resources.readResource(getClass(), "generate.html");
    }

    @PostMapping("/form")
    public void getForm(@RequestBody GenerateRequest generateRequest) {


    }

    @PostMapping
    public String generateRegExes(
            @RequestBody MultipartFile[] files,
            @RequestBody String words
    ) {
        var pdfs = Arrays.stream(files)
                .map(map(MultipartFile::getBytes))
                .map(digester::digest)
                .toList();
        System.out.println(pdfs);
        System.out.println(words);
        return "Yo";
    }

}