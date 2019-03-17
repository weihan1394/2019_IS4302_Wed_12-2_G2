package ejb.session.stateless;

import entity.ActorUser;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UserActorNotFoundException;
import util.security.CryptographicHelper;

@Stateless
@Local(ActorUserControllerLocal.class)

public class ActorUserController implements ActorUserControllerLocal {

    @PersistenceContext(unitName = "FoodIEBackend-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ActorUserController() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // Create new actoruser
    @Override
    public ActorUser createNewActorUser(ActorUser newActorUser) throws InputDataValidationException {
        Set<ConstraintViolation<ActorUser>> constraintViolations = validator.validate(newActorUser);

        if (constraintViolations.isEmpty()) {
            em.persist(newActorUser);
            em.flush();

            return newActorUser;
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public List<ActorUser> retrieveAllActorUser() {
        Query query = em.createQuery("SELECT au FROM ActorUser au");
        return query.getResultList();
    }
    
    @Override
    public ActorUser retrieveActorUserByEmail(String email) throws UserActorNotFoundException {
        Query query = em.createQuery("SELECT au FROM ActorUser au WHERE au.email = :inEmail");
        query.setParameter("inEmail", email);
        
        try {
            return (ActorUser)query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new UserActorNotFoundException("User: " + email + " does not exist!");
        }
    }
    
    @Override
    public ActorUser actorUserLogin(String email, String password) throws InvalidLoginCredentialException {
        try {
            ActorUser actorUser = retrieveActorUserByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + actorUser.getSalt()));
            
            if (actorUser.getPassword().equals(passwordHash)) {
                return actorUser;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (UserActorNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ActorUser>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
