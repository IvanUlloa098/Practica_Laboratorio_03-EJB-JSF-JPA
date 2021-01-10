package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: FacturaDetalle
 *
 */
@Entity

public class FacturaDetalle implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigo;
    private int cantidad;
    private double total;

    @ManyToOne
    private FacturaCabecera facturaCabecera;

    @ManyToOne
    private Producto producto;

    public FacturaDetalle(){}

    public FacturaDetalle(int cantidad, double total, FacturaCabecera facturaCabecera, Producto producto) {

        this.cantidad = cantidad;
        this.total = total;
        this.facturaCabecera = facturaCabecera;
        this.producto = producto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public FacturaCabecera getFacturaCabecera() {
        return facturaCabecera;
    }

    public void setFacturaCabecera(FacturaCabecera facturaCabecera) {
        this.facturaCabecera = facturaCabecera;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacturaDetalle)) return false;
        FacturaDetalle that = (FacturaDetalle) o;
        return codigo == that.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "FacturaDetalle{" +
                "codigo=" + codigo +
                ", cantidad=" + cantidad +
                ", total=" + total +
                ", facturaCabecera=" + facturaCabecera +
                ", producto=" + producto +
                '}';
    }
   
}
