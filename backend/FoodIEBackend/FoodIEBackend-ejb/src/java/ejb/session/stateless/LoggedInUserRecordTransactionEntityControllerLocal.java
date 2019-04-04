package ejb.session.stateless;

import entity.LoggedInUserRecordTransactionEntity;
import javax.ejb.Local;

@Local
public interface LoggedInUserRecordTransactionEntityControllerLocal {

    public LoggedInUserRecordTransactionEntity createNewLoggedInUserRecordTransactionEntity(LoggedInUserRecordTransactionEntity newLoggedInUserRecordTransactionEntity);

    public void updateTransactionStatus(LoggedInUserRecordTransactionEntity loggedInUserRecordTransactionEntity);
    
}
