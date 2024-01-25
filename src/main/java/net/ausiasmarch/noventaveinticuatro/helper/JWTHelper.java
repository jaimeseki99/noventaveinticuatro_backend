package net.ausiasmarch.noventaveinticuatro.helper;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTHelper {

    private static final String SECRET = "noventaveinticuatro_9024_jsq_12345678900987654321@#%&";
    private static final String ISSUER = "NOVENTAVEINTICUATRO BY JAIME SERRANO";

    private static SecretKey secretKey() {
        return Keys.hmacShaKeyFor((SECRET + ISSUER + SECRET).getBytes());
    }

    public static String generateJWT(String username) {

        Date currentTime = Date.from(Instant.now());
        Date expirationTime = Date.from(Instant.now().plusSeconds(3600));

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer(ISSUER)
                .setIssuedAt(currentTime)
                .setExpiration(expirationTime)
                .claim("name", username)
                .signWith(secretKey())
                .compact();
    }

    public static String validateJWT(String strJWT) {
        try {
            Jws<Claims> headerClaimsJwt = Jwts.parserBuilder()
                    .setSigningKey(secretKey())
                    .build()
                    .parseClaimsJws(strJWT);
            
            Claims claims = headerClaimsJwt.getBody();

            if (claims.getExpiration().before(new Date())) {
                return null;
            }

            if (!claims.getIssuer().equals(ISSUER)) {
                return null;
            }

            return claims.get("name", String.class);
        } catch (Exception e) {
            return null;
        }
    }


    
}
