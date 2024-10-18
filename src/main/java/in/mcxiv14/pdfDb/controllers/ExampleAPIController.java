package in.mcxiv14.pdfDb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleAPIController {
    @GetMapping("/checkAlive")
    public String get() {
        return "Hello";
    }
}
