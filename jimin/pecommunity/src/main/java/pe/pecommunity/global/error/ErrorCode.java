package pe.pecommunity.global.error;

public enum ErrorCode {

    /**
     * custom
     */
    // member
    MEMBER_ID_ALREADY_EXIST("이미 존재하는 아이디입니다."),
    NICKNAME_ALREADY_EXIST("이미 존재하는 닉네임입니다."),
    EMAIL_ALREADY_EXIST("이미 존재하는 이메일입니다."),

    MEMBER_ID_NOT_EXIST("존재하지 않는 아이디입니다."),
    NICKNAME_NOT_EXIST("존재하지 않는 닉네임입니다."),
    WRONG_PASSWORD("틀린 비밀번호입니다."),

    MEMBER_NOT_EXIST("존재하지 않는 회원입니다."),

    NOT_AUTHORIZED("해당 권한이 없습니다."),
    NOT_LOGIN("로그인 되어있지 않습니다."),

    //board
    BOARD_NOT_EXIST("존재하지 않는 게시판입니다."),


    //post
    POST_NOT_EXIST("존재하지 않는 게시글입니다."),
    COMMENT_NOT_EXIST("존재하지 않는 댓글입니다."),
    COMMENT_NOT_SAME_POST("부모 댓글과 자식 댓글의 게시글이 일치하지 않습니다."),

    // File
    FILE_NOT_EXIST("존재하지 않는 파일입니다."),



    // jwt token
    TOKEN_EXPIRED_EXCEPTION("만료된 JWT 토큰입니다."),
    TOKEN_UNSUPPORTED_EXCEPTION("지원되지 않는 JWT 토큰입니다."),
    TOKEN_WRONG_EXCEPTION("잘못된 JWT 토큰 서명입니다."),
    TOKEN_INVALID_EXCEPTION("유효하지 않은 토큰입니다."),
    TOKEN_NOT_EXIST("토큰이 존재하지 않습니다."),


    INVALID_TYPE_VALUE("유효하지 않은 데이터 값입니다."),
    NOT_FOUND_ERROR("잘못된 주소 요청입니다."),
    INTERNAL_SERVER_ERROR("서버 에러입니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
