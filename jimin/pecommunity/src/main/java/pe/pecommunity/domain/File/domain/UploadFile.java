package pe.pecommunity.domain.File.domain;

import lombok.Getter;

@Getter
public class UploadFile {
    private String uploadFileName;
    private String storeFileName;
    private long fileSize;

    public UploadFile(String uploadFileName, String storeFileName, long fileSize) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.fileSize = fileSize;
    }
}
