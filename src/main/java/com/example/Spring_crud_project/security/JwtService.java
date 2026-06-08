package com.example.Spring_crud_project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


/**
 * Service responsible for all JWT operations: token generation, validation
 * and claim extraction.
 *
 * <p>Reads {@code jwt.secret} (Base64-encoded HMAC key) and
 * {@code jwt.expiration} (milliseconds) from application properties</p>
 **/
@Service
public class JwtService {

    /** Base64-encoded HMAC-SHA signing secret, injected from {@code jwt.secret}. */
    @Value("${jwt.secret}")
    private String secretKey;

    /** Token lifetime in milliseconds, injected from {@code jwt.expiration}. */
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // =========================
    // GENERATE TOKEN
    // =========================


    /**
     * Builds and signs a JWT for the given username.
     *
     * @param username the subject to embed in the token
     * @return a compact, signed JWT string
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    // =========================
    // VALIDATE TOKEN
    // =========================

    /**
     * Checks whether a token is valid for the given {@link UserDetails}.
     *
     * <p>A token is considered valid when its subject matches the
     * {@code UserDetails} username and it has not expired.</p>
     *
     * @param token       the JWT to validate
     * @param userDetails the authenticated user to validate against
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // =========================
    // EXTRACT USERNAME
    // =========================

    /**
     * Extracts the {@code sub} (subject / username) claim from a token.
     *
     * @param token the JWT to parse
     * @return the username stored as the subject claim
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // =========================
    // GENERIC CLAIM EXTRACTOR
    // =========================

    /**
     * Extracts an arbitrary claim from a token using the provided resolver function.
     *
     * @param <T>      the type of the claim value
     * @param token    the JWT to parse
     * @param resolver a function that maps the {@link Claims} object to the desired value
     * @return the resolved claim value
     */
    public <T> T extractClaim(String token, Function<Claims,T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // =========================
    // CHECK EXPIRATION
    // =========================
    /**
     * Returns {@code true} if the token's expiration date is in the past.
     *
     * @param token the JWT to check
     * @return {@code true} if expired; {@code false} if still valid
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Returns {@code true} if the token's expiration date is in the past.
     *
     * @param token the JWT to check
     * @return {@code true} if expired; {@code false} if still valid
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // =========================
    // PARSE TOKEN
    // =========================

    /**
     * Parses and verifies the token signature, returning all claims.
     *
     * @param token the JWT to parse
     * @return the {@link Claims} payload
     * @throws io.jsonwebtoken.JwtException if the token is malformed or the signature is invalid
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // =========================
    // SIGNING KEY (MODERN JJWT 0.12+)
    // =========================
    /**
     * Decodes the Base64 secret and constructs an HMAC-SHA {@link SecretKey}.
     *
     * @return the {@link SecretKey} used to sign and verify tokens
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
