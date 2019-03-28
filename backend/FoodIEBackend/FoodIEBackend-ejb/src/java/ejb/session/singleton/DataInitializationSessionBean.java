package ejb.session.singleton;

import ejb.session.stateless.ActorUserControllerLocal;
import ejb.session.stateless.LoggedInUserRecordEntityControllerLocal;
import entity.ActorUserEntity;
import entity.LoggedInUserRecordEntity;
import java.util.Iterator;
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
    
    @EJB
    private LoggedInUserRecordEntityControllerLocal loggedInUserRecordEntityControllerLocal;
    
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
        
        addLoggedInUserTransaction();
        List<LoggedInUserRecordEntity> lsLoggedInUserRecordEntitys = loggedInUserRecordEntityControllerLocal.retrieveAllLoggedInUserRecord();
        lsLoggedInUserRecordEntitys.forEach((liure) -> {
            System.out.println(liure.getEmail() + "      timestamp:" + liure.getTimestampDateTime());
        });
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
    
    private void addLoggedInUserTransaction() {
        String token = "mF3gKKV8OA4VYYnU0NC4sXdgowTVsHHU9xVrWhPtyM6bQlUSfSpeXPrOlqLA72CFZBW0emPXEX9Y9wmw7QWLJ5jTDwg4gGg5x8MTeI36xRyc0gm6cAWVCou37jOPVutoOC8lLjd6cG1wti9UiEMUCfELtUyqQXfqMW8EFuPkJZmsGK4w4iI0q4x8dVovEgliBRDox1CaKwDey14wsr8BtLyhzN3qSRUbmqX7Bt8AlNfByK8uXuLIWAWsHPzz7tl4Qa9dvjreWJ6T0tErZllbioapabpRLiW3RnA9smlycUfAMy7ysPoIUGwQbeo5MLPJIbIuqNc3xCSsPFceIdh8IV0DtAxmj9EXu11Xl5CHipHzwYNeUZOXKucvhDL1vhhJbwwpXExV6bsoWERjaMQiVYHxLX0yIfBPd24prJc8bsaHsrJdillOSiCvJsdWHfKWH5sQ9BJok6kxfuES6o8fSNAgN1lZhuyp45PJ1gPIA5PPVKXhv8F3eAHJL7LGzAWlEeBITKM4ajpgbhX434lOSN3bxebhTCi4WeSJaQFxYrXPoyOQ7Tko3u6fsnSJky5FkqYWtjZIAuKSsOUOPVvZaZFtNXa9DIIIamo3J0G1neuHs3CVTKfHEWaoEbtGa5ORUFKjp2pZ0qxEGV6tW9pl4vi3gfOZ5OawvjZ4KgO5oolEzEIbMBnRkIg2RyGKDyl5azh8yxFEkQGRxylRcu7r7kHONlZGZOuXd65g2LB4QLWUtsH1bkyC5lfrE8lCl1XR3jDeCvoioUjbQ7f4i8FPvKyH8RZo39fxaiQosQCUBJ5xGCMTGenYC6NmThpkvec1IOzIzl14KaOduMwj9Q7jsYlHx6MAyjV3mv8PWMEEwvfr96GdeBn1liCYzvwReqAxbRBhy7jShk5RaFaTfyombjV8YPrrnx9kob9GA2BHGTMh978qWuSJVG9McLY4QMcXC4YQo2jLJZvdI4LCzeirggvcPMy4gB1CDoyK0YC8";
        LoggedInUserRecordEntity loggedInUserRecordEntity = new LoggedInUserRecordEntity(token, "email");
        loggedInUserRecordEntity = loggedInUserRecordEntityControllerLocal.createNewLoggedInUserRecord(loggedInUserRecordEntity);
        System.out.println("time: " + loggedInUserRecordEntity.getTimestampDateTime()+ "    email: " + loggedInUserRecordEntity.getEmail());
    }
}
