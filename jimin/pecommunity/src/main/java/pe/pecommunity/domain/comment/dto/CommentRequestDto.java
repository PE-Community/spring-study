package pe.pecommunity.domain.comment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {

    @NotBlank(message = "본문은 필수 값입니다.")
    @Size(max = 1000, message = "본문은 1000자이내여야 합니다.")
    private String content;

    @NotNull(message = "회원 ID는 필수 값입니다.")
    private Long memberId;

    private Long parentId;

    private Boolean isSecret;

    @Builder
    public CommentRequestDto(String content, Long memberId, Long parentId, Boolean isSecret) {
        this.content = content;
        this.memberId = memberId;
        this.parentId = parentId;
        this.isSecret = isSecret;
    }
}
