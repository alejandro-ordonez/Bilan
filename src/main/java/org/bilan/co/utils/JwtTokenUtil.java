package org.bilan.co.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bilan.co.domain.dtos.user.AuthDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final String DOCUMENT = "Document";
    public static final String DOCUMENT_TYPE = "DocumentType";
    public static final String USER_TYPE = "UserType";

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("jwt.secret")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(AuthDto userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(DOCUMENT_TYPE, userDetails.getDocumentType());
        claims.put(USER_TYPE , userDetails.getUserType());
        claims.put(DOCUMENT, userDetails.getDocument());
        return doGenerateToken(claims,
                userDetails.getDocument());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public AuthenticatedUserDto getInfoFromToken(String token){
        if(token.startsWith("Bearer "))
            token = token.substring(7);
        final Map<String, Object> claims = getAllClaimsFromToken(token);
        DocumentType documentType =  DocumentType.valueOf((String)claims.get(DOCUMENT_TYPE));
        UserType userType =  UserType.valueOf((String)claims.get(USER_TYPE));
        String document = getUsernameFromToken(token);

        return new AuthenticatedUserDto(document, userType, documentType);
    }

    public Boolean validateToken(String token, AuthenticatedUserDto userName) {
        final String username = getUsernameFromToken(token);

        AuthenticatedUserDto infoFromToken = getInfoFromToken(token);

        return (username.equals(userName.getDocument()) &&
                infoFromToken.getDocument().equals(userName.getDocument())&&
                infoFromToken.getDocumentType() == userName.getDocumentType()&&
                infoFromToken.getUserType() == userName.getUserType()&&
                !isTokenExpired(token));
    }
}
