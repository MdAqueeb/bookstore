package com.example.bookstore.Service;

// import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
// import java.util.Base64.Decoder;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    private String Secretkey = "";
    private String refershSecretkey = "";

    public JWTService(){
        try{
            KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keygen.generateKey();
            Secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());

            SecretKey refershK = keygen.generateKey();
            refershSecretkey = Base64.getEncoder().encodeToString(refershK.getEncoded());

            
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
    public String generate_Token(String username, String email) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("name", username);
        return Jwts.builder()
            .claims()
            .add(claims)
            .subject(email)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+1000*60*60)) // Increased to 1 hour
            .and()
            .signWith(getKey())
            .compact();
    }

    public String generate_Refersh_Token(String username){
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
            .claims()
            .add(claims)
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+1000*60*60*24)) // 24 hours
            .and()
            .signWith(getrefershKey())
            .compact();
    }
    private SecretKey getKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(Secretkey);
        return Keys.hmacShaKeyFor(KeyBytes);
        // throw new UnsupportedOperationException("Unimplemented method 'getKey'");
    }

    private SecretKey getrefershKey(){
        byte[] KeyBytes = Decoders.BASE64.decode(refershSecretkey);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
    public String extractUserName(String token) {
        // TODO Auto-generated method stub
        return extractClaim(token,Claims::getSubject);
    }
    private <T> T extractClaim(String token,Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        // TODO Auto-generated method stub
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){

        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateRefreshToken(String token, String username) {
        final String userName = extractUserName(token);
        return (userName.equals(username) && !isTokenExpired(token));
    }
}