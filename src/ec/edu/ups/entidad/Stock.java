package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Stock
 *
 */
@Entity

public class Stock implements Serializable {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int codigo;
	    private int stock;
	    @ManyToOne
	    private Producto producto;

	    @JsonbTransient
	    @ManyToOne
	    private Bodega bodega;
	    public Stock() {

	    }

	    public Stock(int stock, Producto producto, Bodega bodega) {
	        this.stock = stock;
	        this.producto = producto;
	        this.bodega = bodega;
	    }

	    public int getCodigo() {
	        return codigo;
	    }

	    public void setCodigo(int codigo) {
	        this.codigo = codigo;
	    }

	    public int getStock() {
	        return stock;
	    }

	    public void setStock(int stock) {
	        this.stock = stock;
	    }

	    public Producto getProducto() {
	        return producto;
	    }

	    public void setProducto(Producto producto) {
	        this.producto = producto;
	    }

	    public Bodega getBodega() {
	        return bodega;
	    }

	    public void setBodega(Bodega bodega) {
	        this.bodega = bodega;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Stock stock = (Stock) o;
	        return codigo == stock.codigo;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(codigo);
	    }

	    @Override
	    public String toString() {
	        return "Stock{" +
	                "codigo=" + codigo +
	                ", stock='" + stock + '\'' +
	                ", producto=" + producto +
	                ", bodega=" + bodega +
	                '}';
	    }
   
}
