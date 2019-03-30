package ejb.session.singleton;

import ejb.session.stateless.ActorUserControllerLocal;
import ejb.session.stateless.CompanyEntityControllerLocal;
import ejb.session.stateless.LoggedInUserRecordEntityControllerLocal;
import entity.ActorUserEntity;
import entity.CompanyEntity;
import entity.LoggedInUserRecordEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.CompanyRole;
import util.exception.InputDataValidationException;

@Singleton
@LocalBean
@Startup
public class DataInitializationSessionBean {

    @EJB
    private ActorUserControllerLocal actorUserControllerLocal;

    @EJB
    private LoggedInUserRecordEntityControllerLocal loggedInUserRecordEntityControllerLocal;

    @EJB
    private CompanyEntityControllerLocal companyEntityControllerLocal;

    public DataInitializationSessionBean() {

    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("checking if there is data...");
        List<ActorUserEntity> lsActorUserEntitys = actorUserControllerLocal.retrieveAllActorUser();
        if ((lsActorUserEntitys == null) || (lsActorUserEntitys.isEmpty())) {
            initializeUserData();
        }
        
        List<LoggedInUserRecordEntity> lsLoggedInUserRecordEntitys = loggedInUserRecordEntityControllerLocal.retrieveAllLoggedInUserRecord();
        if ((lsLoggedInUserRecordEntitys == null) || (lsLoggedInUserRecordEntitys.isEmpty())) {
            initializeLoggedInUserTransaction();
        }
    }

