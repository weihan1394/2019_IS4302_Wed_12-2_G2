package ejb.session.stateless;

import entity.LoggedInUserRecordEntity;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LoggedInUserRecordEntityControllerLocal {

    public LoggedInUserRecordEntity createNewLoggedInUserRecord(LoggedInUserRecordEntity loggedInUserRecordEntity);

    public List<LoggedInUserRecordEntity> retrieveAllLoggedInUserRecord();

    public LoggedInUserRecordEntity retrieveLoggedInUserByJWT(String JWTToken);
    
}
