package ec.edu.ups.bean;

import ec.edu.ups.ejb.CiudadFacade;
import ec.edu.ups.entidad.Ciudad;
import ec.edu.ups.entidad.Provincia;

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