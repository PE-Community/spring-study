package board;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import post.PostRepository;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    @GetMapping("/{board_cd_fk}")
    public String checkBoard(@PathVariable long board_cd_fk) {
    }
}
