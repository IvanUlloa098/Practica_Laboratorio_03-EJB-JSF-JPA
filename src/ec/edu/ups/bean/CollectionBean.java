package ec.edu.ups.bean;

import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.ejb.FacturaDetalleFacade;
import ec.edu.ups.ejb.StockFacade;
import ec.edu.ups.entidad.FacturaCabecera;
import ec.edu.ups.entidad.FacturaDetalle;
import ec.edu.ups.entidad.Stock;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class CollectionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<FacturaCabecera> list ;
    

    @EJB
    private FacturaCabeceraFacade  ejbfacturaCabeceraFacade ;
    
    @EJB
	private FacturaDetalleFacade ejbFacturaDetalleFacade;
    
    @EJB
    private StockFacade ejbStockFacade;


    public CollectionBean() {
    }


    @PostConstruct
    public void init(){
        list = ejbfacturaCabeceraFacade.findAll();
    }

    public FacturaCabecera[] getList() {
        return list.toArray(new FacturaCabecera[0]);
    }
    
    public void anularFacturas(int id) {
		
    	FacturaCabecera fc;
    	
    	List<FacturaDetalle> ls; 
    	
    	List<Stock> st;
    	
    	Stock aux;
    	
    	try {
			
			fc = ejbfacturaCabeceraFacade.find(id);
			fc.setAnulado('S');
			ejbfacturaCabeceraFacade.edit(fc);
			
			ls = ejbFacturaDetalleFacade.buscarBodegaPorNombre(fc);
			
			for (FacturaDetalle d : ls) {
				st = ejbStockFacade.recuperarStockProducto(d.getProducto());
				aux = st.get(0);
				aux.setStock(st.get(0).getStock()+d.getCantidad());
				ejbStockFacade.edit(aux);
			}
			
			System.out.println(">> FACTURA ANULADA");
			this.init();
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("/Practica_Laboratorio_03-EJB-JSF-JPA/private/listarFacturas.xhtml");
		} catch (Exception e) {
			System.out.println(">> FACTURA NO ANULADA");
			this.init();
			
		}
    	
	}

    public void setList(List<FacturaCabecera> list) {
        this.list = list;
    }

    public String delete(FacturaCabecera c) {
        ejbfacturaCabeceraFacade.remove(c);
        list = ejbfacturaCabeceraFacade.findAll();
        return null;
    }

    public String edit(FacturaCabecera c) {
        c.setEditable(true);
        return null;
    }

    public String save(FacturaCabecera c) {
        ejbfacturaCabeceraFacade.edit(c);
        c.setEditable(false);
        return null;
    }
}