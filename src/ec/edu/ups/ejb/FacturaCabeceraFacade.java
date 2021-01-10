package ec.edu.ups.ejb;

import ec.edu.ups.entidad.*;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class FacturaCabeceraFacade extends AbstractFacade<FacturaCabecera> {
    @PersistenceContext(unitName = "Practica_Laboratorio_03-EJB-JSF-JPA")
    private EntityManager entityManager;

    public FacturaCabeceraFacade(){
        super(FacturaCabecera.class);
    }

    public FacturaCabecera getPedidoFacturaCabecera(Pedido pedido){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FacturaCabecera> criteriaQuery = criteriaBuilder.createQuery(FacturaCabecera.class);
        Root<FacturaCabecera> FacturaCabeceraRoot = criteriaQuery.from(FacturaCabecera.class);
        Predicate predicatePersona = criteriaBuilder.equal(FacturaCabeceraRoot.get("pedido"), pedido);
        criteriaQuery.select(FacturaCabeceraRoot).where(predicatePersona);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    protected EntityManager getEntityManager(){
        return entityManager;
    }
}
