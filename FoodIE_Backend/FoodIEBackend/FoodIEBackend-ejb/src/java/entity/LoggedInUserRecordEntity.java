package entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class LoggedInUserRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loggedInUserRecordEId;
    
    @Lob
    private String JWTToken;
    
    @Column
    private OffsetDateTime timestampDateTime;
    
    @ManyToOne
    private ActorUserEntity actorUser;
    @OneToMany(mappedBy = "loggedInUserRecordEntity")
    private List<LoggedInUserRecordTransactionEntity> loggedInUserRecordTransactionEntities;

    public LoggedInUserRecordEntity() {
        timestampDateTime = OffsetDateTime.now();
        this.loggedInUserRecordTransactionEntities = new ArrayList<>();
    }

    public LoggedInUserRecordEntity(String JWTToken, ActorUserEntity actorUserEntity) {
        this();
        this.JWTToken = JWTToken;
        this.actorUser = actorUserEntity;
    }

    public Long getLoggedInUserRecordEId() {
        return loggedInUserRecordEId;
    }

    public void setLoggedInUserRecordEId(Long loggedInUserRecordEId) {
        this.loggedInUserRecordEId = loggedInUserRecordEId;
    }

    public String getJWTToken() {
        return JWTToken;
    }

    public void setJWTToken(String JWTToken) {
        this.JWTToken = JWTToken;
    }

    public OffsetDateTime getTimestampDateTime() {
        return timestampDateTime;
    }

    public void setTimestampDateTime(OffsetDateTime timestampDateTime) {
        this.timestampDateTime = timestampDateTime;
    }

    public List<LoggedInUserRecordTransactionEntity> getLoggedInUserRecordTransactionEntities() {
        return loggedInUserRecordTransactionEntities;
    }

    public void setLoggedInUserRecordTransactionEntities(List<LoggedInUserRecordTransactionEntity> loggedInUserRecordTransactionEntities) {
        this.loggedInUserRecordTransactionEntities = loggedInUserRecordTransactionEntities;
    }

    public ActorUserEntity getActorUser() {
        return actorUser;
    }

    public void setActorUser(ActorUserEntity actorUser) {
        this.actorUser = actorUser;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loggedInUserRecordEId != null ? loggedInUserRecordEId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the loggedInUserRecordEId fields are not set
        if (!(object instanceof LoggedInUserRecordEntity)) {
            return false;
        }
        LoggedInUserRecordEntity other = (LoggedInUserRecordEntity) object;
        if ((this.loggedInUserRecordEId == null && other.loggedInUserRecordEId != null) || (this.loggedInUserRecordEId != null && !this.loggedInUserRecordEId.equals(other.loggedInUserRecordEId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LoggedInUserRecord[ id=" + loggedInUserRecordEId + " ]";
    }
}
