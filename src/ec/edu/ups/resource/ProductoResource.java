package ec.edu.ups.resource;

import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.entidad.Producto;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/producto/")
public class ProductoResource {

    @EJB
    ProductoFacade productoFacade;

    @POST
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getProductos(@FormParam("bodegaId") Integer bodegaId, @FormParam("categoriaId") Integer categoriaId){
        Jsonb jsonb = JsonbBuilder.create();

        List<Producto> productoList = productoFacade.getProductos(bodegaId, categoriaId);
        System.out.println(productoList);
        if(!productoList.isEmpty())
            return Response.ok(jsonb.toJson(productoList))
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        else
            return Response.status(Response.Status.BAD_REQUEST).entity("No se ha obtenido ningun resultado!").build();
    }

}
