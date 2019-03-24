package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import util.enumeration.UserRole;
import util.security.CryptographicHelper;

@Entity
public class ActorUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;
    @Column(nullable = false, unique = true, length = 64)
    @NotNull
    @Size(max = 64)
    @Email
    private String email;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String password;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private UserRole userRoleEnum;
    
    public ActorUserEntity() {
        // generate a salt
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
    }

    public ActorUserEntity(String firstName, String lastName, String email, String password, UserRole role) {
        this();
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRoleEnum = role;
        
        setPassword(password);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        if (password != null) {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        }
        else {
            this.password = null;
        }
    }
    
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserRole getUserRoleEnum() {
        return userRoleEnum;
    }

    public void setUserRoleEnum(UserRole userRoleEnum) {
        this.userRoleEnum = userRoleEnum;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActorUserEntity)) {
            return false;
        }
        ActorUserEntity other = (ActorUserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ id=" + id + " ]";
    }
}
