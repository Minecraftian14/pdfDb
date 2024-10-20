package in.mcxiv14.pdfDb.odb.objects.comm;

import lombok.Data;

@Data
public class SimpleResponse {
    int status;
    String message;

    public void success() {
        setStatus(1);
        setMessage("Successful");
    }

    public void fail() {
        setStatus(-1);
        setMessage("Failure");
    }
}
