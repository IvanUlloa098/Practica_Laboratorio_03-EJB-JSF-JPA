package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Ciudad
 *
 */
@Entity

public class Ciudad implements Serializable {
	 @Id
	    private String codigo;
	    private String nombre;

	    @ManyToOne
	    private Provincia provincia;

	    @JsonbTransient
	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudad")
	    private List<Bodega> listaBodeas;

	    public Ciudad(){

	    }

	    public Ciudad(String codigo, String nombre, Provincia provincia) {
	        this.codigo = codigo;
	        this.nombre = nombre;
	        this.provincia = provincia;
	        /*this.listaBodeas = listaBodeas;*/
	    }

	    public Ciudad(String codigo, String nombre, Provincia provincia, List<Bodega> listaBodeas) {
	        this.codigo = codigo;
	        this.nombre = nombre;
	        this.provincia = provincia;
	        this.listaBodeas = listaBodeas;
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

	    public Provincia getProvincia() {
	        return provincia;
	    }

	    public void setProvincia(Provincia provincias) {
	        this.provincia = provincias;
	    }

	    public List<Bodega> getListaBodeas() {
	        return listaBodeas;
	    }

	    public void setListaBodeas(List<Bodega> listaBodeas) {
	        this.listaBodeas = listaBodeas;
	    }



	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Ciudad)) return false;
	        Ciudad ciudad = (Ciudad) o;
	        return Objects.equals(codigo, ciudad.codigo);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(codigo);
	    }

	    @Override
	    public String toString() {
	        return "Ciudad{" +
	                "codigo='" + codigo + '\'' +
	                ", nombre='" + nombre + '\'' +
	                ", provincias=" + provincia +
	                ", listaBodeas=" + listaBodeas +
	                '}';
	    }

	    public boolean agregarBodega(Bodega bodega){
	        return this.listaBodeas.add(bodega);
	    }
   
}
