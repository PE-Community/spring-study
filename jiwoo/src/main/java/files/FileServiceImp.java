package files;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService{

    private final FileRepository fileRepository;
    @Override
    public void enrollFiles(List<UploadFileInfo> uploadFileInfos) {
        List<File> fileList = new ArrayList<>();
        for (UploadFileInfo uploadFileInfo : uploadFileInfos) {
            File file = File.builder().server_file_nm(uploadFileInfo.getServerFileName())
                    .upload_file_nm(uploadFileInfo.getUploadFileName())
                    .file_size(uploadFileInfo.getFileSize())
                    .upload_ymd(LocalDateTime.now())
                    .build();
            fileList.add(file);
        }
        fileRepository.saveFile(fileList);
    }

    @Override
    public void deleteFile(long post_pk) {
        fileRepository.deleteFileByPost_fk(post_pk);
    }

    @Override
    public void updateFile(long post_pk, List<UploadFileInfo> uploadFileInfos) {
        List<File> fileList = fileRepository.findFileByPost_fk(post_pk);
        if(uploadFileInfos.isEmpty()) {
            fileRepository.deleteFileByPost_fk(post_pk);
        }
        //파일 수정의 경우 로직은 생각 중...
    }
}
