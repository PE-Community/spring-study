package pe.pecommunity.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.pecommunity.global.config.jwt.CustomJwtFilter;
import pe.pecommunity.global.config.jwt.ExceptionHandlerFilter;
import pe.pecommunity.global.config.jwt.JwtAccessDeniedHandler;
import pe.pecommunity.global.config.jwt.JwtAuthenticationEntryPoint;
import pe.pecommunity.global.config.jwt.TokenProvider;
import pe.pecommunity.global.config.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 해시 함수를 이용하여 패스워드를 암호화하는 구현체
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/css/**", "/js/**"); // 인증을 무시할 경로 설정
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * Form 로그인
         */
//        http.authorizeRequests()
//            .antMatchers("/", "/member/**", "/members/new", "/members/login", "/resources/**").permitAll()
//            .antMatchers("/login-test").hasRole(Role.ADMIN.name())
////            .antMatchers("/loginHome").authenticated()
////            .antMatchers("/members/**").authenticated()
//            .antMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
//        .and()
//            .formLogin()
//            .loginPage("/members/login") // 로그인 페이지
//            .loginProcessingUrl("/members/login") // 로그인 요청 URI 매핑
//            .usernameParameter("loginId")
//            .passwordParameter("password")
//            .defaultSuccessUrl("/loginHome", true) // 로그인 성공 시, 리다이렉트
//            .failureUrl("/");
//
//        http.logout().logoutUrl("/members/logout").logoutSuccessUrl("/"); // 로그아웃
//
//        http.userDetailsService(userDetailsService);


        /**
         * REST API - JWT 추가
         */
        http
            .httpBasic().disable() // 기본설정 미사용
            .csrf().disable() // 토큰 사용으로 csrf 보안이 불필요
            .formLogin().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 실패 핸들링
            .accessDeniedHandler(jwtAccessDeniedHandler) // 인가 실패 핸들링

//        .and()
//            .headers()
//            .frameOptions()
//            .sameOrigin()

        // 세션을 사용 x -> STATELESS로 설정
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // api 경로
        .and()
            .authorizeRequests()
                .antMatchers("/member/join", "/member/login", "/post/search").permitAll()
                .anyRequest().authenticated() // 나머지 경로는 jwt 인증

        // JwtFilter 추가
        .and()
            .addFilterBefore(new CustomJwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new ExceptionHandlerFilter(), CustomJwtFilter.class);

    }
}
