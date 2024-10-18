package in.mcxiv14.pdfDb.odb.objects.comm;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Data
public class GenerateRequest {
    private List<MultipartFile> files;
    private Map<String, Hint> hints;
}
