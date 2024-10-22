package in.mcxiv14.pdfDb.regexGen.controllers;


import in.mcxiv.tryCatchSuite.Try;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import in.mcxiv14.pdfDb.odb.util.Resources;
import in.mcxiv14.pdfDb.regexGen.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/regexeval")
public class RegExEvalController {

    @Autowired
    PDFDigester digester;

    @GetMapping
    public  String evaluateRegExUserInterface() {
        return Resources.readResource(getClass(), "regexeval.html");
    }

    @PostMapping
    public String evaluateStrategy(@RequestBody MultipartFile file, @RequestParam("strategy") String strategySerialized) {
        var space = digester.digest(Try.get(file::getBytes));
        var strategy = Strategy.StrategyProvider.defaultDeserialize(strategySerialized);
        return strategy.process(space);
    }

}
