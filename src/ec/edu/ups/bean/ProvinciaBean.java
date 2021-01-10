package ec.edu.ups.bean;
import ec.edu.ups.entidad.*;
import ec.edu.ups.ejb.*;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ProvinciaBean {
    @EJB
    private ProvinciaFacade provinciaFacade;
    private String nombre;


    public ProvinciaBean(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Provincia consultarProvincia(String nombre, Pais pais){
        Provincia p=provinciaFacade.find(nombre);
        if (p ==null){
            provinciaFacade.create(new Provincia(nombre,nombre,pais));
            return (Provincia) provinciaFacade.find(nombre);
        }
        return p;
    }
}
