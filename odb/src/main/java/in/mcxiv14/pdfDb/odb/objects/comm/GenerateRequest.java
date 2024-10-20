package in.mcxiv14.pdfDb.odb.objects.comm;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GenerateRequest {
    private MultipartFile file;
    private String key = null;
    private String value;
    private String typeHint = null;
    private String lengthHint = null;
}
