package in.mcxiv14.pdfDb.odb.objects.comm;

import lombok.Data;

@Data
public class Hint {
    private String key;
    private String value;
    private String typeHint;
    private String lengthHint;
}
