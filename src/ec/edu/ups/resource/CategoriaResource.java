package ec.edu.ups.resource;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.CategoriaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.entidad.Categoria;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/categoria/")
public class CategoriaResource {

    @EJB
    ProductoFacade productoFacade;

    @POST
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getCategorias(@FormParam("bodega_id") Integer bodegaId){
        Jsonb jsonb = JsonbBuilder.create();
        try {
            List<Categoria> categorias = productoFacade.getCategorias(bodegaId);
            if(!categorias.isEmpty())
                return Response.status(Response.Status.ACCEPTED).entity(jsonb.toJson(categorias))
                        .header("Access-Control-Allow-Origins", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                        .build();
            else
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se ha encontrado ninguna categoria!").build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR AL OBTENER LAS CATEGORIAS!").build();
        }
    }

}












