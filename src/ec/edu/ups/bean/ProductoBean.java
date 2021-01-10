package ec.edu.ups.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import ec.edu.ups.ejb.*;
import ec.edu.ups.entidad.Bodega;
import ec.edu.ups.entidad.Categoria;
import ec.edu.ups.entidad.Producto;

//Activates CDI build-in beans
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named 
@RequestScoped
public class ProductoBean implements Serializable{
        
    private static final long serialVersionUID = 1L;
         
      private String nombre;
      private double precioVenta;
      private double precioCompra;
      private int iva;
      private int stock;
      private List<Categoria> list;
      private List<Bodega> bodegas;
      private List<Producto> productos;
      @EJB
      private CategoriaFacade ejbCategoriaFacade;
      @EJB
      private BodegaFacade ejbBodegaFacade;
      @EJB
      private ProductoFacade ejbProductoFacade;
      @PostConstruct
      public void init(){
          list=ejbCategoriaFacade.findAll();
          bodegas= ejbBodegaFacade.findAll();
          productos= ejbProductoFacade.findAll();

      }

   
      
}