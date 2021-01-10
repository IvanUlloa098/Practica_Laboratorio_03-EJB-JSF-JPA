package ec.edu.ups.bean;
import ec.edu.ups.entidad.Pedido;

import java.io.Serializable;

public class RowPedido implements Serializable {
    private static final long serialVersionUID = 1L;
    private Pedido pedido;
    private String estadoSelect;
    private  String[] estados={"Receptado", "En proceso", "En camino", "Finalizado"};

    public RowPedido(Pedido p){
        this.pedido=p;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getEstadoSelect() {
        return estadoSelect;
    }

    public void setEstadoSelect(String estadoSelect) {
        this.estadoSelect = estadoSelect;
    }

    public String[] getEstados() {
        return estados;
    }

    public void setEstados(String[] estados) {
        this.estados = estados;
    }
}
