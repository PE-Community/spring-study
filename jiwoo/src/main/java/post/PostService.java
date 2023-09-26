package post;

import member.Member;

public interface PostService {

    void enrollPost(ValidatePostRequestDto validatePostRequestDto);

    String deletePost(long post_pk, Member member);

    void updatePost(long post_pk, ValidatePostRequestDto validatePostRequestDto, Member member);
}
