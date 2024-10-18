package in.mcxiv14.pdfDb.impl.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/odb/library")
public class LibraryControllerIMPL {
    @GetMapping("/checkAlive")
    public String get() {
        return "Hello ODB Lib";
    }
}