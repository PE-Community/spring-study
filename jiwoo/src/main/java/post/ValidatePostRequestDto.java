package post;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ValidatePostRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
