package ec.edu.ups.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.ejb.FacturaDetalleFacade;
import ec.edu.ups.ejb.PersonaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.ejb.StockFacade;
import ec.edu.ups.entidad.FacturaCabecera;
import ec.edu.ups.entidad.FacturaDetalle;
import ec.edu.ups.entidad.Persona;
import ec.edu.ups.entidad.Producto;
import ec.edu.ups.entidad.Stock;

import javax.servlet.http.Cookie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped

public class DetalleBean implements Serializable {
	// private static final long serialVersionUID = 1L;
	private Set<Row> list = new HashSet<Row>();

	private String cookie;
	private int id;
	private String name;
	private String quantity;
	private double precio;
	private double subtotal;
	private String busqueda;
	private int disminuir;

	@EJB
	private ProductoFacade ejbProductoFacade;

	private Producto producto;
	private Double subtotalcabecera;
	private int fila;
	private double descuento;
	private double iva;
	private double totalpagar;

	@EJB
	private PersonaFacade ejbPersonaFacade;
	@EJB
	private FacturaCabeceraFacade ejbFacturaCabeceraFacade;

	@EJB
	private FacturaDetalleFacade ejbFacturaDetalleFacade;
	
	@EJB
    private StockFacade ejbStockFacade;

	private Persona persona;
	private String cedula;
	private String mensaje;
	private String nombre;
	private String apellido;
	private String direccion;
	private String celular;
	private String correo;

	public DetalleBean() {
		this.fila = 1;
	}

	@PostConstruct
	public void init() {
		this.descuento = 0.0;
		this.quantity = "0";
		this.iva = 0.0;
		this.list = new HashSet<Row>();
		this.name = "";
		this.subtotalcabecera = 0.0;
		this.totalpagar = 0.0;
		
	}

