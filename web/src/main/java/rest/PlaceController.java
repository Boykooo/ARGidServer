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
    private PlaceService service;

    @GET
    @Path("/{placeid}")
    public Response getByPlaceId(@PathParam("placeid") String placeid) throws Exception {
        return Response.ok(service.findPlaceByPlaceId(placeid)).build();
    }

    @GET
    public Response getAllByLocation(@QueryParam("lat") String lat, @QueryParam("lng") String lng) throws Exception {
        return Response.ok(service.findPlaceByCrd(lat, lng)).build();
    }
}
