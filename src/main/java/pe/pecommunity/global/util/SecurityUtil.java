package pe.pecommunity.global.util;

import static pe.pecommunity.global.error.ErrorCode.NOT_AUTHORIZED;
import static pe.pecommunity.global.error.ErrorCode.NOT_LOGIN;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import pe.pecommunity.global.error.exception.BaseException;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {

    /**
     * JwtFilter에서 SecurityContext에 세팅한 유저 정보 리턴
     */
    public static Optional<String> getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String memberId = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            memberId = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            memberId = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(memberId);
    }

    public static void checkAuthorizedMember(String memberId) {
        String loginId = SecurityUtil.getCurrentMemberId().orElseThrow(() -> new BaseException(NOT_LOGIN));
//        log.info("memberId:{}, loginId:{}", memberId, loginId);
        if(!memberId.equals(loginId)) throw new BaseException(NOT_AUTHORIZED);
    }
}