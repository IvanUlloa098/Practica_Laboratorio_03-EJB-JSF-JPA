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
public class PersonaFacade extends AbstractFacade<Persona> {
    @PersistenceContext(unitName = "Practica_Laboratorio_03-EJB-JSF-JPA")
    private EntityManager entityManager;

    public PersonaFacade(){
        super(Persona.class);
    }

    @Override
    protected EntityManager getEntityManager(){
        return entityManager;
    }

    public boolean verificarUsuario(String cedula, String password){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Persona> usuarioCriteriaQuery = criteriaBuilder.createQuery(Persona.class);
        Root<Persona> usuarioRoot = usuarioCriteriaQuery.from(Persona.class);

        Predicate[] predicates = new Predicate[]{criteriaBuilder.equal(usuarioRoot.get("cedula"), cedula),
        criteriaBuilder.equal(usuarioRoot.get("password"), password),
        criteriaBuilder.equal(usuarioRoot.get("anulado"), 'F')};

        usuarioCriteriaQuery.select(usuarioRoot)
                .where(predicates);
        try {
            Persona persona = getEntityManager().createQuery(usuarioCriteriaQuery).getSingleResult();
            return persona != null;
        }catch (Exception e){
            return false;
        }
    }

    public Persona searchPerson(String cedula){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Persona> usuarioCriteriaQuery = criteriaBuilder.createQuery(Persona.class);
        Root<Persona> usuarioRoot = usuarioCriteriaQuery.from(Persona.class);
        Predicate predicate= criteriaBuilder.equal(usuarioRoot.get("cedula"),cedula);
        usuarioCriteriaQuery.select(usuarioRoot).where(predicate);
        return entityManager.createQuery(usuarioCriteriaQuery).getSingleResult();
    }

}