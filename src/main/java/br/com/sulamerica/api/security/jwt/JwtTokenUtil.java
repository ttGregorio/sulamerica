package br.com.sulamerica.api.security.jwt;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_CREATED = "created";
	static final String CLAIM_KEY_EXPIRED = "exp";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String getUsernameByToken(String token) {
		String username;

		try {
			final Claims clains = getClainsFromToken(token);
			username = clains.getSubject();
		} catch (Exception e) {
			e.printStackTrace();
			username = null;
		}

		return username;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;

		try {
			final Claims clains = getClainsFromToken(token);
			expiration = clains.getExpiration();
		} catch (Exception e) {
			e.printStackTrace();
			expiration = null;
		}

		return expiration;
	}

	private Claims getClainsFromToken(String token) {
		Claims claims = null;

		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			claims = null;
		}
		return claims;
	}

	private boolean isTokenExpired(String token) {
		final Date dateExpiration = getExpirationDateFromToken(token);
		return dateExpiration.before(new Date());
	}

	public String generateToken(UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		final Date date = new Date();
		claims.put(CLAIM_KEY_CREATED, date);

		return doGenerateToken(claims);

	}

	private String doGenerateToken(Map<String, Object> claims) {
		final Date createDate = (Date) claims.get(CLAIM_KEY_CREATED);
		final Date expirationDate = new Date(createDate.getTime() + expiration * 1000);

		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token));
	}

	public String refreshToken(String token) {
		String refreshedToken;

		final Claims claims = getClainsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date());
		refreshedToken = doGenerateToken(claims);

		return refreshedToken;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		JwtUser user = (JwtUser) userDetails;

		final String username = getUsernameByToken(token);

		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}
}