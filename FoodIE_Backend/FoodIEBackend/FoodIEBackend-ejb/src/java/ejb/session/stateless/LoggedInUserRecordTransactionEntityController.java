package ejb.session.stateless;

import entity.LoggedInUserRecordTransactionEntity;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(LoggedInUserRecordTransactionEntityControllerLocal.class)
public class LoggedInUserRecordTransactionEntityController implements LoggedInUserRecordTransactionEntityControllerLocal {

    @PersistenceContext(unitName = "FoodIEBackend-ejbPU")
    private EntityManager em;

    @Override
    public LoggedInUserRecordTransactionEntity createNewLoggedInUserRecordTransactionEntity(LoggedInUserRecordTransactionEntity newLoggedInUserRecordTransactionEntity) {
        em.persist(newLoggedInUserRecordTransactionEntity);
        em.flush();
        em.refresh(newLoggedInUserRecordTransactionEntity);
        
        return newLoggedInUserRecordTransactionEntity;
    }
    
    @Override
    public void updateTransactionStatus(LoggedInUserRecordTransactionEntity loggedInUserRecordTransactionEntity) {
        em.merge(loggedInUserRecordTransactionEntity);
    }
}
