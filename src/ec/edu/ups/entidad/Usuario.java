package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity

public class Usuario implements Serializable {
	@Id
    private int codigo;
    private String correo;
    private String password;

    @ManyToOne
    private Rol rol;


    public Usuario(){
    	
    }

    public Usuario(String correo, String password, Rol rol) {
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(String cedula, String nombre, String apellido, String direccion, String telefono, String correo, String password, Rol rol, List<FacturaCabecera> facturaCabeceras) {
        super();

        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return codigo == usuario.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "codigo=" + codigo +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                "} " + super.toString();
    }
}
