package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Pais
 *
 */
@Entity

public class Pais implements Serializable {
	 @Id
	    private String codigo;
	    private String nombre;

	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pais")
	    private List<Provincia> listaProvincias;


	    public Pais(){

	    }

	    public Pais(String codigo,String nombre) {
	        this.codigo=codigo;
	        this.nombre = nombre;
	    }

	    @Override
	    public String toString() {
	        return "Pais{" +
	                "codigo='" + codigo + '\'' +
	                ", nombre='" + nombre + '\'' +
	                ", listaProvincias=" + listaProvincias +
	                '}';
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Pais)) return false;
	        Pais pais = (Pais) o;
	        return Objects.equals(codigo, pais.codigo);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(codigo);
	    }

	    public String getCodigo() {
	        return codigo;
	    }

	    public void setCodigo(String codigo) {
	        this.codigo = codigo;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public List<Provincia> getListaProvincias() {
	        return listaProvincias;
	    }

	    public void setListaProvincias(List<Provincia> listaProvincias) {
	        this.listaProvincias = listaProvincias;
	    }

	    public boolean agregarProvincia(Provincia provincia){
	        return this.listaProvincias.add(provincia);
	    }


}
