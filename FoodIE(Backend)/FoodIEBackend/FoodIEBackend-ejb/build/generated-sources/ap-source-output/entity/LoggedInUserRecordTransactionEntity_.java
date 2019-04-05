package entity;

import entity.LoggedInUserRecordEntity;
import java.time.OffsetDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.enumeration.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-02T15:36:02")
@StaticMetamodel(LoggedInUserRecordTransactionEntity.class)
public class LoggedInUserRecordTransactionEntity_ { 

    public static volatile SingularAttribute<LoggedInUserRecordTransactionEntity, LoggedInUserRecordEntity> loggedInUserRecordEntity;
    public static volatile SingularAttribute<LoggedInUserRecordTransactionEntity, Long> loggedInUserRecordTransactionEntityId;
    public static volatile SingularAttribute<LoggedInUserRecordTransactionEntity, String> hashedTransaction;
    public static volatile SingularAttribute<LoggedInUserRecordTransactionEntity, Transaction> transactionJob;
    public static volatile SingularAttribute<LoggedInUserRecordTransactionEntity, OffsetDateTime> timestampDateTime;
    public static volatile SingularAttribute<LoggedInUserRecordTransactionEntity, String> JWTToken;

}