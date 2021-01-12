package ec.edu.ups.resource;

import ec.edu.ups.ejb.*;
import ec.edu.ups.entidad.*;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Path("/pedido/")
public class PedidoResource {

    @EJB
    PedidoFacade pedidoFacade;

    @EJB
    PersonaFacade personaFacade;
    @EJB
    ProductoFacade productoFacade;
    @EJB
    FacturaCabeceraFacade facturaCabeceraFacade;
    @EJB
    FacturaDetalleFacade facturaDetalleFacade;

    //SCORPION CODE START
    @POST
    @Path("crearpedido")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response crearPedido(@FormParam("producto_Id") String productoid,@FormParam("cedula_Id") String cedulaid,@FormParam("cantidad") String cantidad) throws Exception {
        System.out.println("producto "+ productoid);
        System.out.println("cedula "+ cedulaid);
        System.out.println("cantidad "+ cantidad);
        GregorianCalendar cal = getCurrentDate();
        Persona persona =personaFacade.searchPerson(cedulaid);
        int cant= Integer.parseInt(cantidad);
        try{
            System.out.println("PEDIDO EN PROCESO");
            Pedido pedido =pedidoFacade.getCurrentPedido(persona);
            FacturaCabecera facturacab= facturaCabeceraFacade.getPedidoFacturaCabecera(pedido);
            Producto producto = productoFacade.buscarProductoPorCodigo(productoid);
            double total_producto=producto.getPrecioVenta()*cant;
            FacturaDetalle facturaDetalle = new FacturaDetalle(cant,total_producto,facturacab,producto);
            facturaDetalleFacade.create(facturaDetalle);
        }catch (Exception e){
            System.out.println("IT's MY FIRST ORDER");
            Pedido ped = new Pedido("EN_PROCESO",cal,persona,null);
            FacturaCabecera facturaCabecera = new FacturaCabecera(cal, 'N', 0, 0, 0, 0, null, persona, ped);
            pedidoFacade.create(ped);
            ped.setFacturaCabecera(facturaCabecera);
            facturaCabeceraFacade.create(facturaCabecera);
            pedidoFacade.edit(ped);
            FacturaCabecera facturacab= facturaCabeceraFacade.getPedidoFacturaCabecera(ped);
            Producto producto = productoFacade.buscarProductoPorCodigo(productoid);
            double total_producto=producto.getPrecioVenta()*cant;
            FacturaDetalle facturaDetalle = new FacturaDetalle(cant,total_producto,facturacab,producto);
            facturaDetalleFacade.create(facturaDetalle);
        }

        return Response.ok("OK!" + productoid + " <--> " + cedulaid)
                .header("Access-Control-Allow-Origins", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .build();
    }


    @POST
    @Path("confirmpedido")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response confirmPedido(@FormParam("cedula_Id") String cedulaid) throws Exception {
        System.out.println("pedido a confirmar cedula "+ cedulaid);
        Persona persona= personaFacade.searchPerson(cedulaid);
        System.out.println("ped---");
        GregorianCalendar cal = getCurrentDate();
        System.out.println("ped--111");
        Pedido pedido = new Pedido("EN_PROCESO",cal,persona,null);
        FacturaCabecera facturaCabecera = new FacturaCabecera(cal, 'N', 0, 0, 0, 0, null, persona, pedido);
        pedidoFacade.create(pedido);
        pedido.setFacturaCabecera(facturaCabecera);
        facturaCabeceraFacade.create(facturaCabecera);
        pedidoFacade.edit(pedido);
        return Response.ok("OK!" + cedulaid + " <-->Factura CREADA Y PEDIDO NEW " )
                .header("Access-Control-Allow-Origins", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .build();

    }


    //SCORPION CODE ENDS

    @POST
    @Path("/create")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createPedido(@FormParam("personaId") String id, @FormParam("productos") String productos, @FormParam("cantidades") String cantidades) throws Exception{
        GregorianCalendar currentDate = getCurrentDate();
        Persona persona = personaFacade.find(id);
        Pedido pedido = new Pedido("ENVIADO", currentDate, persona, null);
        pedidoFacade.create(pedido);
        pedido = pedidoFacade.getUltimoPedido(persona, currentDate);

        String[] productosArray = productos.split(";");
        String[] cantidadesArray = cantidades.split(";");

        FacturaCabecera facturaCabecera = new FacturaCabecera(currentDate, 'N', 0, 0, 0, 0, null, persona, pedido);
        facturaCabeceraFacade.create(facturaCabecera);

        List<FacturaDetalle> detalleList = new ArrayList<>();

        for (int i = 0; i < productosArray.length; i++) {
            Producto producto = productoFacade.find(Integer.parseInt(productosArray[i]));
            producto.setStock(producto.getStock() - Integer.parseInt(cantidadesArray[i]));
            FacturaDetalle facturaDetalle = new FacturaDetalle(Integer.parseInt(cantidadesArray[i]),
                    (Integer.parseInt(cantidadesArray[i])*producto.getPrecioVenta()), facturaCabecera, producto);
            detalleList.add(facturaDetalle);
        }
        double [] totalSubtotalIva = getTotalSubtotalIva(detalleList);
        facturaCabecera.setListaFacturasDetalles(detalleList);
        facturaCabecera.setTotal(totalSubtotalIva[0]);
        facturaCabecera.setSubtotal(totalSubtotalIva[1]);
        facturaCabecera.setIva_total(totalSubtotalIva[2]);

        pedido.setFacturaCabecera(facturaCabecera);
        pedidoFacade.edit(pedido);

        return Response.ok("OK!" + id + " <--> " + productos)
                .header("Access-Control-Allow-Origins", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .build();
    }

    private double subtotal, iva_total;
    private double[] getTotalSubtotalIva(List<FacturaDetalle> detalleList){
        subtotal = 0;
        iva_total = 0;
        detalleList.forEach(facturaDetalle -> {
            Producto producto = facturaDetalle.getProducto();
            double precioVenta = producto.getPrecioVenta()*facturaDetalle.getCantidad();
            subtotal += (producto.getIva() == 'S') ? (precioVenta-precioVenta*0.12) : precioVenta;
            iva_total += (producto.getIva() == 'S') ? precioVenta*0.12 : 0;
        });
        return new double[]{subtotal+iva_total, subtotal, iva_total};
    }

    private GregorianCalendar getCurrentDate() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    @POST
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)

    public Response getPedidos(@FormParam("persona_id") String personaId) {

        Jsonb jsonb = JsonbBuilder.create();
        Persona persona = personaFacade.find(personaId);
        List<Pedido> pedidoList = (List<Pedido>) pedidoFacade.find(persona);

        try {
            List<Pedido> pedidos = Pedido.serializePedidos(pedidoList);
            return Response.ok(jsonb.toJson(pedidos))
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al obtener las bodegas ->" + e.getMessage()).build();
        }
    }

}
