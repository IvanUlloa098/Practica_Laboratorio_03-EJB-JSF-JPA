package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@Entity

public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigo;
    private String nombre;
    private String imagen;
    private double precioCompra;
    private double precioVenta;
    private char iva;
    private int stock;

    @JsonbTransient
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn
    private List<Bodega> bodegasList;

    @ManyToOne
    private Categoria categoria;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<FacturaDetalle> facturasDetallesList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<Stock> lista_stock;

    @Transient
    private boolean editable;
    
    public Producto(){
        bodegasList= new ArrayList<Bodega>();
        facturasDetallesList= new ArrayList<FacturaDetalle>();
        lista_stock= new ArrayList<Stock>();
    }

    public Producto(String nombre, String imagen, double precioCompra, double precioVenta, char iva, int stock,Categoria categoria) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.iva = iva;
        this.stock = stock;
        this.categoria = categoria;
        bodegasList= new ArrayList<Bodega>();
        lista_stock= new ArrayList<Stock>();
    }
    
    public Producto(int codigo, String nombre, String imagen, double precioCompra, double precioVenta, char iva, int stock,Categoria categoria) {
    	this.codigo = codigo;
    	this.nombre = nombre;
        this.imagen = imagen;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.iva = iva;
        this.stock = stock;
        this.categoria = categoria;
        bodegasList= new ArrayList<Bodega>();
        lista_stock= new ArrayList<Stock>();
    }

    public Producto(int codigo, String nombre, String imagen, double precioCompra, double precioVenta, char iva, int stock, List<Bodega> bodegasList, Categoria categoria, List<FacturaDetalle> facturasDetallesList) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.iva = iva;
        this.stock = stock;
        this.bodegasList = bodegasList;
        this.categoria = categoria;
        this.facturasDetallesList = facturasDetallesList;
    }

    public Producto(String nombre, String imagen, double precioCompra, double precioVenta, char iva, int stock, List<Bodega> bodegasList, Categoria categoria, List<FacturaDetalle> facturasDetallesList) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.iva = iva;
        this.stock = stock;
        this.bodegasList = bodegasList;
        this.categoria = categoria;
        this.facturasDetallesList = facturasDetallesList;
    }

    public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
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

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public char getIva() {
        return iva;
    }

    public void setIva(char iva) {
        this.iva = iva;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<Bodega> getBodegasList() {
        return bodegasList;
    }

    public void setBodegasList(List<Bodega> bodegasList) {
        this.bodegasList = bodegasList;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public List<FacturaDetalle> getFacturasDetallesList() {
        return facturasDetallesList;
    }

    public void setFacturasDetallesList(List<FacturaDetalle> facturasDetallesList) {
        this.facturasDetallesList = facturasDetallesList;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Stock> getLista_stock() {
        return lista_stock;
    }

    public void setLista_stock(List<Stock> lista_stock) {
        this.lista_stock = lista_stock;
    }

    public void addBodega(Bodega bodega){
        this.bodegasList.add(bodega);
    }

    public void addStock(Stock stock){
        this.lista_stock.add(stock);
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto producto = (Producto) o;
        return codigo == producto.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", precioCompra=" + precioCompra +
                ", precioVenta=" + precioVenta +
                ", iva=" + iva +
                ", stock=" + stock +
                ", bodegasList=" + bodegasList +
                ", categoria=" + categoria +
                ", facturasDetallesList=" + facturasDetallesList +
                '}';
    }
   
}
