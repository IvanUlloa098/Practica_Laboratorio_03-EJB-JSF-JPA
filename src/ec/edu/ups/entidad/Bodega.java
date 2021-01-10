package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Bodega
 *
 */
@Entity

public class Bodega implements Serializable {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int codigo;
	    private String nombre;

	    @ManyToOne
	    private Ciudad ciudad;

	    @JsonbTransient
	    @ManyToMany(mappedBy = "bodegasList")
	    @JoinColumn
	    private List<Producto> productosList;
	    @Transient
	    private boolean editable;

	    //@JsonbTransient
	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bodega")
	    private List<Stock> lista_stock;

	    public Bodega(){
	        productosList= new ArrayList<Producto>();
	    }

	    public Bodega( String nombre, Ciudad ciudad) {
	        this.nombre = nombre;
	        this.ciudad = ciudad;
	        productosList = new ArrayList<Producto>();
	        lista_stock= new ArrayList<Stock>();
	    }

	    public List<Stock> getLista_stock() {
	        return lista_stock;
	    }

	    public void setLista_stock(List<Stock> lista_stock) {
	        this.lista_stock = lista_stock;
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

	    public Ciudad getCiudad() {
	        return ciudad;
	    }

	    public void setCiudad(Ciudad ciudad) {
	        this.ciudad = ciudad;
	    }

	    public List<Producto> getProductosList() {
	        return productosList;
	    }

	    public void setProductosList(List<Producto> productosList) {
	        this.productosList = productosList;
	    }

	    public boolean agregarProducto(Producto producto){
	        return this.productosList.add(producto);
	    }

	    public boolean addStock(Stock stock){
	        return this.lista_stock.add(stock);
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Bodega)) return false;
	        Bodega bodega = (Bodega) o;
	        return codigo == bodega.codigo;
	    }

	    public static List<Bodega> serializeBodegas(List<Bodega> bodegas){
	        List<Bodega> bodegaList = new ArrayList<>();
	        bodegas.forEach(
	                bodega -> {
	                    Pais pais = bodega.getCiudad().getProvincia().getPais();
	                    pais = new Pais(pais.getCodigo(), pais.getNombre());

	                    Provincia provincia = bodega.getCiudad().getProvincia();
	                    provincia = new Provincia(provincia.getCodigo(), provincia.getNombre(), pais, null);

	                    bodega.setCiudad(new Ciudad(bodega.getCiudad().getCodigo(), bodega.getCiudad().getNombre(), provincia, null));
	                    bodega.setProductosList(null);
	                    bodega.setLista_stock(null);
	                    bodegaList.add(bodega);
	                }
	        );
	        return bodegaList;
	    }

	    @Override
	    public String toString() {
	        return "Bodega{" +
	                "codigo=" + codigo +
	                ", nombre='" + nombre + '\'' +
	                ", ciudad=" + ciudad +
	                ", productosList=" + productosList +
	                ", lista_stock=" + lista_stock +
	                '}';
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(codigo);
	    }

	    public boolean isEditable() {
	        return editable;
	    }

	    public void setEditable(boolean editable) {
	        this.editable = editable;
	    }
}
