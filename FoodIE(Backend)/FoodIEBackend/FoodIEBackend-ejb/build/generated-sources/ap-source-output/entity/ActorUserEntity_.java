package entity;

import entity.CompanyEntity;
import entity.LoggedInUserRecordEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.enumeration.CompanyRole;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-02T15:36:02")
@StaticMetamodel(ActorUserEntity.class)
public class ActorUserEntity_ { 

    public static volatile SingularAttribute<ActorUserEntity, String> firstName;
    public static volatile SingularAttribute<ActorUserEntity, String> lastName;
    public static volatile SingularAttribute<ActorUserEntity, String> password;
    public static volatile SingularAttribute<ActorUserEntity, String> salt;
    public static volatile SingularAttribute<ActorUserEntity, CompanyEntity> company;
    public static volatile SingularAttribute<ActorUserEntity, Long> id;
    public static volatile SingularAttribute<ActorUserEntity, CompanyRole> userRoleEnum;
    public static volatile SingularAttribute<ActorUserEntity, String> email;
    public static volatile ListAttribute<ActorUserEntity, LoggedInUserRecordEntity> loggedInUserRecordEntities;

}