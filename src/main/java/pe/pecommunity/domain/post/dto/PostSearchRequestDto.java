package pe.pecommunity.domain.post.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSearchRequestDto {

    private String title;
    private String content;
    private String memberId;

}
