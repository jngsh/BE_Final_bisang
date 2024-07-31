package com.exam.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;


// token 생성하는 기능
@Component  // @Service
public class JwtTokenService {
	  
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtTokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    //Spring Security에서 인증 결과인 Authentication 이용해서 token을 생성하는 메서드
    public String generateToken(Authentication authentication) {

        var scope = authentication
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                        .issuer("self")
                        .issuedAt(Instant.now())
                        .expiresAt(Instant.now().plus(90, ChronoUnit.MINUTES)) // 90분 이후 만기
                        .subject(authentication.getName())
                        .claim("scope", scope)
                        .build();

        return this.jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
    
 // 토큰 유효성 검사 
    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
 // 토큰에서 인증 정보 반환
    public Authentication getAuthentication(String token) {
        var jwt = jwtDecoder.decode(token);
        var authorities = jwt.getClaimAsStringList("scope")
                             .stream()
                             .map(SimpleGrantedAuthority::new)
                             .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, authorities);
    }
}
