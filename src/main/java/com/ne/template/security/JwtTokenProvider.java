package com.ne.template.security;

import com.ne.template.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiresIn}")
    private Long expiration;
    private static final String CLAIM_KEY_USER = "user";
    private static final String CLAIM_KEY_ROLES = "roles";


    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        // Extract roles from userPrincipal and convert them to a list of role names
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        System.out.println(userPrincipal.getId());
        try {
            String jwt = Jwts.builder()
                    .setId(userPrincipal.getId() + "")
                    .setSubject(userPrincipal.getId() + "")
                    .claim(CLAIM_KEY_USER, userPrincipal)
                    .claim(CLAIM_KEY_ROLES, roles) // Include roles in the claim
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
            return jwt;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

    }

    public String getUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public JwtUserInfo decodeToken(String token) throws JWTVerificationException {
        Claims claims = extractAllClaims(token);
        String userId = (String) claims.get(CLAIM_KEY_USER);
        String role = (String) claims.get(CLAIM_KEY_ROLES);
        return new JwtUserInfo()
                .setRole(role)
                .setUserId(userId);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token" + ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token" + ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty" + ex);
        }
        return false;
    }

}

