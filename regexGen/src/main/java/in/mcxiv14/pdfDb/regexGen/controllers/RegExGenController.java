package in.mcxiv14.pdfDb.regexGen.controllers;

import in.mcxiv.tryCatchSuite.Try;
import in.mcxiv14.pdfDb.odb.objects.comm.GenerateResponse;
import in.mcxiv14.pdfDb.odb.pdf.PDFDigester;
import in.mcxiv14.pdfDb.odb.util.Resources;
import in.mcxiv14.pdfDb.regexGen.SolutionRegress;
import in.mcxiv14.pdfDb.regexGen.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

import static in.mcxiv14.pdfDb.odb.util.MapMapper.unchecked;

@RestController
@RequestMapping("/regexgen")
public class RegExGenController {

    @Autowired
    PDFDigester digester;

    SolutionRegress regressor = new SolutionRegress();

    @GetMapping
    public String uploadPDFUserInterface() {
        return Resources.readResource(getClass(), "regexgen.html");
    }

    @PostMapping(consumes = "multipart/form-data")
    public GenerateResponse generateRegExes(
            @RequestBody MultipartFile file,
            @RequestParam("key") String key,
            @RequestParam("value") String value) {
        var response = new GenerateResponse();
//        var file = /*request.getFile()*/;
        var space = digester.digest(Try.get(file::getBytes));
        Strategy strategy;
        if (/*request.getKey()*/key == null)
            strategy = regressor.regressUsingValue(space, /*request.getValue()*/ value);
        else strategy = regressor.regress(space, /*request.getKey()*/key, /*request.getValue()*/value);
//        if (strategy == null) strategy = regressor.regressUsingGenAI(space, request);
        if (strategy != null) {
            response.setStrategy(strategy.serialize());
            response.success();
        } else response.fail();
        return response;
    }

    //    @PostMapping
    public String generateRegExes(
            @RequestBody MultipartFile[] files,
            @RequestBody String words
    ) {
        var pdfs = Arrays.stream(files)
                .map(unchecked(MultipartFile::getBytes))
                .map(digester::digest)
                .toList();
        System.out.println(pdfs);
        System.out.println(words);
        return "Yo";
    }

}