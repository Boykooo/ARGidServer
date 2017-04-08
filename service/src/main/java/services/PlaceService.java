package services;

import api.GoogleMapsApi;
import dto.PlaceDto;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PlaceService {

    @EJB
    private GoogleMapsApi api;

    public PlaceDto findPlaceByPlaceId(String placeId) throws Exception {
        JSONObject main = api.getDataByPlaceId(placeId);

        String response = main.getJSONObject("result").getString("name");

        return new PlaceDto(response);
    }

    public PlaceDto findPlaceByCrd(String location){
        return null;
    }


}
