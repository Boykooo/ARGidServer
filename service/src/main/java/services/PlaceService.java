package services;

import api.GoogleMapsApi;
import dto.PlaceDto;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PlaceService {

    @EJB
    private GoogleMapsApi api;

    private Double movementCoef;

    public PlaceDto findPlaceByPlaceId(String placeId) throws Exception {
        JSONObject main = api.getDataByPlaceId(placeId);

        String name = main.getJSONObject("result").getString("name");
        String address = main.getJSONObject("result").getString("formatted_address");
        String type = main.getJSONObject("result").getJSONArray("types").get(0).toString();
        Double lat = main.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        Double lng = main.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        return new PlaceDto(name, address, type, lat, lng);
    }

    public List<PlaceDto> findPlaceByCrd(String lat, String lng) throws Exception {
        JSONObject main = api.getDataFromCrd(lat, lng);
        List<PlaceDto> places = new ArrayList<>();

        if (main.getString("status").equals("OK"))
        {
            List<String> placeIDs = new ArrayList<>();
            JSONArray results = main.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                placeIDs.add(results.getJSONObject(i).getString("place_id"));
            }

            placeIDs.forEach(
                    (String id) -> {
                        try {
                            places.add(findPlaceByPlaceId(id));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
        }


        return places;
    }

}
