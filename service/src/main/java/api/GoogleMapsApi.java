package api;

import org.json.JSONObject;
import util.ApiConst;

import javax.ejb.Stateless;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Scanner;

@Stateless
public class GoogleMapsApi {

    private Integer searchRadius;

    public GoogleMapsApi() {
        searchRadius = 30;
    }

    public JSONObject getDataFromCrd(String lat, String lon) throws Exception {
        String request = MessageFormat.format(ApiConst.BASE_RADAR_REQUEST +
                "location={0},{1}&radius={2}&types=point_of_interest&key={3}", lat, lon, searchRadius, ApiConst.API_KEY);
        return new JSONObject(getJsonFromUrl(request));
    }

    public JSONObject getDataByPlaceId(String placeId) throws Exception {
        String request = MessageFormat.format(ApiConst.BASE_PLACE_INFO_REQUEST +
                "placeid={0}&language=ru&key={1}", placeId, ApiConst.API_KEY);

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



















