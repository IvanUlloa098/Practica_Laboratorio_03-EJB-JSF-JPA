package ec.edu.ups.resource;

import ec.edu.ups.ejb.PersonaFacade;
import ec.edu.ups.entidad.Persona;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/usuario/")
public class UsuarioResource {

    @EJB
    PersonaFacade personaFacade;

    @POST
    @Path("/login/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUsuario(@FormParam("cedula") String cedula, @FormParam("password") String password) throws IOException {

        if (personaFacade.verificarUsuario(cedula, password)){
            System.out.println("USUARIO EXISTENTE");
            return Response.ok("1").header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        }
    }

    @POST
    @Path("/register/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(@FormParam("cedula") String cedula, @FormParam("correo") String correo, @FormParam("password") String password){
        Persona persona = personaFacade.find(cedula);
        if(persona != null){
            persona.setCorreo(correo);
            persona.setPassword(password);
            try{
                personaFacade.edit(persona);
                return Response.status(Response.Status.ACCEPTED).entity("1")
                        .header("Access-Control-Allow-Origins", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                        .build();
            }catch (Exception e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear usuario!" + e.getCause())
                        .header("Access-Control-Allow-Origins", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                        .build();
            }
        }else{
            return Response.status(Response.Status.BAD_REQUEST).entity("2")
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        }
    }

    @PUT
    @Path("/update/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(@FormParam("cedula") String cedula, @FormParam("nombre") String nombre,
                           @FormParam("apellido") String apellido, @FormParam("direccion") String direccion, @FormParam("telefono") String telefono,
                           @FormParam("correo") String correo, @FormParam("password") String password){

        try{
            Persona persona = personaFacade.find(cedula);
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setDireccion(direccion);
            persona.setTelefono(telefono);
            persona.setCorreo(correo);
            persona.setPassword(password);
            personaFacade.edit(persona);
            return Response.ok("Se ha actualizado el usuario correctamente!")
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        }catch (Exception e){
            return Response.status(418).entity("No se ha podido actualizar el usuario" + e.getMessage())
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        }
    }

    @PUT
    @Path("/anular/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response anular(@FormParam("cedula") String cedula){
        try{
            Persona persona = personaFacade.find(cedula);
            persona.setAnulado('T');
            personaFacade.edit(persona);
            return Response.status(Response.Status.BAD_REQUEST).entity("Se ha anulado el usuario correctamente!")
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al anular la cuenta del usuario" + e.getCause())
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        }
    }


}
