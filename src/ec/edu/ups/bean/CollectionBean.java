package ec.edu.ups.bean;

import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.entidad.FacturaCabecera;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CollectionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<FacturaCabecera> list ;
    private String cedula;

    @EJB
    FacturaCabeceraFacade  ejbfacturaCabeceraFacade ;


    public CollectionBean() {
    }
    


    @PostConstruct
    public void init(){
        list = ejbfacturaCabeceraFacade.findAll();
    }

    public FacturaCabecera[] getList() {
        return list.toArray(new FacturaCabecera[0]);
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
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
//Metodo para buscar factura
    
    public void obtenerFactura() {
    	List<FacturaCabecera> facturasCabecera = new ArrayList<FacturaCabecera>();
    	facturasCabecera = ejbfacturaCabeceraFacade.findAll();
    	
    	
    	List<FacturaCabecera> facturasFiltradas = new ArrayList<FacturaCabecera>();
    	if(!facturasCabecera.isEmpty()) {
    		for (FacturaCabecera fc : facturasCabecera)
    			if(fc.getPersona() != null) {
    				if(fc.getPersona().getCedula() != null) {
    					if(fc.getPersona().getCedula().equals(cedula))
    						facturasFiltradas.add(fc);
    				}else {
    					System.out.println("ERROR AL OBTENER LA CEDULA");
    				}
    			}else {
    				System.out.println("ERROR AL OBTENER EL USUARIO");
    			}
    	}else {
    		System.out.println("NO SE ENCONTRO NINGUNA FACTURA CABECERA!");
    	}
    	if(!facturasFiltradas.isEmpty()) {
    		System.out.println(facturasFiltradas);
    		list = facturasFiltradas;
    	}
    	else
    		System.out.println("ERROR!, no se ha encontrado ninguna factura con la cedula: " + cedula);
    }
    
}