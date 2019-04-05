package entity;

import entity.ActorUserEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.enumeration.CompanyRole;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-02T15:36:02")
@StaticMetamodel(CompanyEntity.class)
public class CompanyEntity_ { 

    public static volatile SingularAttribute<CompanyEntity, Long> companyId;
    public static volatile SingularAttribute<CompanyEntity, CompanyRole> companyEntity;
    public static volatile SingularAttribute<CompanyEntity, String> name;
    public static volatile SingularAttribute<CompanyEntity, String> email;
    public static volatile ListAttribute<CompanyEntity, ActorUserEntity> actorUserEntities;

}