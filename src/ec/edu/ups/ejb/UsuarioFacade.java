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
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "Practica_Laboratorio_03-EJB-JSF-JPA")
    private EntityManager entityManager;


    public UsuarioFacade(){
        super(Usuario.class);
    }


    public synchronized Usuario logIn(String correo, String password){
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteriaQuery= criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> usuarioRoot= criteriaQuery.from(Usuario.class);
        Predicate predicate= criteriaBuilder.equal(usuarioRoot.get("correo"),correo);
        Predicate predicate1= criteriaBuilder.equal(usuarioRoot.get("password"),password);
        Predicate validaciones= criteriaBuilder.and(predicate,predicate1);
        criteriaQuery.select(usuarioRoot).where(validaciones);
        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager(){
        return entityManager;
    }


}
