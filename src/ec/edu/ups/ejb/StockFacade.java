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
public class StockFacade extends AbstractFacade<Stock> {
    @PersistenceContext(unitName = "Practica_Laboratorio_03-EJB-JSF-JPA")
    private EntityManager entityManager;

    public StockFacade() {
        super(Stock.class);
    }

    public Stock recuperarStock(Producto producto, Bodega bodega){
        
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> criteriaQuery= criteriaBuilder.createQuery(Stock.class);
       
        Root<Stock> usuarioRoot= criteriaQuery.from(Stock.class);
        Predicate predicate= criteriaBuilder.equal(usuarioRoot.get("producto"),producto);
        Predicate predicate1= criteriaBuilder.equal(usuarioRoot.get("bodega"),bodega);
        System.out.println("mark 1");
        Predicate validaciones= criteriaBuilder.and(predicate,predicate1);
        criteriaQuery.select(usuarioRoot).where(validaciones);
        return entityManager.createQuery(criteriaQuery).getSingleResult();

    }
    
    public List<Stock> recuperarStockProducto(Producto producto){
        
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> criteriaQuery= criteriaBuilder.createQuery(Stock.class);
       
        Root<Stock> stockRoot= criteriaQuery.from(Stock.class);
        Predicate predicate= criteriaBuilder.equal(stockRoot.get("producto"),producto);
        
        System.out.println("mark 1");
        criteriaQuery.select(stockRoot).where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }
    
    public List<Stock> recuperarStockPorBodega(Bodega bodega){
     
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> criteriaQuery= criteriaBuilder.createQuery(Stock.class);
        Root<Stock> usuarioRoot= criteriaQuery.from(Stock.class);
        Predicate predicate= criteriaBuilder.equal(usuarioRoot.get("bodega"),bodega);
        criteriaQuery.select(usuarioRoot).where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
