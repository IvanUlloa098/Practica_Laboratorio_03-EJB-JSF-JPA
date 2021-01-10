package ec.edu.ups.bean;

import ec.edu.ups.ejb.*;
import ec.edu.ups.entidad.Pais;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class PaisBean implements Serializable {
    @EJB
    private PaisFacade ejbpaisFacade;

    private String nombre;


    public PaisBean(){

    }

    @PostConstruct
    public void init(){



    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Pais consultarPais(String nombre){
        Pais p;

        System.out.println("PAIS BEANNNN*******************************"+nombre);

        p=ejbpaisFacade.find(nombre);
        if (p==null){
            ejbpaisFacade.create(new Pais(nombre,nombre));
            System.out.println("Crear PAIS"+nombre);
            return ejbpaisFacade.find(nombre);
        }
        return new Pais("", "");
    }
}
