package ups.edu.ec.ejb;
import ups.edu.ec.entidad.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FacturaDetalleFacade extends AbstractFacade<FacturaDetalle> {
    @PersistenceContext(unitName = "Practica_Laboratorio_03-EJB-JSF-JPA")
    private EntityManager entityManager;

    public FacturaDetalleFacade(){
        super(FacturaDetalle.class);
    }

    @Override
    protected EntityManager getEntityManager(){
        return entityManager;
    }
}