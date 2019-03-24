package ejb.session.stateless;

import entity.ActorUserEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UserActorNotFoundException;

@Local
public interface ActorUserControllerLocal {

    public ActorUserEntity createNewActorUser(ActorUserEntity newActorUser) throws InputDataValidationException;

    public List<ActorUserEntity> retrieveAllActorUser();

    public ActorUserEntity retrieveActorUserByEmail(String email) throws UserActorNotFoundException;

    public String actorUserLogin(String email, String password) throws InvalidLoginCredentialException;
}
