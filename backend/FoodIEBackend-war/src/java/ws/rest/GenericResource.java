package ws.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import ejb.session.stateless.ActorUserControllerLocal;
import entity.ActorUser;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Path("GenericResource")
public class GenericResource {

    @Context
    private UriInfo context;

    private final ActorUserControllerLocal actorUserControllerLocal;
    
    private Gson gson;

    public GenericResource() {
        actorUserControllerLocal = lookupActorUserControllerLocal();
        gson = new Gson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "HelloWorld")
    public String HelloWorld() {
        List<String> lsName = new ArrayList<>();
        lsName.add("test1");
        lsName.add("test2");
        lsName.add("test3");

        String jsonStr = gson.toJson(lsName);
        return jsonStr;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "RetrieveAllActorUser")
    public String RetrieveAllActorUser() {
        List<ActorUser> lsActorUser =  actorUserControllerLocal.retrieveAllActorUser();
        
        String jsonStr = gson.toJson(lsActorUser);
        return jsonStr;
    }
    
    private ActorUserControllerLocal lookupActorUserControllerLocal() {
        try {
            javax.naming.Context context = new InitialContext();
            return (ActorUserControllerLocal)context.lookup("java:global/FoodIEBackend/FoodIEBackend-ejb/ActorUserController!ejb.session.stateless.ActorUserControllerLocal");
        }
        catch (NamingException namingException) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", namingException);
            throw new RuntimeException(namingException);
        }
    }
}