    private void initializeUserData() {
        String firstName1 = "Alice";
        String firstName2 = "Bob";
        String firstName3 = "Charlie";

        String lastName1 = "Aw";
        String lastName2 = "Lee";
        String lastName3 = "Wong";

        String password = "password";

        // create company
        CompanyEntity farmerCompany = new CompanyEntity("Koh Fah Technology Farm", "KohFT@foodie.com", CompanyRole.FARMER);
        farmerCompany = companyEntityControllerLocal.createCompany(farmerCompany);
        CompanyEntity producerCompany = new CompanyEntity("Lin Food Supply", "LinFS@foodie.com", CompanyRole.PRODUCER);
        producerCompany = companyEntityControllerLocal.createCompany(producerCompany);
        CompanyEntity distributorCompany = new CompanyEntity("Walson Food Distributor Pte Ltd", "Walson@foodie.com", CompanyRole.FARMER);
        distributorCompany = companyEntityControllerLocal.createCompany(distributorCompany);
        CompanyEntity retailerCompany = new CompanyEntity("NTUC FairPrice", "Ntuc@foodie.com", CompanyRole.RETAILER);
        retailerCompany = companyEntityControllerLocal.createCompany(retailerCompany);

        try {
            // Farmer (Koh Fah Technology Farm)
            // String farmerCompany = "Koh Fah Technology Farm";
            List<ActorUserEntity> lsFarmer = new ArrayList<>();
            lsFarmer.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName1, lastName1, firstName1 + "_KohFT@foodie.com", password, CompanyRole.FARMER, farmerCompany)));
            lsFarmer.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName2, lastName2, firstName2 + "_KohFT@foodie.com", password, CompanyRole.FARMER, farmerCompany)));
            lsFarmer.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName3, lastName3, firstName3 + "_KohFT@foodie.com", password, CompanyRole.FARMER, farmerCompany)));
            farmerCompany.setActorUserEntities(lsFarmer);

            // Producer (Lin Food Supply)
            // String producerCompany = "Lin Food Supply";
            List<ActorUserEntity> lsProducer = new ArrayList<>();
            lsProducer.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName1, lastName1, firstName1 + "_LinFS@foodie.com", password, CompanyRole.PRODUCER, producerCompany)));
            lsProducer.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName2, lastName2, firstName2 + "_LinFS@foodie.com", password, CompanyRole.PRODUCER, producerCompany)));
            lsProducer.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName3, lastName3, firstName3 + "_LinFS@foodie.com", password, CompanyRole.PRODUCER, producerCompany)));
            producerCompany.setActorUserEntities(lsProducer);

            // Distributor (Walson Food Distributor Pte Ltd)
            // String distributorCompany = "Walson Food Distributor Pte Ltd";
            List<ActorUserEntity> lsDistributor = new ArrayList<>();
            lsDistributor.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName1, lastName1, firstName1 + "_Walson@foodie.com", password, CompanyRole.DISTRIBUTOR, distributorCompany)));
            lsDistributor.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName2, lastName2, firstName2 + "_Walson@foodie.com", password, CompanyRole.DISTRIBUTOR, distributorCompany)));
            lsDistributor.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName3, lastName3, firstName3 + "_Walson@foodie.com", password, CompanyRole.DISTRIBUTOR, distributorCompany)));
            distributorCompany.setActorUserEntities(lsDistributor);

            // Retailer (NTUC FairPrice)
            List<ActorUserEntity> lsRetailer = new ArrayList<>();
            lsRetailer.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName1, lastName1, firstName1 + "_Ntuc@foodie.com", password, CompanyRole.RETAILER, retailerCompany)));
            lsRetailer.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName2, lastName2, firstName2 + "_Ntuc@foodie.com", password, CompanyRole.RETAILER, retailerCompany)));
            lsRetailer.add(actorUserControllerLocal.createNewActorUser(new ActorUserEntity(firstName3, lastName3, firstName3 + "_Ntuc@foodie.com", password, CompanyRole.RETAILER, retailerCompany)));
            retailerCompany.setActorUserEntities(lsRetailer);

        } catch (InputDataValidationException ex) {
            System.err.println("********** DataInitializationSessionBean.initializeData(): " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("********** DataInitializationSessionBean.initializeData(): An error has occurred while loading initial test data: " + ex.getMessage());
        }
    }

    private void initializeLoggedInUserTransaction() {
        String token = "mF3gKKV8OA4VYYnU0NC4sXdgowTVsHHU9xVrWhPtyM6bQlUSfSpeXPrOlqLA72CFZBW0emPXEX9Y9wmw7QWLJ5jTDwg4gGg5x8MTeI36xRyc0gm6cAWVCou37jOPVutoOC8lLjd6cG1wti9UiEMUCfELtUyqQXfqMW8EFuPkJZmsGK4w4iI0q4x8dVovEgliBRDox1CaKwDey14wsr8BtLyhzN3qSRUbmqX7Bt8AlNfByK8uXuLIWAWsHPzz7tl4Qa9dvjreWJ6T0tErZllbioapabpRLiW3RnA9smlycUfAMy7ysPoIUGwQbeo5MLPJIbIuqNc3xCSsPFceIdh8IV0DtAxmj9EXu11Xl5CHipHzwYNeUZOXKucvhDL1vhhJbwwpXExV6bsoWERjaMQiVYHxLX0yIfBPd24prJc8bsaHsrJdillOSiCvJsdWHfKWH5sQ9BJok6kxfuES6o8fSNAgN1lZhuyp45PJ1gPIA5PPVKXhv8F3eAHJL7LGzAWlEeBITKM4ajpgbhX434lOSN3bxebhTCi4WeSJaQFxYrXPoyOQ7Tko3u6fsnSJky5FkqYWtjZIAuKSsOUOPVvZaZFtNXa9DIIIamo3J0G1neuHs3CVTKfHEWaoEbtGa5ORUFKjp2pZ0qxEGV6tW9pl4vi3gfOZ5OawvjZ4KgO5oolEzEIbMBnRkIg2RyGKDyl5azh8yxFEkQGRxylRcu7r7kHONlZGZOuXd65g2LB4QLWUtsH1bkyC5lfrE8lCl1XR3jDeCvoioUjbQ7f4i8FPvKyH8RZo39fxaiQosQCUBJ5xGCMTGenYC6NmThpkvec1IOzIzl14KaOduMwj9Q7jsYlHx6MAyjV3mv8PWMEEwvfr96GdeBn1liCYzvwReqAxbRBhy7jShk5RaFaTfyombjV8YPrrnx9kob9GA2BHGTMh978qWuSJVG9McLY4QMcXC4YQo2jLJZvdI4LCzeirggvcPMy4gB1CDoyK0YC8";
        LoggedInUserRecordEntity loggedInUserRecordEntity = new LoggedInUserRecordEntity(token, "email");
        loggedInUserRecordEntity = loggedInUserRecordEntityControllerLocal.createNewLoggedInUserRecord(loggedInUserRecordEntity);
        System.out.println("time: " + loggedInUserRecordEntity.getTimestampDateTime() + "    email: " + loggedInUserRecordEntity.getEmail());
    }
}
