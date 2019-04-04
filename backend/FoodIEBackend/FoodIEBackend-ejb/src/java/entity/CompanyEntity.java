package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.CompanyRole;

@Entity
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    @Column(nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    private String name;
    @Column(nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private CompanyRole companyEntity;
    @OneToMany(mappedBy = "company")
    private List<ActorUserEntity> actorUserEntities;

    public CompanyEntity() {
        actorUserEntities = new ArrayList<>();
    }
    
    public CompanyEntity(String name, String email, CompanyRole companyEntity) {
        this();
        this.name = name;
        this.email = email;
        this.companyEntity = companyEntity;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public CompanyRole getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyRole companyEntity) {
        this.companyEntity = companyEntity;
    }

    public List<ActorUserEntity> getActorUserEntities() {
        return actorUserEntities;
    }

    public void setActorUserEntities(List<ActorUserEntity> actorUserEntities) {
        this.actorUserEntities = actorUserEntities;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyId != null ? companyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the companyId fields are not set
        if (!(object instanceof CompanyEntity)) {
            return false;
        }
        CompanyEntity other = (CompanyEntity) object;
        if ((this.companyId == null && other.companyId != null) || (this.companyId != null && !this.companyId.equals(other.companyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CompanyEntity[ id=" + companyId + " ]";
    }
    
}
