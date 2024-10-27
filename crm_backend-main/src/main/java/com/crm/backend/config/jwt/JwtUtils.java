////package com.crm.backend.config.jwt;
////
////import io.jsonwebtoken.*;
////import io.jsonwebtoken.io.Decoders;
////import io.jsonwebtoken.security.Keys;
////import jakarta.servlet.http.HttpServletRequest;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.stereotype.Component;
////
////import javax.crypto.SecretKey;
////import java.security.Key;
////import java.util.Date;
////
////@Component
////public class JwtUtils {
////    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
////
////    @Value("${spring.app.jwtSecret}")
////    private String jwtSecret;
////
////    @Value("${spring.app.jwtExpirationMs}")
////    private int jwtExpirationMs;
////
////    public String getJwtFromHeader(HttpServletRequest request) {
////        String bearerToken = request.getHeader("Authorization");
////        logger.debug("Authorization Header: {}", bearerToken);
////        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
////            return bearerToken.substring(7);
////        }
////        return null;
////    }
////
////    public String generateTokenFromUsername(UserDetails userDetails) {
////        String username = userDetails.getUsername();
////        return Jwts.builder()
////                .subject(username)
////                .issuedAt(new Date())
////                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
////                .signWith(key())
////                .compact();
////    }
////
////    public String getUserNameFromJwtToken(String token) {
////        return Jwts.parser()
////                .verifyWith((SecretKey) key())
////                .build().parseSignedClaims(token)
////                .getPayload().getSubject();
////    }
////
////    private Key key() {
////        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
////    }
////
////    public boolean validateJwtToken(String authToken) {
////        try {
////            System.out.println("Validate");
////            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
////            return true;
////        } catch (MalformedJwtException e) {
////            logger.error("Invalid JWT token: {}", e.getMessage());
////        } catch (ExpiredJwtException e) {
////            logger.error("JWT token is expired: {}", e.getMessage());
////        } catch (UnsupportedJwtException e) {
////            logger.error("JWT token is unsupported: {}", e.getMessage());
////        } catch (IllegalArgumentException e) {
////            logger.error("JWT claims string is empty: {}", e.getMessage());
////        }
////        return false;
////    }
////}
//
//
//
//
//
//
//
//
//
//
//
//// JwtUtil.java
//package com.crm.backend.config.jwt;
//
//import io.jsonwebtoken.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    @Value("${spring.app.jwtSecret}")
//    private String jwtSecret;
//    //private String SECRET_KEY = "your_secret_key";
//
//    public String generateToken(UserDetails userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
//                .signWith(SignatureAlgorithm.HS256, jwtSecret)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    private boolean isTokenExpired(String token) {
//        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration().before(new Date());
//    }
//}
