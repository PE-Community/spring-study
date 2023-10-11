package pe.pecommunity.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.pecommunity.domain.member.application.MemberService;
import pe.pecommunity.domain.member.dto.LoginRequestDto;
import pe.pecommunity.domain.member.dto.LoginResponseDto;
import pe.pecommunity.domain.member.dto.SignUpRequestDto;
import pe.pecommunity.global.common.response.ApiResponse;
import pe.pecommunity.global.common.response.ResponseUtils;

@Slf4j
@Tag(name = "Member", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer ";

    private final MemberService memberService;

    @Operation(summary = "회원 가입", description = "회원을 등록하는 api")
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    private ApiResponse<?> signUp(@RequestBody @Valid SignUpRequestDto request) {
        memberService.join(request.toEntity());
        return ResponseUtils.success("회원가입 성공");
    }

    @Operation(summary = "아이디 중복 확인", description = "회원 아이디 사용 가능 여부")
    @Parameter(name = "memberId", description = "회원 아이디 값", example = "testId")
    @GetMapping("/join/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    private ApiResponse<?> checkMemberId(@PathVariable String memberId) {
        memberService.checkMemberId(memberId);
        return ResponseUtils.success("사용가능한 아이디입니다.");
    }

    @Operation(summary = "로그인 요청", description = "회원 로그인 요청하는 api")
    @PostMapping("/login")
    private ResponseEntity<ApiResponse<?>> singIn(@RequestBody @Valid LoginRequestDto request) {
        LoginResponseDto responseDto = memberService.login(request.getMemberId(), request.getPassword());

        // response header 토큰 넣음
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION_HEADER, BEARER_TYPE + responseDto.getAccessToken());

        // 응답 객체에도 토큰 넣음
        ApiResponse<LoginResponseDto> responseBody = ResponseUtils.successAsJson("member", responseDto, "로그인 성공");

        return new ResponseEntity<>(responseBody, httpHeaders, HttpStatus.OK);
    }
}
