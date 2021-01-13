package ec.edu.ups.ejb;

import ec.edu.ups.entidad.*;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class BodegaFacade extends AbstractFacade<Bodega>{

    @PersistenceContext(unitName = "Practica_Laboratorio_03-EJB-JSF-JPA")
    private EntityManager entityManager;

    public BodegaFacade(){
        super(Bodega.class);
    }

    public Bodega buscarBodegaPorNombre(int codigo){
        System.out.println("Bodega buscada "+codigo);
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Bodega> criteriaQuery= criteriaBuilder.createQuery(Bodega.class);
        Root<Bodega> categoriaRoot= criteriaQuery.from(Bodega.class);
        Predicate predicate= criteriaBuilder.equal(categoriaRoot.get("codigo"),codigo);
        criteriaQuery.select(categoriaRoot).where(predicate);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    protected  EntityManager getEntityManager(){
        return entityManager;
    }





}
