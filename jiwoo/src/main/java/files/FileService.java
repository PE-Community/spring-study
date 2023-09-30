package files;

import java.util.List;

public interface FileService {
    void enrollFiles(List<UploadFileInfo> uploadFileInfos);

    void deleteFile(long postPk);

    void updateFile(long postPk, List<UploadFileInfo> uploadFileInfos);
}
