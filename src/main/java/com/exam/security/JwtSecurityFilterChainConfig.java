package com.exam.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class JwtSecurityFilterChainConfig {

	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
	  
		  log.info("ConfiguringsecurityFilterChain");
	        // https://github.com/spring-projects/spring-security/issues/12310 참조
	        return httpSecurity
	        		.cors(Customizer.withDefaults())
	                .authorizeHttpRequests(auth -> 
	                
	                auth.antMatchers("/**","/auth/**","/hello").permitAll()  // 회원가입 요청 허용.
	                    .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
	        		)
	        		.cors(cors -> cors
	                        .configurationSource(request -> {
	                            CorsConfiguration corsConfig = new CorsConfiguration();
	                            corsConfig.setAllowedOrigins(List.of(
	                            		"http://10.10.10.186:5173", "http://10.10.10.136:5173", "http://10.10.10.206:5173","http://192.168.0.102:5173", "http://10.10.10.221:5173"
	                            		, "http://10.10.10.228:5173", "http://10.10.10.228:5173/orderCompleted","http://peterpet.store.s3-website-ap-northeast-1.amazonaws.com", "https://peterpet.store"));
	                            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	                            corsConfig.setAllowedHeaders(List.of("*"));
	                            corsConfig.setAllowCredentials(true);
	                            return corsConfig;
	                        })
	                    )
	                .authorizeHttpRequests(auth -> auth
	                		.antMatchers("/**","/auth/**","/hello").permitAll()  // 회원가입 요청 허용.s
	                    .antMatchers(HttpMethod.OPTIONS,"/bisang/**").permitAll()
	                    .anyRequest()
	                    .authenticated())
	                .csrf(AbstractHttpConfigurer::disable)
	                .sessionManagement(session -> session.
	                    sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
	                .oauth2ResourceServer(
	                        OAuth2ResourceServerConfigurer::jwt)
	                .httpBasic(
	                        Customizer.withDefaults())
	                .headers(header -> {header.
	                    frameOptions().sameOrigin();})
	                .build();
	    }
	

//		@Bean
//		public WebMvcConfigurer corsConfigurer() {
//			return new WebMvcConfigurer() {
//				@Override
//				public void addCorsMappings(CorsRegistry registry) {
//					registry.addMapping("/**")
//					.allowedOrigins("http://localhost:5173", "http://10.10.10.151:5173","*") //ngrok 설정은 빠져있음
//							.allowedMethods("GET", "POST", "PUT", "DELETE")
//							.allowedHeaders("*");
////							.allowCredentials(true); //이거 true설정하면 "*"사용할 수 없다.
////							.maxAge(3000);
//				}
//			};
//		}
	  //allowedHeaders 예비군 : "X-AUTH-TOKEN","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials","ngrok-skip-browser-warning","Content-Type",
	  
	    @Bean
	    public JWKSource<SecurityContext> jwkSource() {
	        JWKSet jwkSet = new JWKSet(rsaKey());
	        return (((jwkSelector, securityContext) 
	                        -> jwkSelector.select(jwkSet)));
	    }

	    @Bean
	    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
	        return new NimbusJwtEncoder(jwkSource);
	    }

	    @Bean
	    JwtDecoder jwtDecoder() throws JOSEException {
	        return NimbusJwtDecoder
	                .withPublicKey(rsaKey().toRSAPublicKey())
	                .build();
	    }
	    
	    
	    @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    public RSAKey rsaKey() {
	        
	        KeyPair keyPair = keyPair();
	        
	        return new RSAKey
	                .Builder((RSAPublicKey) keyPair.getPublic())
	                .privateKey((RSAPrivateKey) keyPair.getPrivate())
	                .keyID(UUID.randomUUID().toString())
	                .build();
	    }

	    @Bean
	    public KeyPair keyPair() {
	        try {
	            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	            keyPairGenerator.initialize(2048);
	            return keyPairGenerator.generateKeyPair();
	        } catch (Exception e) {
	            throw new IllegalStateException(
	                    "Unable to generate an RSA Key Pair", e);
	        }
	    }






}