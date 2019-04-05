package ejb.session.stateless;

import entity.CompanyEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(CompanyEntityControllerLocal.class)
public class CompanyEntityController implements CompanyEntityControllerLocal {

    @PersistenceContext(unitName = "FoodIEBackend-ejbPU")
    private EntityManager em;
    
    @Override
    public CompanyEntity createCompany(CompanyEntity newCompany) {
        em.persist(newCompany);
        em.flush();
        em.refresh(newCompany);
        
        return newCompany;
    }
    
    @Override
    public List<CompanyEntity> retrieveAllCompany() {
        Query query = em.createQuery("SELECT c FROM CompanyEntity c");
        return query.getResultList();
    }
    
    @Override
    public CompanyEntity updateCompany(CompanyEntity retrievedCompany) {
        em.merge(retrievedCompany);
        return retrievedCompany;
    }
}
