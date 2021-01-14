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
    @EJB
    private StockFacade ejbStockFacade;

    private Map<String, String> mapaCodigoNombreProductos;
    private List<Producto> productosList;
    private List<Producto> productos_total;
    private List<Categoria> categoriasList;
    private List<Producto> filtrado;
    private List<Producto> filtrado2;
    

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
        bodegaList = bodegaFacade.findAll();
        categoriasList = categoriaFacade.findAll();

    }
    
    public List<Producto> getFiltrado2() {
		return consultarInventarioPorCategoria();
	}

	public void setFiltrado2(List<Producto> filtrado2) {
		this.filtrado2 = filtrado2;
	}

	public List<Categoria> getCategoriasList() {
		return categoriasList;
	}

	public void setCategoriasList(List<Categoria> categoriasList) {
		this.categoriasList = categoriasList;
	}

	public List<Bodega> getBodegaList() {
		return bodegaList;
	}

	public void setBodegaList(List<Bodega> bodegaList) {
		this.bodegaList = bodegaList;
	}

	public List<Producto> getFiltrado() {
		return consultarInventarioPorBodega();
	}


	public void setFiltrado(List<Producto> filtrado) {
		this.filtrado = filtrado;
	}

    public Producto getProducto(){return this.producto; }
    public void setProducto(Producto producto){this.producto = producto;}

    public List<Producto> getProductos_total() {
		return productos_total;
	}

	public void setProductos_total(List<Producto> productos_total) {
		this.productos_total = productos_total;
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

    public String getCategoriaSeleccionada(){
        return this.categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(String categoriaSeleccionada){
        this.categoriaSeleccionada = categoriaSeleccionada;
    }
    
    public List<Producto> listarProductosStockTotal() {
    	
    	List<Producto> prTotal =  new ArrayList<Producto>();
    	
    	for (Producto p:productosList) {
    		
    		p.setStock(this.consultarStockTotal(p));
    		prTotal.add(p);
    	}
    	
    	return prTotal;
    }
    
    public Integer consultarStockTotal(Producto pr) {
    	
    	int st = 0;
    	//System.out.println("ENTAR EN consultarStockPorBodega");
    	//System.out.println(">> "+pr.getNombre());
    	try {
    		List<Stock> productos_null= ejbStockFacade.recuperarStockProducto(pr);
            
            for (Stock s: productos_null) {
            	st = st + s.getStock();
            }
            
		} catch (Exception e) {
			System.out.println("NO HAY STOCK");
		}
    	
    	//System.out.println(">> "+st);
        return st;
    }
    
    public List<Producto> consultarInventarioPorBodega(){
    	
        if(bodegaSeleccionada!=null){
            Bodega bodega_to_inventario=bodegaFacade.buscarBodegaPorNombre(Integer.parseInt(bodegaSeleccionada));
            if(bodega_to_inventario!=null)
                System.out.println("bodega encontrada");
            
            List<Stock> stock_inventario= ejbStockFacade.recuperarStockPorBodega(bodega_to_inventario);
            if(bodega_to_inventario!=null)
                System.out.println("inventario encontrado");
            List<Producto> productos_inventario= new ArrayList<Producto>();
            for (Stock stock_inv:stock_inventario) {
            	
                String codigo_prod_inv=stock_inv.getProducto().getNombre();
                System.out.println("nombre del producto buscado "+codigo_prod_inv);

                Producto producto_inv=productoFacade.buscarPrductoPorNombre(codigo_prod_inv);

                producto_inv.setStock(stock_inv.getStock());
                System.out.println(producto_inv.toString());
                productos_inventario.add(producto_inv);
            }
            bodegaSeleccionada = null;
            return productos_inventario;
        } else {
            Producto pr = new Producto();
            pr.setNombre("No encontrado");
            List<Producto>productos_null= new ArrayList<Producto>();
            productos_null.add(pr);
            bodegaSeleccionada = null;
            return productos_null;

        }
    }
    
    public List<Producto> consultarInventarioPorCategoria(){
        
        if(categoriaSeleccionada!=null){
            Categoria bodega_to_inventario=categoriaFacade.buscarCategoriaPorNombre(Integer.parseInt(categoriaSeleccionada));
            if(bodega_to_inventario!=null)
                System.out.println("categoria encontrada");
            
            List<Producto> stock_inventario= productoFacade.getProductosPorCategoria(bodega_to_inventario);
            
            return stock_inventario;
        } else {
            Producto pr = new Producto();
            pr.setNombre("No encontrado");
            List<Producto>productos_null= new ArrayList<Producto>();
            productos_null.add(pr);
            return productos_null;

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