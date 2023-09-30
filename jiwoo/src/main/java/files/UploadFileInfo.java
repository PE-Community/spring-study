package files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class UploadFileInfo {
    private String uploadFileName;
    private String serverFileName;
    private long fileSize;
}
