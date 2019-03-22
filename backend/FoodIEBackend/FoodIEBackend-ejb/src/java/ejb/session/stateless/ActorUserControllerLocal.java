package ejb.session.stateless;

import entity.ActorUser;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UserActorNotFoundException;

@Local
public interface ActorUserControllerLocal {

    public ActorUser createNewActorUser(ActorUser newActorUser) throws InputDataValidationException;

    public List<ActorUser> retrieveAllActorUser();

    public ActorUser retrieveActorUserByEmail(String email) throws UserActorNotFoundException;

    public String actorUserLogin(String email, String password) throws InvalidLoginCredentialException;
}
