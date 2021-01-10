package ec.edu.ups.bean;
import java.io.Serializable;

public class Row implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int quantity;
    private double precio;
    private double subtotal;
    private boolean editable;

    public Row(int id, String name, int quantity , double precio, double subtotal) {

        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.precio = precio;
        this.subtotal = subtotal;
    }

    // getters & setters
    // hasCode & equals


    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        return "Row [id=" + id + ", name=" + name + ", quantity=" + quantity + ", editable=" + editable + "]";
    }
}
