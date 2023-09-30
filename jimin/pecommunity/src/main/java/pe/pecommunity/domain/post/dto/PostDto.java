package pe.pecommunity.domain.post.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.pecommunity.domain.post.domain.Post;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostDto {
    private Long id;
    private Long boardId;
    private String boardNm;
    private String memberId;
    private String nickname;
    private String title;
    private String content;
    private Long viewCnt;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static PostDto of(Post post) {
        return PostDto.createPostDtoBuilder()
                .id(post.getId())
                .boardId(post.getBoard().getId())
                .boardNm(post.getBoard().getBoardNm())
                .memberId(post.getMember().getMemberId())
                .nickname(post.getMember().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCnt(post.getViewCnt())
                .createDate(post.getCreateDate())
                .updateDate(post.getUpdateDate())
                .build();
    }

    @Builder(builderMethodName = "createPostDtoBuilder")
    public PostDto(Long id, Long boardId, String boardNm, String memberId, String nickname, String title,
                   String content, Long viewCnt, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.boardId = boardId;
        this.boardNm = boardNm;
        this.memberId = memberId;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.viewCnt = viewCnt;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
