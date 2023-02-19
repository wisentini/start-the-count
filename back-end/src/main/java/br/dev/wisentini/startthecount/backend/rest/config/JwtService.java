package br.dev.wisentini.startthecount.backend.rest.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "77397A24432646294A404E635266556A586E327235753878214125442A472D4B6150645367566B597033733676397924423F4528482B4D6251655468576D5A71";

    public String extractUsername(String jwt) {
        return this.extractClaim(jwt, Claims::getSubject);
    }

    private Date extractExpiration(String jwt) {
        return this.extractClaim(jwt, Claims::getExpiration);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts
            .parserBuilder()
            .setSigningKey(this.getSigningKey())
            .build()
            .parseClaimsJws(jwt)
            .getBody();
    }

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(this.extractAllClaims(jwt));
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() * 60 * 24))
            .signWith(this.getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    public boolean isJwtExpired(String jwt) {
        return this.extractExpiration(jwt).before(new Date(System.currentTimeMillis()));
    }

    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        return this.extractUsername(jwt).equals(userDetails.getUsername()) && !this.isJwtExpired(jwt);
    }
}
