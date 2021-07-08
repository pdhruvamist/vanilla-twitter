package com.dhruva.test.plain.twitter.utils;

import com.dhruva.test.plain.twitter.model.User;
import io.jsonwebtoken.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.util.Date;


@Slf4j
@UtilityClass
public class JwtTokenUtils {

    private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private final String jwtIssuer = "dhruva.test";


    public static String generateJwtToken(Authentication authentication) {
        User loginUserDetails = (User) authentication.getPrincipal();

        return Jwts.builder()
                   .setSubject(loginUserDetails.getUsername())
                   .setIssuer(jwtIssuer)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hr
                   .signWith(SignatureAlgorithm.HS512, jwtSecret)
                   .compact();
    }


    public static String getUserIdFromJwtToken(String token) {
        return Jwts.parser()
                   .setSigningKey(jwtSecret)
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    public static boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
