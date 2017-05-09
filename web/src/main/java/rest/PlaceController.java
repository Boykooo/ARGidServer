package rest;

import dto.PlaceDto;
import services.PlaceService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


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
    @Path("/static")
    public Response getAllByLocation(@QueryParam("lat") String lat, @QueryParam("lng") String lng) throws Exception {
        return Response.ok(service.findPlacesByCrd(lat, lng)).build();
    }

    @GET
    public Response getAllByAzimuth(@QueryParam("lat") String lat,
                                    @QueryParam("lng") String lng,
                                    @QueryParam("azimuth") String azimuth) throws Exception {
        List<PlaceDto> places = service.findPlacesByAzimuth(lat, lng, azimuth);
        int size = (places == null) ? 0 : places.size();
        log(lat, lng, azimuth, size);
        return Response.ok(places).build();
    }

    private void log(String lat, String lng, String azimuth, int sizeList){
        System.out.print("lat = " + lat);
        System.out.print("lng = " + lng);
        System.out.print("azimuth = " + azimuth);
        System.out.print("sizeList = " + sizeList);
    }
}