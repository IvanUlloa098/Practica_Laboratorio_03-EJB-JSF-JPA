package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Provincia
 *
 */
@Entity

public class Provincia implements Serializable {


    @Id
    private String codigo;
    private String nombre;

    @ManyToOne
    @JoinColumn
    private Pais pais;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provincia")
    private List<Ciudad> listaCiudades;

    public Provincia(){}

    public Provincia(String codigo,String nombre,Pais pais) {

        this.nombre = nombre;
        this.codigo = codigo;
        this.pais = pais;
        /*this.listaCiudades = listaCiudades;*/
    }

    public Provincia(String codigo, String nombre, Pais pais, Object o) {
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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Ciudad> getListaCiudades() {
        return listaCiudades;
    }

    public void setListaCiudades(List<Ciudad> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provincia)) return false;
        Provincia that = (Provincia) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Provincias{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", pais=" + pais +
                ", listaCiudades=" + listaCiudades +
                '}';
    }

    public boolean agregarCiudad(Ciudad ciudad){
        return this.listaCiudades.add(ciudad);
    }
}
