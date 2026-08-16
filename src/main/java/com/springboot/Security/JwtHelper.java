package com.springboot.Security;

import com.springboot.Model.User.Users;
import com.springboot.Repository.User.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtHelper {

    private final TokenRepository tokenRepository;
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final String SECRET_KEY = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    //TODO: Retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //TODO:  Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //TODO:  For retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //TODO:  if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //TODO:  Generate token from user
    public String generateToken(Users users,Long employeeId,Long organizationId,Long branchId,Long userTypeId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", users.getId());
        claims.put("employeeId", employeeId);
        claims.put("organizationId", organizationId);
        claims.put("branchId", branchId);
        claims.put("userTypeId", userTypeId);
        claims.put("emailId", users.getEmailId());
        return doGenerateToken(claims,users.getEmailId()
        );
    }

    private String doGenerateToken(Map<String , Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }

    //TODO: validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        Boolean isLogout = tokenRepository.findByToken(token).map(t -> !t.getIsLogOut()).orElse(false);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) && isLogout;
    }
}
