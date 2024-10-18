package in.mcxiv14.pdfDb.json.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/json/library")
public class LibraryControllerJSON {
    @GetMapping("/checkAlive")
    public String get() {
        return "Hello JSON Library";
    }
}