package ec.edu.ups.bean;

// https://i.pinimg.com/originals/96/2f/f6/962ff6c2e535eebc9d762cf420b631c8.gif

import ec.edu.ups.ejb.*;
import ec.edu.ups.entidad.*;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@ViewScoped
public class CatalogoBean implements Serializable{
    private static final long serialVersionUID = 1L;

    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private BodegaFacade bodegaFacade;

    private Map<String, String> mapaCodigoNombreProductos;
    private List<Producto> productosList;
    private List<Producto> filtrado;
    // Mapa Codigo <-> Nombre
    private Map<String, String> mapaCodigoNombreProducto;
    // Mapa Codigo <-> Producto
    private Map<Integer, Producto> mapaCodigoProducto;
    private Producto producto;

    // Lista de bodegas.
    private List<Bodega> bodegaList;
    // Variable para la categoria seleccionada.
    private String categoriaSeleccionada;
    // Variable para la bodega seleccionada.
    private String bodegaSeleccionada;

    @PostConstruct
    public void init() {
        mapaCodigoNombreProducto = new HashMap<>();
        mapaCodigoProducto = new HashMap<>();
        productosList = productoFacade.findAll();
        productosList.forEach(e->{mapaCodigoProducto.put(e.getCodigo(), e);});
        mapaCodigoNombreProducto = new TreeMap<>();
        mapaCodigoNombreProductos = new HashMap<>();
        producto = new Producto();

    }

    public Producto getProducto(){return this.producto; }
    public void setProducto(Producto producto){this.producto = producto;}

    public CatalogoBean(){
        filtrado = new ArrayList<>();
    }

    public void filtrarProductos(AjaxBehaviorEvent event){
        mapaCodigoNombreProductos = buscarProducto((String) ((UIOutput) event.getSource()).getValue());
    }

    public void abrirProducto(String param){
        producto = mapaCodigoProducto.get(Integer.parseInt(param));
        System.out.println(producto);
    }

    public List<String> getCategorias(){
        return categoriaFacade.findAll().parallelStream().map(Categoria::getNombre).collect(Collectors.toList());
    }

    public List<String> getBodegas(){
        bodegaList = bodegaFacade.findAll();
        return bodegaList.parallelStream().map(Bodega::getNombre).collect(Collectors.toList());
    }

    public Map<String, String> getProductos() {
        return mapaCodigoNombreProductos;
    }

    public void cargarProductosPorCategoria(){
        mapaCodigoNombreProductos = productoFacade.getProductosPorCategoria(categoriaFacade.getCategoryByName(categoriaSeleccionada));
    }

    public String getCategoriaSeleccionada(){
        return this.categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(String categoriaSeleccionada){
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public void cargarProductosPorBodega(){
        AtomicInteger atomicInteger= new AtomicInteger();
        Optional<Bodega> bodega = bodegaList.stream().filter(e -> e.getNombre().equals(bodegaSeleccionada)).findFirst();
        bodega.ifPresent(value -> {
            atomicInteger.set(value.getCodigo());});
        int codigoBodega = atomicInteger.get();
        List<Integer> idProductos = productoFacade.getProductosPorBodega(codigoBodega);
        if(!idProductos.isEmpty()){
            idProductos.forEach(
                    e->{
                        producto = productoFacade.find(e);
                        mapaCodigoNombreProductos.put(String.valueOf(producto.getCodigo()), producto.getNombre());
                    }
            );
            System.out.println(mapaCodigoNombreProductos);
        }
    }

    public void setBodegaSeleccionada(String bodegaSeleccionada){
        this.bodegaSeleccionada = bodegaSeleccionada;
    }

    public String getBodegaSeleccionada(){
        return this.bodegaSeleccionada;
    }

    private Map<String, String> buscarProducto(String productoNombre){
        mapaCodigoNombreProducto = new TreeMap<>();
        filtrado = productosList.stream().filter(value -> value.getNombre().toUpperCase().contains(productoNombre.toUpperCase())).collect(Collectors.toList());
        filtrado.forEach(e ->{mapaCodigoNombreProducto.put(String.valueOf(e.getCodigo()), e.getNombre()); });
        return mapaCodigoNombreProducto.isEmpty() ? new HashMap<>() : mapaCodigoNombreProducto;
    }

    public void redirect(){
        System.out.println("redirige");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Practica_Laboratorio_03-EJB-JSF-JPA/public/logIn.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}