package pe.pecommunity.global.config.jwt;

import static pe.pecommunity.global.error.ErrorCode.TOKEN_EXPIRED_EXCEPTION;
import static pe.pecommunity.global.error.ErrorCode.TOKEN_INVALID_EXCEPTION;
import static pe.pecommunity.global.error.ErrorCode.TOKEN_UNSUPPORTED_EXCEPTION;
import static pe.pecommunity.global.error.ErrorCode.TOKEN_WRONG_EXCEPTION;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean { // 토큰 생성 및 검증

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer ";
    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long tokenValidityInMilliseconds;

    private Key key;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // key Base64로 인코딩
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 헤더에서 토큰 정보 추출
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * 토큰 생성 - 로그인 요청 시 반환
     */
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 토큰에 저장한 유저 식별값(memberId)을 추출
     * 토큰을 받아 Claims을 만들고 권한정보로 시큐리티 유저객체를 만들어 Authentication 객체 반환
     */
    public Authentication getAuthentication(String token) {
        // 토큰 복호화
        Claims claims = parseClaims(token);

        // 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private Claims parseClaims(String accessToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    /**
     * 토큰 유효성 검사
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JwtException(TOKEN_WRONG_EXCEPTION.getMessage());
        } catch (ExpiredJwtException e) {
            throw new JwtException(TOKEN_EXPIRED_EXCEPTION.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new JwtException(TOKEN_UNSUPPORTED_EXCEPTION.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JwtException(TOKEN_INVALID_EXCEPTION.getMessage());
        }
    }

}
