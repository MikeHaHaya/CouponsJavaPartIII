package app.core.tests;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;

public class Test {

    public static void main(String[] args) {

        // Creates some variables
        String signatureAlg = SignatureAlgorithm.HS256.getJcaName();
        String encodedSecret = "SupremeSecreteJustForUsS46a1a6d4v1a64fa2sh68jh69";
        Key decodedSecret = new SecretKeySpec(Base64.getDecoder().decode(encodedSecret), signatureAlg);
        HashMap<String, Object> newClaims = new HashMap<>();
        newClaims.put("email", "some.email@email.com");
        Instant now = Instant.now();

        // Creates a token String
        String token = Jwts.builder()
                .setClaims(newClaims)
                .setSubject("1")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.MICROS)))
                .signWith(decodedSecret)
                .compact();

        // Waits a second to make sure the token has expired
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Extract and print to see if java throws an ExpiredException
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedSecret).build();
        System.out.println(jwtParser.parseClaimsJws(token).getBody().getSubject());
    }

}
