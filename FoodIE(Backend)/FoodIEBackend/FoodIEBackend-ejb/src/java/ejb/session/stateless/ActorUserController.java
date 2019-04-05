package ejb.session.stateless;

import com.google.gson.Gson;
import entity.ActorUserEntity;
import entity.LoggedInUserRecordEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
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
import util.security.JWTManager;

@Stateless
@Local(ActorUserControllerLocal.class)
public class ActorUserController implements ActorUserControllerLocal {

    @PersistenceContext(unitName = "FoodIEBackend-ejbPU")
    private EntityManager em;

    @EJB
    private LoggedInUserRecordEntityControllerLocal loggedInUserRecordEntityControllerLocal;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    private final JWTManager jWTManager;

    private final Gson gson;

    public ActorUserController() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        gson = new Gson();

        jWTManager = new JWTManager();
    }

    // Create new actoruser
    @Override
    public ActorUserEntity createNewActorUser(ActorUserEntity newActorUser) throws InputDataValidationException {
        Set<ConstraintViolation<ActorUserEntity>> constraintViolations = validator.validate(newActorUser);

        if (constraintViolations.isEmpty()) {
            em.persist(newActorUser);
            em.flush();

            return newActorUser;
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<ActorUserEntity> retrieveAllActorUser() {
        Query query = em.createQuery("SELECT au FROM ActorUserEntity au");
        return query.getResultList();
    }

    @Override
    public ActorUserEntity retrieveActorUserByEmail(String email) throws UserActorNotFoundException {
        System.out.println("checking.... " + email);
        Query query = em.createQuery("SELECT au FROM ActorUserEntity au WHERE au.email = :inEmail");
        query.setParameter("inEmail", email);

        try {
            return (ActorUserEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new UserActorNotFoundException("User: " + email + " does not exist!");
        }
    }

    @Override
    public String actorUserLogin(String email, String password) throws InvalidLoginCredentialException {
        try {
            ActorUserEntity actorUserEntity = retrieveActorUserByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + actorUserEntity.getSalt()));

            if (actorUserEntity.getPassword().equals(passwordHash)) {
//                String jsonStr = gson.toJson(actorUserEntity);

                // logged the login jwt token to database
                // create jwt
                String response = jWTManager.createJWT(actorUserEntity, null, "login");

                // logged the log in transaction
                LoggedInUserRecordEntity loggedInUserRecordEntity = new LoggedInUserRecordEntity(response, actorUserEntity);
                loggedInUserRecordEntityControllerLocal.createNewLoggedInUserRecord(loggedInUserRecordEntity);

                // return the jwt response
                return response;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (UserActorNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ActorUserEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
