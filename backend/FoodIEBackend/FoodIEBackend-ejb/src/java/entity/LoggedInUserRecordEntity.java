package entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class LoggedInUserRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loggedInUserRecordEId;
    
    @Lob
    private String JWTToken;
    
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String email;
    
    @Column
    private OffsetDateTime timestampDateTime;

    public LoggedInUserRecordEntity() {
        timestampDateTime = OffsetDateTime.now();
    }

    public LoggedInUserRecordEntity(String JWTToken, String email) {
        this();
        this.JWTToken = JWTToken;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
