package co.id.danafix.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public static String generateTokenJwt(String sub, String aud, Object data) {

        Instant now = Instant.now();
        byte[] secret = Base64.getDecoder().decode("OOx4SktBIqcSQLqj4LAgkjbX436s0ajigVY2RO0Ryto=");

        String jwtToken = Jwts.builder()
                .setSubject(sub)
                .setAudience(aud)
                .claim("user", data)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();

        if (log.isInfoEnabled()) {
            log.debug("{\"generate_token\" : \" "+jwtToken+" \"}");
        }

        return jwtToken;
    }

    public String generateSub() {
        String subject = UUID.randomUUID().toString().substring(5);
        return subject;
    }

}
