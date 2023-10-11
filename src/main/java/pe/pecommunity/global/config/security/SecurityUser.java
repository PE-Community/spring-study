package pe.pecommunity.global.config.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import pe.pecommunity.domain.member.domain.Member;

@Slf4j
@Getter @Setter
public class SecurityUser extends User {

    private Member member;

    public SecurityUser(Member member) {
        super(member.getMemberId(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));

        log.info("SecurityUser member.memberId = {}", member.getMemberId());
        log.info("SecurityUser member.nickname = {}", member.getNickname());
        log.info("SecurityUser member.email = {}", member.getEmail());
        log.info("SecurityUser member.role = {}", member.getRole().toString());

        this.member = member;
    }
}
