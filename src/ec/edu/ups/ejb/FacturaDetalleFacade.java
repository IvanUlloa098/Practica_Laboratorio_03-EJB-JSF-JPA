package ec.edu.ups.ejb;
import ec.edu.ups.entidad.*;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    
    public List<FacturaDetalle> buscarBodegaPorNombre(FacturaCabecera fc) {
    	
    	System.out.println("Bodega buscada "+fc.getCodigo());
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<FacturaDetalle> criteriaQuery= criteriaBuilder.createQuery(FacturaDetalle.class);
        Root<FacturaDetalle> categoriaRoot= criteriaQuery.from(FacturaDetalle.class);
        Predicate predicate= criteriaBuilder.equal(categoriaRoot.get("facturaCabecera"),fc);
        criteriaQuery.select(categoriaRoot).where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    	
    }
}