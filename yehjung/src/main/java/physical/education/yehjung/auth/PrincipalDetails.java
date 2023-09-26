package physical.education.yehjung.auth;
// 시큐리티가 "/login" 주소요청이 오면 낚아채서 로그인 진행
//로그인 진행이 완료되면 시큐리티가 가지고 있는 session을 만들어 줍니다.(security contextholder)
//오브젝트 => authentication 타입 객체
//authentication 안에 유저 정보가 있어야함
//User 오브젝트 타입 => userDetail 타입 객체


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import physical.education.yehjung.Member.dto.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//security session 에 session 정보를 저장하는데, 거기에 들어갈 수 있는 객체가 authentication
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String,Object> attributes;

    public PrincipalDetails(User user){
        this.user =user;
    }

    public PrincipalDetails(User user, Map<String,Object> attributes){
        this.user =user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    //해당 유저의 권한을 return하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().getAuthority();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로 함
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
