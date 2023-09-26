package physical.education.yehjung.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import physical.education.yehjung.board.repository.BoardRepository;
import physical.education.yehjung.post.dto.Post;
import physical.education.yehjung.post.repository.PostRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class BoardController {

    @Autowired
    BoardRepository boardRepository;


}
