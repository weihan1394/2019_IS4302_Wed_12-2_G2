package util.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import ejb.session.stateless.ActorUserControllerLocal;
import ejb.session.stateless.LoggedInUserRecordEntityControllerLocal;
import ejb.session.stateless.LoggedInUserRecordTransactionEntityControllerLocal;
import entity.ActorUserEntity;
import entity.LoggedInUserRecordEntity;
import entity.LoggedInUserRecordTransactionEntity;
import entity.payload.ActorUserJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import util.enumeration.CompanyRole;
import util.enumeration.Transaction;
import util.exception.UserActorNotFoundException;
import util.inject.LookupController;
import util.security.HashHelper;
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

        // check if the api put bearer at header
        if (headerMap.containsKey("authorization")) {
            // check the JWT token
            String JWT = headerMap.get("authorization");
            String[] splitJWT = JWT.split(" ");
            JWT = splitJWT[1];
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
                if (key.equalsIgnoreCase("Authorization")) {
                    jwt = value;
                    String [] splitValue = jwt.split(" ");
                    jwt = splitValue[1];
                    break;
                }
            }
        }

        System.out.println(jwt);
        return jwt;
    }

    private ActorUserJWT getHeaderActorUserJWTObject(HttpServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        ActorUserJWT actorUserJWT = getActorUserJWTByHttpServletRequest(httpRequest);

        return actorUserJWT;
    }

    // get jwt back
    private String getHeaderJWTToken(HttpServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return getJWTByHttpServletRequest(httpRequest);
    }

    public List<Object> getReturnObject(HttpServletRequest request, String jsonStr, Transaction transaction) {
        List<Object> lsReturn = new ArrayList<>();
        LookupController lookupController = new LookupController();
        ActorUserControllerLocal actorUserControllerLocal = lookupController.lookupActorUserControllerLocal();
        LoggedInUserRecordEntityControllerLocal loggedInUserRecordEntityControllerLocal = lookupController.lookupLoggedInUserRecordEntityControllerLocal();
        LoggedInUserRecordTransactionEntityControllerLocal loggedInUserRecordTransactionEntityControllerLocal = lookupController.lookupLoggedInUserRecordTransactionEntityControllerLocal();

        String companyId = null;
        
        try {
            // Pass the HttpServletRequest to util.httpParse.getActorUserJWTByHttpServletRequest to parse to get back the actorUserJWT
            ActorUserJWT actorUserJWT = getHeaderActorUserJWTObject(request);
            System.out.println(actorUserJWT.getEmail() + "-" + actorUserJWT.getFirstName() + "-" + actorUserJWT.getLastName() + "-" + actorUserJWT.getRole());

            ActorUserEntity actorUserEntity = actorUserControllerLocal.retrieveActorUserByEmail(actorUserJWT.getEmail());
            System.out.println(actorUserEntity.getCompany().getEmail());
            companyId = actorUserEntity.getCompany().getEmail();

            // store user transaction
            String hashedTransaction = HashHelper.hashString(jsonStr);
            //     public LoggedInUserRecordTransactionEntity(String JWTToken, Transaction transactionJob, String hashedTransaction, LoggedInUserRecordEntity loggedInUserRecordEntity) {
            String JWTToken = getHeaderJWTToken(request);
            System.out.println("2----" + JWTToken);
            LoggedInUserRecordEntity loggedInUserRecordEntity = loggedInUserRecordEntityControllerLocal.retrieveLoggedInUserByJWT(JWTToken);
            LoggedInUserRecordTransactionEntity loggedInUserRecordTransactionEntity = new LoggedInUserRecordTransactionEntity(JWTToken, transaction, hashedTransaction, loggedInUserRecordEntity);
            loggedInUserRecordTransactionEntity = loggedInUserRecordTransactionEntityControllerLocal.createNewLoggedInUserRecordTransactionEntity(loggedInUserRecordTransactionEntity);
            System.out.println("hash: " + hashedTransaction);
            lsReturn.add(loggedInUserRecordTransactionEntity);
            lsReturn.add(companyId);
            lsReturn.add(hashedTransaction);
        } catch (NoSuchAlgorithmException | UserActorNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        
        return lsReturn;
    }
    
    public String getCompanyId(HttpServletRequest request) {
        
        LookupController lookupController = new LookupController();
        ActorUserControllerLocal actorUserControllerLocal = lookupController.lookupActorUserControllerLocal();
        
        String companyId = "";
        
        try {
            // Pass the HttpServletRequest to util.httpParse.getActorUserJWTByHttpServletRequest to parse to get back the actorUserJWT
            ActorUserJWT actorUserJWT = getHeaderActorUserJWTObject(request);
            System.out.println(actorUserJWT.getEmail() + "-" + actorUserJWT.getFirstName() + "-" + actorUserJWT.getLastName() + "-" + actorUserJWT.getRole());

            ActorUserEntity actorUserEntity = actorUserControllerLocal.retrieveActorUserByEmail(actorUserJWT.getEmail());
            System.out.println(actorUserEntity.getCompany().getEmail());
            companyId = actorUserEntity.getCompany().getEmail();
        } catch (UserActorNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        
        return companyId;
    } 
}
