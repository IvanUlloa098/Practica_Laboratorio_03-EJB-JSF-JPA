package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Pedido
 *
 */
@Entity

public class Pedido implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    private String estado;
    private GregorianCalendar fecha_emision;
    @ManyToOne
    private Persona persona;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "pedido")
    private FacturaCabecera facturaCabecera;
    @Transient
    private boolean editable;

    public Pedido() {

    }

    public Pedido(String estado, GregorianCalendar fecha_emision, Persona persona, FacturaCabecera facturaCabecera) {
        this.estado = estado;
        this.fecha_emision = fecha_emision;
        this.persona = persona;
        this.facturaCabecera = facturaCabecera;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public GregorianCalendar getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(GregorianCalendar fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public FacturaCabecera getFacturaCabecera() {
        return facturaCabecera;
    }

    public void setFacturaCabecera(FacturaCabecera facturaCabecera) {
        this.facturaCabecera = facturaCabecera;
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
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return codigo == pedido.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "codigo=" + codigo +
                ", estado='" + estado + '\'' +
                ", fecha_emision=" + fecha_emision +
                ", persona=" + persona +
                ", facturaCabecera=" + facturaCabecera +
                '}';
    }

    public static List<Pedido> serializePedidos(List<Pedido> pedidos){
        List<Pedido> pedidoList = new ArrayList<>();

        pedidos.forEach(
                pedido -> {
                    int codigo = pedido.getCodigo();
                    Persona persona = pedido.getPersona();

                    persona.setFacturasCabeceraList(null);
                    persona.setPedidos(null);

                    String estado = pedido.getEstado();

                    pedido.setPersona(persona);
                    pedido.setCodigo(codigo);
                    pedido.setEstado(estado);
                    pedido.setFecha_emision(pedido.getFecha_emision());
                    pedido.setFacturaCabecera(null);
                    pedidoList.add(pedido);
                }
        );
        return pedidoList;
    }
}
