package util.inject;

import ejb.session.stateless.ActorUserControllerLocal;
import ejb.session.stateless.LoggedInUserRecordEntityControllerLocal;
import ejb.session.stateless.LoggedInUserRecordTransactionEntityControllerLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LookupController {
    
    private static final Logger LOGGER = Logger.getLogger(LookupController.class.getName());

    public ActorUserControllerLocal lookupActorUserControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            // weihan: naming convention to inject ejb
            // https://docs.oracle.com/javaee/6/tutorial/doc/gipjf.html
            return (ActorUserControllerLocal) c.lookup("java:global/FoodIEBackend/FoodIEBackend-ejb/ActorUserController!ejb.session.stateless.ActorUserControllerLocal");
        } catch (NamingException namingException) {
            // Clean up the logger
            LOGGER.log(Level.SEVERE, "exception caught", namingException);
            throw new RuntimeException(namingException);
        }
    }
    
    public LoggedInUserRecordEntityControllerLocal lookupLoggedInUserRecordEntityControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            // weihan: naming convention to inject ejb
            // https://docs.oracle.com/javaee/6/tutorial/doc/gipjf.html
            return (LoggedInUserRecordEntityControllerLocal) c.lookup("java:global/FoodIEBackend/FoodIEBackend-ejb/LoggedInUserRecordEntityController!ejb.session.stateless.LoggedInUserRecordEntityControllerLocal");
        } catch (NamingException namingException) {
            // Clean up the logger
            LOGGER.log(Level.SEVERE, "exception caught", namingException);
            throw new RuntimeException(namingException);
        }
    }
    
    public LoggedInUserRecordTransactionEntityControllerLocal lookupLoggedInUserRecordTransactionEntityControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            // weihan: naming convention to inject ejb
            // https://docs.oracle.com/javaee/6/tutorial/doc/gipjf.html
            return (LoggedInUserRecordTransactionEntityControllerLocal) c.lookup("java:global/FoodIEBackend/FoodIEBackend-ejb/LoggedInUserRecordTransactionEntityController!ejb.session.stateless.LoggedInUserRecordTransactionEntityControllerLocal");
        } catch (NamingException namingException) {
            // Clean up the logger
            LOGGER.log(Level.SEVERE, "exception caught", namingException);
            throw new RuntimeException(namingException);
        }
    }
}
