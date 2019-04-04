/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CompanyEntity;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CompanyEntityControllerLocal {

    public CompanyEntity createCompany(CompanyEntity newCompany);

    public List<CompanyEntity> retrieveAllCompany();

    public CompanyEntity updateCompany(CompanyEntity retrievedCompany);
    
}
