package com.shareeverything.security;

import com.shareeverything.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shareeverything.security.Constants.*;


@Component
public class JwtTokenUtils {

    private Clock clock = DefaultClock.INSTANCE;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_EMAIL, user.getEmail());
        claims.put(CLAIM_ROLE, user.getRoles().stream().map(role -> role.getAuthority()).collect(Collectors.toList()));
        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_TOKEN_VALIDITY);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }


    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String user = getUserId(token);
        if (user == null) {
            return null;
        }
        List<String> roles = getRoles(token);
        List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
        authenticationToken.setDetails(SecurityContextDetails.builder().userid(user).build());
        return authenticationToken;
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRoles(String token) {
        return (List<String>) Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().get(CLAIM_ROLE);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean isTokenExpired(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            final Date expiration = claims.getBody().getExpiration();
            return expiration.before(clock.now());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
