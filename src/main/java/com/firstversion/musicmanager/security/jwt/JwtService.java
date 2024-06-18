package com.firstversion.musicmanager.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${security.jwt.secrect}")
    private String jwtSecrect;

    @Value("${security.jwt.expirationTime}")
    @Getter
    private long jwtExpirationTime;

    @Value("${security.jwt.refresh-token.re-expiration}")
    @Getter
    private long refreshExpirationTime;


    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecrect));
    }

    public String createToken(Map<String, Object> extraClaims, UserDetails userDetails, long expirationTime) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails, jwtExpirationTime);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails, refreshExpirationTime);
    }

    // how to use: extractAllClaims(token).get("email")
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Expired token: {}", e.getMessage());
        } catch (SignatureException | MalformedJwtException e) {
            logger.error("Invalid jwt token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("This token is not supported : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("No claims found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("error: {}", e.getMessage());
        }
        return false;
    }


}
