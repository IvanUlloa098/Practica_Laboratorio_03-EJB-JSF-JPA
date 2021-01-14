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
public class CategoriaFacade extends AbstractFacade<Categoria> {

    @PersistenceContext(unitName = "Practica_Laboratorio_03-EJB-JSF-JPA")
    private EntityManager entityManager;

    public CategoriaFacade(){
        super(Categoria.class);
    }

    public Categoria buscarCategoriaPorNombre(int codigo){
        System.out.println("llego a buscar  Categoria por nombre");
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Categoria> criteriaQuery= criteriaBuilder.createQuery(Categoria.class);
        Root<Categoria> categoriaRoot= criteriaQuery.from(Categoria.class);
        Predicate predicate= criteriaBuilder.equal(categoriaRoot.get("codigo"),codigo);
        criteriaQuery.select(categoriaRoot).where(predicate);
        System.out.println("Salio de a buscar Categoria por nombre");
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
    @Override
    protected EntityManager getEntityManager(){
        return entityManager;
    }

    public Categoria getCategoryByName(String nombreCategoria){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Categoria> categoriaCriteriaQuery = criteriaBuilder.createQuery(Categoria.class);
        Root<Categoria> categoriaRoot = categoriaCriteriaQuery.from(Categoria.class);

        categoriaCriteriaQuery = categoriaCriteriaQuery.select(categoriaRoot)
                .where(criteriaBuilder.equal(categoriaRoot.get("nombre"), nombreCategoria));
        return entityManager.createQuery(categoriaCriteriaQuery).getSingleResult();
    }

    public List<Categoria> categoriaList(){



        return null;
    }





}
