package pe.pecommunity.domain.member.application;

import static pe.pecommunity.global.error.ErrorCode.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.pecommunity.domain.member.dao.MemberRepository;
import pe.pecommunity.domain.member.domain.Member;
import pe.pecommunity.domain.member.dto.LoginResponseDto;
import pe.pecommunity.global.config.jwt.TokenProvider;
import pe.pecommunity.global.error.exception.BaseException;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        checkMemberId(member.getMemberId());

        if(memberRepository.findByNickname(member.getNickname()).isPresent()) {
            throw new BaseException(NICKNAME_ALREADY_EXIST);
        }

        if(memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new BaseException(EMAIL_ALREADY_EXIST);
        }

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.changePasswordBcrypt(encodedPassword); // 비밀번호 암호화

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 로그인
     */
    public LoginResponseDto login(String memberId, String password) {
        Member loginMember = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BaseException(MEMBER_ID_NOT_EXIST));

        if(!passwordEncoder.matches(password, loginMember.getPassword())) {
            throw new BaseException(WRONG_PASSWORD);
        }

        String accessToken =  authorize(memberId, password);

        return LoginResponseDto.builder().accessToken(accessToken).build();
    }

    public String authorize(String memberId, String password) {
        // 1. ID/PW 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 로직 - CustomUserDetailsService에서 재정의한 loadUserByUsername 메서드 호출
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증 정보를 기준으로 jwt access 토큰 생성
        return tokenProvider.createToken(authentication);
    }

    /**
     * 아이디 중복 확인
     */
    public void checkMemberId(String memberId) {
        if(memberRepository.findByMemberId(memberId).isPresent()) {
            throw new BaseException(MEMBER_ID_ALREADY_EXIST);
        }
    }
}
