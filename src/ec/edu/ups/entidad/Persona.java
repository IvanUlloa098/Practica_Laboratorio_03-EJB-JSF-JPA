package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Persona
 *
 */
@Entity

public class Persona implements Serializable {

	 @Id
	    private String cedula;
	    private String nombre;
	    private String apellido;
	    private String direccion;
	    private String telefono;
	    private String correo;
	    private String password;
	    private char anulado;

	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
	    private List<FacturaCabecera> facturasCabeceraList;
	    //PEDIDOS
	    @OneToMany(cascade = CascadeType.ALL,mappedBy = "persona")
	    private List<Pedido> pedidos;

	    public Persona(){}

	    public Persona(String cedula,String nombre, String apellido, String direccion, String telefono, List<FacturaCabecera> facturasCabeceraList, List<Pedido> pedidos) {
	        this.cedula=cedula;
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.direccion = direccion;
	        this.telefono = telefono;
	        this.facturasCabeceraList = facturasCabeceraList;
	        this.pedidos = pedidos;
	    }

	    public Persona(String cedula, String nombre, String apellido, String direccion, String telefono, String correo, String password, char anulado) {
	        this.cedula = cedula;
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.direccion = direccion;
	        this.telefono = telefono;
	        this.correo = correo;
	        this.password = password;
	        this.anulado = anulado;
	    }

	    public Persona(String cedula, String nombre, String apellido, String direccion, String telefono) {
	        this.cedula = cedula;
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.direccion = direccion;
	        this.telefono=telefono;
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

	    public char getAnulado() {
	        return anulado;
	    }

	    public void setAnulado(char anulado) {
	        this.anulado = anulado;
	    }

	    public String getCedula() {
	        return cedula;
	    }

	    public void setCedula(String cedula) {
	        this.cedula = cedula;
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

	    public String getTelefono() {
	        return telefono;
	    }

	    public void setTelefono(String telefono) {
	        this.telefono = telefono;
	    }

	    public List<FacturaCabecera> getFacturasCabeceraList() {
	        return facturasCabeceraList;
	    }

	    public void setFacturasCabeceraList(List<FacturaCabecera> facturasCabeceraList) {
	        this.facturasCabeceraList = facturasCabeceraList;
	    }

	    public List<Pedido> getPedidos() {
	        return pedidos;
	    }

	    public void setPedidos(List<Pedido> pedidos) {
	        this.pedidos = pedidos;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Persona)) return false;
	        Persona persona = (Persona) o;
	        return cedula.equals(persona.cedula);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(cedula);
	    }

	    @Override
	    public String toString() {
	        return "Persona{" +
	                "cedula='" + cedula + '\'' +
	                ", nombre='" + nombre + '\'' +
	                ", apellido='" + apellido + '\'' +
	                ", direccion='" + direccion + '\'' +
	                ", telefono='" + telefono + '\'' +
	                ", facturasCabeceraList=" + facturasCabeceraList +
	                ", pedidos=" + pedidos +
	                '}';
	    }
   
}
