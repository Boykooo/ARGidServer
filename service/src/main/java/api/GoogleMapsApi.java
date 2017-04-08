package api;

import org.json.JSONObject;

import javax.ejb.Stateless;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Scanner;

@Stateless
public class GoogleMapsApi {

    private String apiKey;
    private String baseRadarRequest;
    private String basePlaceInfoRequest;
    private Integer searchRadius;
    //private JsonParser parser;

    public GoogleMapsApi() {
        this.apiKey = "AIzaSyC3fnZHLCy56uTgIpOgkpSRSFzqzEO8GEM";
        this.baseRadarRequest = "https://maps.googleapis.com/maps/api/place/radarsearch/json?";
        this.basePlaceInfoRequest = "https://maps.googleapis.com/maps/api/place/details/json?";
        this.searchRadius = 15;
        //this.parser = new JsonParser();
    }

    public JSONObject getDataFromCrd(String lat, String lon) throws Exception {
        String request = MessageFormat.format(baseRadarRequest +
                "location={0},{1}&radius={2}&types=point_of_interest&key={3}", lat, lon, searchRadius, this.apiKey);
        return new JSONObject(getJsonFromUrl(request));
    }

    public JSONObject getDataByPlaceId(String placeId) throws Exception {
        String request = MessageFormat.format(basePlaceInfoRequest +
                "placeid={0}&language=ru&key={1}", placeId, this.apiKey);

        return new JSONObject(getJsonFromUrl(request));
    }

    public void changeSearchRadius(Integer searchRadius) {
        this.searchRadius = searchRadius;
    }

    private String getJsonFromUrl(String request) throws Exception {
        URL url = new URL(request);

        String encoding = System.getProperty("console.encoding", "utf-8"); // for russian chars

        Scanner scan = new Scanner(url.openStream(), encoding);
        StringBuilder sb = new StringBuilder();
        while (scan.hasNext()) {
            sb.append(scan.nextLine());
        }
        scan.close();

        return sb.toString();
    }
}



















