package pe.pecommunity.global.config.security;

import static pe.pecommunity.global.error.ErrorCode.NICKNAME_NOT_EXIST;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pe.pecommunity.domain.member.dao.MemberRepository;
import pe.pecommunity.domain.member.domain.Member;
import pe.pecommunity.global.error.exception.BaseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new BaseException(NICKNAME_NOT_EXIST));

        log.info("loadUserByUsername member.memberId = {}", username);

        return new SecurityUser(findMember);
    }
}
