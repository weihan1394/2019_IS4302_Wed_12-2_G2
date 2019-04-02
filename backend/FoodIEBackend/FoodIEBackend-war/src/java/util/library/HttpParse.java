package util.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.payload.ActorUserJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import util.enumeration.CompanyRole;
import util.security.JWTManager;

public class HttpParse {

    private JWTManager jWTManager;

    public ActorUserJWT getActorUserJWTByHttpServletRequest(HttpServletRequest httpRequest) {
        jWTManager = new JWTManager();
        
        Map<String, String> headerMap = new HashMap<>();
        ActorUserJWT actorUserJWT = null;

        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = httpRequest.getHeader(key);
                headerMap.put(key, value);
            }
        }

        if (headerMap.containsKey("bearer")) {
            // check the JWT token
            String JWT = headerMap.get("bearer");
            System.out.println("*** " + JWT);

            try {
                Claims claims = jWTManager.decodeJWT(JWT);

                System.out.println("Expiry: " + claims.getExpiration());
                Map<String, Object> claimsMap = (Map<String, Object>) claims;
                // get body payload
                ObjectMapper objectMapper = new ObjectMapper();

                Object LoggedInUserObject = claimsMap.get("LoggedInUser");
                LinkedHashMap<String, String> LoggedInUserMap = new LinkedHashMap<>();
                LoggedInUserMap = objectMapper.convertValue(LoggedInUserObject, LoggedInUserMap.getClass());

                // public ActorUserJWT(String email, String firstName, String lastName, CompanyRole role) {
                actorUserJWT = new ActorUserJWT(LoggedInUserMap.get("email"), LoggedInUserMap.get("firstName"), LoggedInUserMap.get("lastName"), CompanyRole.valueOf(LoggedInUserMap.get("role")));
                // System.out.println(actorUserJWT.getEmail() + "-" + actorUserJWT.getFirstName(Ï) + "-" + actorUserJWT.getLastName() + "-" + actorUserJWT.getRole());
            } catch (ExpiredJwtException | SignatureException exception) {
                System.err.println(exception.getMessage());
            }
        }
        
        return actorUserJWT;
    }
    
    public String getJWTByHttpServletRequest(HttpServletRequest httpRequest) {
        String jwt = "";
        jWTManager = new JWTManager();
        
        Map<String, String> headerMap = new HashMap<>();

        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = httpRequest.getHeader(key);
                headerMap.put(key, value);
                if (key.equals("bearer")) {
                    jwt = value;
                    break;
                }
            }
        }
        
        System.out.println(jwt);
        return jwt;
    }
}
