package pe.pecommunity.domain.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

    @NotBlank(message = "제목은 필수 값입니다.")
    @Size(max = 50, message = "제목은 50자이내여야 합니다.")
    private String title;

    @NotBlank(message = "본문은 필수 값입니다.")
    @Size(max = 10000, message = "본문은 10000자이내여야 합니다.")
    private String content;

    @NotNull(message = "회원 ID는 필수 값입니다.")
    private Long memberId;

    @NotNull(message = "게시판 ID는 필수 값입니다.")
    private Long boardId;

    @Builder
    public PostRequestDto(String title, String content, Long memberId, Long boardId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.boardId = boardId;
    }
}
