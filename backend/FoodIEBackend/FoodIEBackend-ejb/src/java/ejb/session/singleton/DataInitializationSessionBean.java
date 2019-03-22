package ejb.session.singleton;

import ejb.session.stateless.ActorUserControllerLocal;
import entity.ActorUserEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.UserRole;
import util.exception.InputDataValidationException;

@Singleton
@LocalBean
@Startup
public class DataInitializationSessionBean {

    @EJB
    private ActorUserControllerLocal actorUserControllerLocal;
    
    public  DataInitializationSessionBean() {
        
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("checking if there is data...");
        try {
            List<ActorUserEntity> lsActorUserEntitys = actorUserControllerLocal.retrieveAllActorUser();
            if ((lsActorUserEntitys == null) || (lsActorUserEntitys.isEmpty())) {
                initializeData();
            }
        } catch (Exception ex) {
            initializeData();
        }
    }
    
    private void initializeData() {
        try {
            actorUserControllerLocal.createNewActorUser(new ActorUserEntity("Lee", "Alice", "alice@test.com", "password", UserRole.FARMER));
            actorUserControllerLocal.createNewActorUser(new ActorUserEntity("Lee", "Bob", "bob@test.com", "password", UserRole.FARMER));
            actorUserControllerLocal.createNewActorUser(new ActorUserEntity("Lee", "Charlie", "charlie@test.com", "password", UserRole.FARMER));
            actorUserControllerLocal.createNewActorUser(new ActorUserEntity("Lee", "Delta", "delta@test.com", "password", UserRole.FARMER));
            
        } catch (InputDataValidationException ex) {
            System.err.println("********** DataInitializationSessionBean.initializeData(): " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("********** DataInitializationSessionBean.initializeData(): An error has occurred while loading initial test data: " + ex.getMessage());
        }
    }
}
