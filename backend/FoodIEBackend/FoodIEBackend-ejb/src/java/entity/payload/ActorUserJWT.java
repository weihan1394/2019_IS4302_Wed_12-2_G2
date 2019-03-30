package entity.payload;

import util.enumeration.CompanyRole;

public class ActorUserJWT {
    private String email;
    private String firstName;
    private String lastName;
    private CompanyRole role;

    public ActorUserJWT(String email, String firstName, String lastName, CompanyRole role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public CompanyRole getRole() {
        return role;
    }

    public void setRole(CompanyRole role) {
        this.role = role;
    }
    
    
}
