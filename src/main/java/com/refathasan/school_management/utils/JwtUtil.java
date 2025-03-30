package com.refathasan.school_management.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    public static final String SECRET = "f040612a6235ed5c9380318eecb452541dc956f093241e4629ef7ba987aa43bbba7f477939a803bdad4004a5f5071827f61ebc101d0289354da8c5b51323d942bbeb4482366d5200f395cce704b3dfde3f853ac5a87f35957a854b89bf6b74f5d2012586d154749aa9dd7ed1664613cd8ab6daa0206c12f571adb570b488deb7f7f78d0bd88fdead81bbfdd444d6abd11390d41cdc6a031f74c07ee735b08e43a54f72ac5992ceba1ab1f7a498dd795f58f77ecea8afbb03414d362c1d11a849a3994d77c121780c9db4e647bb8e28eb534c1ffadf035d0da54388ec8338586ff323e70ffbd756186bbab6f30c500adfcf974a0678394647b8955abb48907820";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,username);
    }
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
