package entity;

import entity.ActorUserEntity;
import entity.LoggedInUserRecordTransactionEntity;
import java.time.OffsetDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-02T15:36:02")
@StaticMetamodel(LoggedInUserRecordEntity.class)
public class LoggedInUserRecordEntity_ { 

    public static volatile SingularAttribute<LoggedInUserRecordEntity, OffsetDateTime> timestampDateTime;
    public static volatile ListAttribute<LoggedInUserRecordEntity, LoggedInUserRecordTransactionEntity> loggedInUserRecordTransactionEntities;
    public static volatile SingularAttribute<LoggedInUserRecordEntity, Long> loggedInUserRecordEId;
    public static volatile SingularAttribute<LoggedInUserRecordEntity, String> JWTToken;
    public static volatile SingularAttribute<LoggedInUserRecordEntity, ActorUserEntity> actorUser;

}