	public void add() {		
		
		try {
			producto = ejbProductoFacade.buscarProducto(name);
			producto.setStock(this.consultarStockTotal(producto));
			System.out.println("NOMBRE >>> "+producto.getNombre());
			System.out.println("CANTIDAD >>> "+quantity);
			
			if ((producto.getStock() != 0) && Integer.valueOf(this.quantity) > 0) {
				this.id = producto.getCodigo();

				this.name = producto.getNombre();
				this.precio = producto.getPrecioVenta();
				this.subtotal = Integer.valueOf(this.quantity) * producto.getPrecioVenta();
				this.busqueda = "producto encontrado : stock : " + producto.getStock();
				this.list.add(new Row(id, producto, Integer.valueOf(this.quantity), precio, this.subtotal));
				this.subtotalcabecera = 0.0;
				
				for (Row p : list) {
					subtotalcabecera = subtotalcabecera + p.getSubtotal();
				}
				
				System.out.println("SUBTOTAL >>> "+subtotalcabecera);
				this.descuento = 0.00;
				this.iva = subtotalcabecera * 0.12;
				this.totalpagar = this.iva + subtotalcabecera;
				
			} else {
				this.busqueda = "producto no encontrado : sin stock : ";

			}
		} catch (Exception e) {
			this.busqueda = "producto no encontrado : sin stock : ";
		}
		
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public ProductoFacade getEjbProductoFacade() {
		return ejbProductoFacade;
	}

	public void setEjbProductoFacade(ProductoFacade ejbProductoFacade) {
		this.ejbProductoFacade = ejbProductoFacade;
	}

	public String edit(Row t) {
		// t.setEditable(true);
		return null;
	}

	public Row[] getList() {
		return list.toArray(new Row[0]);
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public void setList(Set<Row> list) {
		this.list = list;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getSubtotalcabecera() {
		return subtotalcabecera;
	}

	public void setSubtotalcabecera(Double subtotalcabecera) {
		this.subtotalcabecera = subtotalcabecera;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getTotalpagar() {
		return totalpagar;
	}

	public void setTotalpagar(double totalpagar) {
		this.totalpagar = totalpagar;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getCedula() {
		return cedula;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
    
	//Consultar el stock de un producto
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

	// metodo para presentar si se encuentra la persona
	public void mensaje() {
		persona = ejbPersonaFacade.find(this.cedula);
		
		try {
			if ("".equals(this.cedula) || !this.cedula.equals(persona.getCedula())) {
				this.mensaje = "no se encontro ningun usuario ";
			} else {
				this.mensaje = "usuario encontrado";
				this.nombre = persona.getNombre();
				this.apellido = persona.getApellido();
				this.celular = persona.getTelefono();
				this.direccion = persona.getDireccion();
			}
		} catch (Exception e) {
			this.mensaje = "no se encontro ningun usuario ";
		}
		
		
	}

	// metodo para registrar una persona a facturar
	public void crearPersona() {
		System.out.println("ha llegado a crear una persona");
		System.out.println(cedula + nombre + apellido + direccion + celular);
		Persona persona = new Persona(this.cedula, this.nombre, this.apellido, this.direccion, this.celular, "@@",
				"##@@!!", 'F');
		System.out.println("--->" + persona.toString());
		ejbPersonaFacade.create(persona);
		this.mensaje = "usuario registrado exitosamente";
	}
	
	//Disminuir stock por bodega
	public boolean disminuirStock(Row r) {
		
		int aux;
		int cant = r.getQuantity();
		Stock st_aux = new Stock();		
		boolean cent = false;
		List<Stock> stock_producto= ejbStockFacade.recuperarStockProducto(r.getProducto());
		System.out.println(">> INICIADO disminuirStock()");
		
		if (consultarStockTotal(r.getProducto())>=cant) {
			for (Stock s : stock_producto) {
				aux = cant;
				cant = s.getStock()-cant;			
				
				if (cant < 0) {
					st_aux = s;
					cant = cant*(-1);
					aux = aux-cant;
					s.setStock(s.getStock()-(aux));
					ejbStockFacade.edit(s);
					
				} else {
					s.setStock(cant);
					ejbStockFacade.edit(s);
					cent = true;
					break;
				}
			}
			
			if (cent==false) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> ERROR");
				ejbStockFacade.edit(st_aux);
			}
			
		} else {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> ERROR");
		}
		
		return cent;
		
	}

	public void crearFactura() {
		
		boolean cent=false;
		
		try {
			
			System.out.println("Esta es la persona a registar cedula :: " + this.cedula);
			GregorianCalendar c1 = (GregorianCalendar) GregorianCalendar.getInstance();
			FacturaCabecera facturaCabecera = new FacturaCabecera(c1, 'N', this.descuento, this.subtotalcabecera, this.iva,this.totalpagar, this.persona);

			for (Row p : list) {
				producto = p.getProducto();
				cent = disminuirStock(p);
				
				if (cent==false) {
					System.out.println(">> NO SE PUDO CAMBIAR EL STOCK");
					break;
				}
				
			}			
			
			if (cent==true) {
				
				ejbFacturaCabeceraFacade.create(facturaCabecera);
				
				for (Row p : list) {
					producto = p.getProducto();
					
					ejbFacturaDetalleFacade.create(new FacturaDetalle(p.getQuantity(), p.getSubtotal(), facturaCabecera, producto));
					
				}					
				
				System.out.println(">> FACTURA CREATA EXITOSAMENTE");
				this.init();
				this.mensaje = "se ha creado exitosamente la factura";				
				
			} else {
				this.mensaje = "no se pudo crear la factura";
				
			}
			
		} catch (Exception e) {
			this.mensaje = "no se pudo crear la factura";
		}
		
	}

	public String save(Row t) {
		t.setEditable(false);
		this.subtotalcabecera = 0.0;
		for (Row p : list) {
			subtotalcabecera = subtotalcabecera + p.getPrecio();
		}
		this.descuento = 0.00;
		this.iva = subtotalcabecera * 0.12;
		this.totalpagar = this.iva + subtotalcabecera;
		return null;
	}

	public String delete(Row t) {
		this.list.remove(t);
		this.subtotalcabecera = 0.0;
		for (Row p : list) {
			subtotalcabecera = subtotalcabecera + p.getSubtotal();
		}
		this.descuento = 0.00;
		this.iva = subtotalcabecera * 0.12;
		this.totalpagar = this.iva + subtotalcabecera;
		return null;
	}

	public String getCookie() {
		Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap()
				.get("empleado");
		System.out.println(cookie + "<-->" + cookie.getValue());
		if (cookie == null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../public/paginaCatalogo.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (cookie.getValue().isEmpty())
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../public/logIn.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "Bienvenido!";
	}

	public void redirectPedido() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/Practica_Laboratorio_03-EJB-JSF-JPA/private/paginaPedidos.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void anularFacturas() {
		try {
			
			
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("/Practica_Laboratorio_03-EJB-JSF-JPA/private/listarFacturas.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().addResponseCookie("empleado", "", null);
		Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap()
				.get("empleado");
		if (cookie.getValue().equals(""))
			System.out.println("Se ha nulificado la cookie de manera correcta!");
		else
			System.out.println("Se ha nulificado el valor correctamente!");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/Practica_Laboratorio_03-EJB-JSF-JPA/public/paginaCatalogo.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}