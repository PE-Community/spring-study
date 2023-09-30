package pe.pecommunity.domain.File.application;

import static pe.pecommunity.global.error.ErrorCode.POST_NOT_EXIST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.pecommunity.domain.File.FileStore;
import pe.pecommunity.domain.File.dao.FileRepository;
import pe.pecommunity.domain.File.domain.File;
import pe.pecommunity.domain.File.domain.UploadFile;
import pe.pecommunity.domain.post.dao.PostRepository;
import pe.pecommunity.domain.post.domain.Post;
import pe.pecommunity.global.error.exception.BaseException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {
    private final PostRepository postRepository;
    private final FileRepository fileRepository;
    private final FileStore fileStore;

    @Transactional
    public void saveFiles(Long postId, List<MultipartFile> requestFiles) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new BaseException(POST_NOT_EXIST));
        List<UploadFile> uploadFiles = fileStore.storeFiles(requestFiles);

        List<File> fileList = new ArrayList<>();
        for (UploadFile uploadFile : uploadFiles) {
            File file = File.createFileBuilder()
                    .post(post)
                    .uploadFileName(uploadFile.getUploadFileName())
                    .storeFileName(uploadFile.getStoreFileName())
                    .fileSize(uploadFile.getFileSize())
                    .build();
            fileList.add(file);
        }
        fileRepository.saveAll(fileList);
    }


}
