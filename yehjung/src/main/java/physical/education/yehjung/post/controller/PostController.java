package physical.education.yehjung.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import physical.education.yehjung.file.service.FileService;
import physical.education.yehjung.post.dto.Post;
import physical.education.yehjung.post.service.PostService;
import physical.education.yehjung.file.dto.FileDto;
import physical.education.yehjung.post.util.MD5Generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @GetMapping("/post")
    public String post(Model model){
        List<Post> postList = postService.getPostList();
        model.addAttribute("postList", postList);
        return "board/list.html";
    }

    @GetMapping("/post/write")
    public String write() {
        return "board/post.html";
    }

    @PostMapping("/post")
    public String write(@RequestParam("file") MultipartFile files, Post post) {
        try {
            String origFilename = files.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FileDto dto = FileDto.builder()
                    .origFilename(origFilename)
                    .filename(filename)
                    .filePath(filePath).build();


            Long fileId = fileService.saveFile(dto);
            post.setFileId(fileId);
            postService.savePost(post);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        FileDto file = fileService.getFile(post.getFileId());
        model.addAttribute("post", post);
        model.addAttribute("file", file);

        return "board/detail.html";
    }

    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "board/edit.html";
    }

    @PutMapping("/post/edit/{id}")
    public String update(Post post) {
        postService.savePost(post);
        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return "redirect:/";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        FileDto fileDto = fileService.getFile(fileId);
        Path path = Paths.get(fileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getOrigFilename() + "\"")
                .body(resource);
    }
}
