package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Categoria
 *
 */
@Entity

public class Categoria implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigo;
    private String nombre;
    private String imagen;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private List<Producto> productosList;

    public Categoria(){}

    public Categoria( String nombre, String imagen, List<Producto> productosList) {

        this.nombre = nombre;
        this.imagen = imagen;
        this.productosList = productosList;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Producto> getProductosList() {
        return productosList;
    }

    public void setProductosList(List<Producto> productosList) {
        this.productosList = productosList;
    }

    public boolean agregarProducto(Producto producto){
        return productosList.add(producto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria categoria = (Categoria) o;
        return codigo == categoria.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", productosList=" + productosList +
                '}';
    }
   
}
