package woowhahan.dgswchat.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.events.EventException;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.domain.user.service.UserService;
import woowhahan.dgswchat.global.error.CustomError;
import woowhahan.dgswchat.global.error.ErrorCode;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${product.jwt-secret-key}")
    private String secretKey;

    private final UserService userService;

    private static final Long accessTokenExpiredDate = 1000L * 3600 * 3;  //3시간
    private static final Long refreshTokenExpiredDate = 1000L * 3600 * 6;  //6시간

    private Key getSignKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(String nickname, Long expDate, TokenType type) {

        Claims claims = Jwts.claims();
        claims.put("type", type);
        claims.put("nickname", nickname);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expDate))
                .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException, IllegalArgumentException, UnsupportedJwtException, MalformedJwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw CustomError.of(ErrorCode.TOKEN_EXPIRED);
        } catch (IllegalArgumentException e) {
            throw CustomError.of(ErrorCode.TOKEN_NOT_PROVIDED);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            throw CustomError.of(ErrorCode.INVALID_TOKEN);
        } catch (EventException e) {
            throw e;
        }
    }

    public TokenType checkTokenType(String token) {
        if ("REFRESH_TOKEN".equals(extractAllClaims(token).get("type"))) {
            return TokenType.REFRESH_TOKEN;
        } else {
            return TokenType.ACCESS_TOKEN;
        }
    }

    public User getUserByToken(String token) {
        return userService.getUserByNickname(extractAllClaims(token).get("nickname").toString());
    }

    public String generateAccessToken(String nickname) {
        return generateJwtToken(nickname, accessTokenExpiredDate, TokenType.ACCESS_TOKEN);
    }

    public String generateRefreshToken(String nickname) {
        return generateJwtToken(nickname, refreshTokenExpiredDate, TokenType.REFRESH_TOKEN);
    }

}