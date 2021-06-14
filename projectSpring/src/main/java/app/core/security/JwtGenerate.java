package app.core.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGenerate {

	private final String signatureAlg = SignatureAlgorithm.HS256.getJcaName();
	private final String encodedSecret = "SupremeSecreteJustForUsS46a1a6d4v1a64fa2sh68jh69";
	private final Key decodedSecret = new SecretKeySpec(Base64.getDecoder().decode(encodedSecret), this.signatureAlg);

	/**
	 * Sets the token user properties and generates using the createToken method.
	 */
	public String generateToken(UserDetails userDetails) {

		HashMap<String, Object> newClaims = new HashMap<>();
		newClaims.put("email", userDetails.getEmail());
		newClaims.put("userType", userDetails.getUserType());
		return createToken(newClaims, userDetails.getId());
	}

	/**
	 * Creates the token using details created with the generateToken method.
	 */
	private String createToken(Map<String, Object> newClaims, String subject) {

		Instant now = Instant.now();
		return Jwts.builder()
				.setClaims(newClaims)
				.setSubject(subject)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(8, ChronoUnit.HOURS)))
				.signWith(this.decodedSecret)
				.compact();
	}

	/**
	 * Returns and extracts all claims coming from the incoming token.
	 */
	public Claims extractAllClaims(String token) throws ExpiredJwtException {

		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecret).build();
		return jwtParser.parseClaimsJws(token).getBody();
	}

	/**
	 *  Returns and extracts the ID (subject) written on the incoming token.
	 */
	public String extractID(String token) {
		return extractAllClaims(token).getSubject();
	}

	/**
	 * Returns and extracts the expiration date from the incoming token.
	 */
	public Date extractExpiration(String token) {
		return (Date) extractAllClaims(token).getExpiration();
	}

	/**
	 * Checks if the token has expired.
	 */
	private boolean isTokenExpired(String token) {

		try {
			extractAllClaims(token);
			return false;
		} catch (ExpiredJwtException e) {
			return true;
		}
	}

	/**
	 * Returns true if the token is indeed valid and there is no mismatch with it's
	 * ID or the expiration date.
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = extractID(token);
		return  (username.equals(userDetails.getEmail()) && !isTokenExpired(token));
	}

}