package ejb.session.stateless;

import entity.LoggedInUserRecordEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(LoggedInUserRecordEntityControllerLocal.class)
public class LoggedInUserRecordEntityController implements LoggedInUserRecordEntityControllerLocal {

    @PersistenceContext(unitName = "FoodIEBackend-ejbPU")
    private EntityManager em;

    @Override
    public LoggedInUserRecordEntity createNewLoggedInUserRecord(LoggedInUserRecordEntity loggedInUserRecordEntity) {
        em.persist(loggedInUserRecordEntity);
        em.flush();
        em.refresh(loggedInUserRecordEntity);
        
        return loggedInUserRecordEntity;
    }
    
    @Override
    public List<LoggedInUserRecordEntity> retrieveAllLoggedInUserRecord() {
        Query query = em.createQuery("SELECT liur FROM LoggedInUserRecordEntity liur");
        return query.getResultList();
    }
}
