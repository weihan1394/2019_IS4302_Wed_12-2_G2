package util.security;

import entity.ActorUserEntity;
import entity.payload.ActorUserJWT;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date; 
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JWTManager {
    String defaultId = "FoodIE";
    private final String secretKey = "FoodIESecret";
    private final long ttlMillis = 100000;
    private final String issuer = "FoodIE";
    
    // Logger
    private static final Logger LOGGER = Logger.getLogger(JWTManager.class.getName());
    
    //Sample method to construct a JWT
    public String createJWT(ActorUserEntity actorUserEntity, Object o, String transaction) {
        // API: https://github.com/jwtk/jjwt
        

        // JWT signature algorithm
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        // Get current time
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // Sign JWT with secret key
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Build JWT custom claims
        ActorUserJWT actorUserJWT = new ActorUserJWT(actorUserEntity.getEmail(), actorUserEntity.getFirstName(), actorUserEntity.getLastName(), actorUserEntity.getUserRoleEnum());
        
        Map<String,Object> mapClaims = new HashMap<>(); 
        mapClaims.put("LoggedInUser", actorUserJWT);
        
        // Build header
        Header header = Jwts.header();
        header.setType("JWT");
        header.setContentType("JSON");
        
        // Build Final JWT
        JwtBuilder builder = Jwts.builder().setHeader((Map<String, Object>)header)
                                            .setIssuedAt(now)
                                            .setIssuer(issuer)
                                            .setSubject(transaction)
                                            .setClaims(mapClaims)
                                            .signWith(signatureAlgorithm, signingKey);
        
        // Set the expiration
        if (ttlMillis >= 0) {
        long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        LOGGER.log(Level.INFO, "Generated JWT: {0}", builder.compact());
        return builder.compact();
    }
    
}


//Let's set the JWT Claims
//        JwtBuilder builder = Jwts.builder().setId(defaultId)
//                                    .setIssuedAt(now)
//                                    .setSubject(subject)
//                                    .setIssuer(issuer)
//                                    .signWith(signatureAlgorithm, signingKey);
