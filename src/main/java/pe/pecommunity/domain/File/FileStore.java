package pe.pecommunity.domain.File;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pe.pecommunity.domain.File.domain.UploadFile;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); // image.png
        String storeFileName = createStoreFileName(originalFilename); // 서버에 저장하는 파일명
        long fileSize = multipartFile.getSize(); // 파일 크기
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName, fileSize);
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);  // 파일 확장자
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
