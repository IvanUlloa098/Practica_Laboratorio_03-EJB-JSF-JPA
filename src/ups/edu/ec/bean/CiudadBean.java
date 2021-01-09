package ups.edu.ec.bean;

import ups.edu.ec.ejb.CiudadFacade;
import ups.edu.ec.entidad.Ciudad;
import ups.edu.ec.entidad.Provincia;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class CiudadBean {
    @EJB
    private CiudadFacade ciudadFacade;
    private String nombre;


    public CiudadBean(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Ciudad consultarCiudad(String nombre, Provincia provincia){
        Ciudad c=ciudadFacade.find(nombre);
        if (c ==null){
        	ciudadFacade.create(new Ciudad(nombre,nombre,provincia));
            return (Ciudad) ciudadFacade.find(nombre);
        }
        return c;
    }
}