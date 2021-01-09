package ups.edu.ec.ejb;

import ups.edu.ec.entidad.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.GregorianCalendar;
import java.util.List;

@Stateless
public class PedidoFacade extends AbstractFacade<Pedido> {

    @PersistenceContext(unitName = "Practica_Laboratorio_03-EJB-JSF-JPA")
    private EntityManager entityManager;

    public PedidoFacade(){super(Pedido.class);}

    @Override
    protected EntityManager getEntityManager(){return entityManager;}

    public Pedido getUltimoPedido(Persona persona, GregorianCalendar date){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> pedidoRoot = criteriaQuery.from(Pedido.class);
        Predicate predicatePersona = criteriaBuilder.equal(pedidoRoot.get("persona"), persona);
        Predicate predicateFecha = criteriaBuilder.equal(pedidoRoot.get("fecha_emision"), date);
        Predicate[] predicates = new Predicate[]{predicatePersona, predicateFecha};

        criteriaQuery.select(pedidoRoot).where(predicates);
        List<Pedido> pedidoList = entityManager.createQuery(criteriaQuery).getResultList();

        return pedidoList.get(pedidoList.size()-1);
    }
    //SCORPION CODE
    public Pedido getCurrentPedido(Persona persona){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> pedidoRoot = criteriaQuery.from(Pedido.class);
        Predicate predicatePersona = criteriaBuilder.equal(pedidoRoot.get("persona"), persona);
        Predicate[] predicates = new Predicate[]{predicatePersona};
        criteriaQuery.select(pedidoRoot).where(predicates);
        List<Pedido> pedidoList = entityManager.createQuery(criteriaQuery).getResultList();
        return pedidoList.get(pedidoList.size()-1);

    }

    public List<Pedido> findByPedidosId(Persona persona) {
        // TODO Auto-generated method stub
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> pedidoRoot = criteriaQuery.from(Pedido.class);
        Predicate predicatePersona = criteriaBuilder.equal(pedidoRoot.get("persona"), persona);
        criteriaQuery.select(pedidoRoot).where(predicatePersona);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

}
