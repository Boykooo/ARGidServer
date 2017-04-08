package rest;

import services.PlaceService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/places")
@Produces("text/json")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class PlaceController {

    @EJB
    private PlaceService api;

    @GET
    @Path("/{placeid}")
    public Response getByPlaceId(@PathParam("placeid") String placeid) throws Exception {
        return Response.ok(api.findPlaceByPlaceId(placeid)).build();
    }
}
