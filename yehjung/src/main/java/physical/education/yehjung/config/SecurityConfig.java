package physical.education.yehjung.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import physical.education.yehjung.auth.PrincipalOauth2UserService;

// 1. 코드 받기(인증) 2. 엑세스 토큰(권한)
// 3. 사용자 프로필 정보 받기 4. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
// 추가적인 구성이 필요하면 추가 회원가입 요청
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 된다
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) // secured annotation 활성화, preAuthorize 어노테이션 활성화
public class SecurityConfig {
    // 해당 메서드의 리턴되는 오브젝트를 ioc로 등록해준다
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")// login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        return http.build();


    }
}
