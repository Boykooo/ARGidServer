package services;

import api.GoogleMapsApi;
import dto.PlaceDto;
import org.json.JSONArray;
import org.json.JSONObject;
import util.ApiConst;

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

    public List<PlaceDto> findPlacesByCrd(String lat, String lng) throws Exception {
        return findPlacesByIds(findPlacesIdByCrd(lat, lng));
    }

    public List<PlaceDto> findPlacesByAzimuth(String lat, String lng, String azimuth) throws Exception {
        List<String> ids = new ArrayList<>();
        double newLat = Double.valueOf(lat);
        double newLng = Double.valueOf(lng);
        double azim = Double.valueOf(azimuth);
        int iter = 0; // количество проходов

        while ((ids == null || ids.size() == 0) && iter < 20) {

            double displacement = Math.cos(azim) * ApiConst.DISPLACEMENT_COEF;

            newLat = newLat + displacement;
            newLng = newLng + displacement;

            ids = findPlacesIdByCrd(String.valueOf(newLat), String.valueOf(newLng));

            iter++;
        }

        return findPlacesByIds(ids);
    }

    private List<String> findPlacesIdByCrd(String lat, String lng) throws Exception {
        JSONObject main = api.getDataFromCrd(lat, lng);
        if (main.getString("status").equals("OK")) {
            JSONArray results = main.getJSONArray("results");
            if (results.length() > 0) {
                List<String> placeIDs = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    placeIDs.add(results.getJSONObject(i).getString("place_id"));
                }
                return placeIDs;
            }
        }
        return null;
    }

    private List<PlaceDto> findPlacesByIds(List<String> ids) {
        if (ids != null) {
            List<PlaceDto> places = new ArrayList<>();

            ids.forEach(
                    (String id) -> {
                        try {
                            places.add(findPlaceByPlaceId(id));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
            return places;
        }
        return null;
    }
}
