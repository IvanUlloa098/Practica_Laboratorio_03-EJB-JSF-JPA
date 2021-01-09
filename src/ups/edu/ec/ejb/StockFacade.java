package ups.edu.ec.ejb;

import ups.edu.ec.entidad.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class StockFacade extends AbstractFacade<Stock> {
    @PersistenceContext(unitName = "Practica_Laboratorio_03-EJB-JSF-JPA")
    private EntityManager entityManager;

    public StockFacade() {
        super(Stock.class);
    }

    public Stock recuperarStock(Producto producto, Bodega bodega){
        System.out.println("heree");
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> criteriaQuery= criteriaBuilder.createQuery(Stock.class);
        System.out.println("heree222" );
        Root<Stock> usuarioRoot= criteriaQuery.from(Stock.class);
        System.out.println("heree333");
        Predicate predicate= criteriaBuilder.equal(usuarioRoot.get("producto"),producto);
        System.out.println("heree4");
        Predicate predicate1= criteriaBuilder.equal(usuarioRoot.get("bodega"),bodega);
        Predicate validaciones= criteriaBuilder.and(predicate,predicate1);
        System.out.println("heree5");
        criteriaQuery.select(usuarioRoot).where(validaciones);
        System.out.println("heree6");
        return entityManager.createQuery(criteriaQuery).getSingleResult();

    }
    public List<Stock> recuperarStockPorBodega(Bodega bodega){
        System.out.println("heree");
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> criteriaQuery= criteriaBuilder.createQuery(Stock.class);
        System.out.println("heree222" );
        Root<Stock> usuarioRoot= criteriaQuery.from(Stock.class);
        System.out.println("heree333");
        Predicate predicate= criteriaBuilder.equal(usuarioRoot.get("bodega"),bodega);
        System.out.println("heree5");
        criteriaQuery.select(usuarioRoot).where(predicate);
        System.out.println("heree6");
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
