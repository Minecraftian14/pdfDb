package in.mcxiv14.pdfDb.regexVal.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/regval/library")
public class LibraryControllerVAL {
    @GetMapping("/checkAlive")
    public String get() {
        return "Hello regval Library";
    }
}