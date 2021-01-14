package ec.edu.ups.bean;

import ec.edu.ups.ejb.*;
import ec.edu.ups.entidad.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped

public class FacturaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PersonaFacade ejbPersonaFacade;
    @EJB
    private FacturaCabeceraFacade ejbFacturaCabeceraFacade;
    private Persona persona;
    private String cedula;
    private String mensaje;
    private String nombre;
    private String apellido;
    private String direccion;
    private String celular;
    private String correo;

    public FacturaBean(){
    }
    @PostConstruct

    public void init(){


    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }


    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }


    //metodo para presentar si se encuentra la persona
    public void mensaje(){
        persona = ejbPersonaFacade.find(this.cedula);
        if ("".equals(this.cedula) || !this.cedula.equals(persona.getCedula()))  {
            this.mensaje = "no se encontro ningun usuario ";
        }
        else {
            this.mensaje = "usuario encontrado";
            this.nombre = persona.getNombre();
            this.apellido = persona.getApellido();
            this.celular = persona.getTelefono();
            this.direccion = persona.getDireccion();
        }
    }

    //metodo para registrar una persona a facturar
    public void crearPersona(){
        System.out.println("ha llegado a crear una persona");
        System.out.println(cedula + nombre + apellido +direccion + celular);
        ejbPersonaFacade.create(new Persona(this.cedula, this.nombre, this.apellido, this.direccion, this.celular));
        this.mensaje = "usuario registrado exitosamente";
    }

    
}
