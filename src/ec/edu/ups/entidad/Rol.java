package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Rol
 *
 */
@Entity

public class Rol implements Serializable {
	 @Id
	    private String nombre;
	    private String descripcion;

	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol")
	    private List<Usuario> usuariosRolesList;

	   public Rol() {
		   
	   }

	    public Rol(String nombre, String descripcion, List<Usuario> usuariosRolesList) {
	        this.nombre = nombre;
	        this.descripcion = descripcion;
	        this.usuariosRolesList = usuariosRolesList;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

	    public boolean agregarUsuario(Usuario usuario){
	        return this.usuariosRolesList.add(usuario);
	    }

	    @Override
	    public String toString() {
	        return "Rol{" +
	                "nombre='" + nombre + '\'' +
	                ", descripcion='" + descripcion + '\'' +
	                ", usuariosRolesList=" + usuariosRolesList +
	                '}';
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Rol)) return false;
	        Rol rol = (Rol) o;
	        return Objects.equals(nombre, rol.nombre);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(nombre);
	    }
   
}
