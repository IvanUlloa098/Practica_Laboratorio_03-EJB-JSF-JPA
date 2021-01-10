package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: FacturaCabecera
 *
 */
@Entity

public class FacturaCabecera implements Serializable {

	  @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int codigo;
	    @Temporal(TemporalType.DATE)
	    private GregorianCalendar fecha;
	    private char anulado;
	    private double descuento;
	    private double subtotal;
	    private double iva_total;
	    private double total;
	    @Transient
	    private boolean editable;

	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturaCabecera")
	    private List<FacturaDetalle> listaFacturasDetalles;
	    //PEDIDOS
	    @OneToOne
	    private Pedido pedido;
	    @ManyToOne
	    private Persona persona;


	    public FacturaCabecera(){}

	    public FacturaCabecera(GregorianCalendar fecha, char anulado, double descuento, double subtotal, double iva_total, double total, Persona persona) {
	        this.codigo = codigo;
	        this.fecha = fecha;
	        this.anulado = anulado;
	        this.descuento = descuento;
	        this.subtotal = subtotal;
	        this.iva_total = iva_total;
	        this.iva_total = iva_total;
	        this.total = total;
	        this.listaFacturasDetalles = listaFacturasDetalles;
	        this.persona = persona;
	    }

	    public FacturaCabecera(GregorianCalendar fecha, char anulado, double descuento, double subtotal, double iva_total, double total, List<FacturaDetalle> listaFacturasDetalles, Persona persona, Pedido pedido) {
	        this.fecha = fecha;
	        this.anulado = anulado;
	        this.descuento = descuento;
	        this.subtotal = subtotal;
	        this.iva_total = iva_total;
	        this.total = total;
	        this.listaFacturasDetalles = listaFacturasDetalles;
	        this.persona = persona;
	        this.pedido = pedido;
	    }

	    public int getCodigo() {
	        return codigo;
	    }

	    public void setCodigo(int codigo) {
	        this.codigo = codigo;
	    }

	    public GregorianCalendar getFecha() {
	        return fecha;
	    }

	    public void setFecha(GregorianCalendar fecha) {
	        this.fecha = fecha;
	    }

	    public char getAnulado() {
	        return anulado;
	    }

	    public void setAnulado(char anulado) {
	        this.anulado = anulado;
	    }

	    public double getDescuento() {
	        return descuento;
	    }

	    public void setDescuento(double descuento) {
	        this.descuento = descuento;
	    }

	    public double getSubtotal() {
	        return subtotal;
	    }

	    public void setSubtotal(double subtotal) {
	        this.subtotal = subtotal;
	    }

	    public double getIva_total() {
	        return iva_total;
	    }

	    public void setIva_total(double iva_total) {
	        this.iva_total = iva_total;
	    }

	    public double getTotal() {
	        return total;
	    }

	    public void setTotal(double total) {
	        this.total = total;
	    }

	    public List<FacturaDetalle> getListaFacturasDetalles() {
	        return listaFacturasDetalles;
	    }

	    public void setListaFacturasDetalles(List<FacturaDetalle> listaFacturasDetalles) {
	        this.listaFacturasDetalles = listaFacturasDetalles;
	    }

	    public Persona getPersona() {
	        return persona;
	    }

	    public void setPersona(Persona persona) {
	        this.persona = persona;
	    }

	    public Pedido getPedido() {
	        return pedido;
	    }

	    public void setPedido(Pedido pedido) {
	        this.pedido = pedido;
	    }

	    public boolean isEditable() {
	        return editable;
	    }

	    public void setEditable(boolean editable) {
	        this.editable = editable;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof FacturaCabecera)) return false;
	        FacturaCabecera that = (FacturaCabecera) o;
	        return codigo == that.codigo;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(codigo);
	    }

	    @Override
	    public String toString() {
	        return "FacturaCabecera{" +
	                "codigo=" + codigo +
	                ", fecha=" + fecha +
	                ", anulado=" + anulado +
	                ", descuento=" + descuento +
	                ", subtotal=" + subtotal +
	                ", iva_total=" + iva_total +
	                ", total=" + total +
	                ", listaFacturasDetalles=" + listaFacturasDetalles +
	                ", persona=" + persona +
	                ", pedido=" + pedido +
	                '}';
	    }
	
   
}
