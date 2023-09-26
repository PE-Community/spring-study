package physical.education.yehjung.auth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    // 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration"+userRequest.getClientRegistration());// registrationid로 어떤 auth로 로그인 했는지 확인
        System.out.println("getAccessToken"+userRequest.getAccessToken());
        // 구글로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(Oauth-client라이브 러리) -> accessToken 요청
        // 여기까지가 userRequest의 정보 -> 회원 프로필을 받아야함(loadUser 함수) -> 구글로 부터 회원 프로필 받음
        System.out.println("getAttributes"+super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        return super.loadUser(userRequest);
    }
}
