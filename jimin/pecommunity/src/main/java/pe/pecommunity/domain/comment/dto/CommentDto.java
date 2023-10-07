package pe.pecommunity.domain.comment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.pecommunity.domain.comment.domain.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommentDto {
    private Long id;
    private Long parentId;
    private String memberId;
    private String content;
    private int level;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Boolean isRemoved;
    private Boolean isSecret;

    public static CommentDto of(Comment comment) {
        Long parentId = (comment.getParent() == null) ? null : comment.getParent().getId();
        return CommentDto.createCommentDtoBuilder()
                .id(comment.getId())
                .parentId(parentId)
                .memberId(comment.getMember().getMemberId())
                .content(comment.getContent())
                .level(comment.getLevel())
                .createDate(comment.getCreateDate())
                .updateDate(comment.getUpdateDate())
                .isRemoved(comment.getIsRemoved())
                .isSecret(comment.getIsSecret())
                .build();
    }

    @Builder(builderMethodName = "createCommentDtoBuilder")
    public CommentDto(Long id, Long parentId, String memberId, String content, int level,
                      LocalDateTime createDate, LocalDateTime updateDate, Boolean isRemoved, Boolean isSecret) {
        this.id = id;
        this.parentId = parentId;
        this.memberId = memberId;
        this.content = content;
        this.level = level;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.isRemoved = isRemoved;
        this.isSecret = isSecret;
    }

    @Override
    public String toString() {
        return "CommentDto {" +
                "id=" + id +
                ", parentId=" + parentId +
                ", memberId='" + memberId + '\'' +
                ", content='" + content + '\'' +
                ", level=" + level +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", isRemoved=" + isRemoved +
                ", isSecret=" + isSecret +
                '}';
    }
}
