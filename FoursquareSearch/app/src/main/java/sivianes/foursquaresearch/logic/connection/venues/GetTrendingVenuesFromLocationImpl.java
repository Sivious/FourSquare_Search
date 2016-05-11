package sivianes.foursquaresearch.logic.connection.venues;

import android.location.Location;
import android.location.LocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import sivianes.foursquaresearch.framework.foursquare.constants.FoursquareConstants;
import sivianes.foursquaresearch.logic.connection.callers.DoGet;
import sivianes.foursquaresearch.logic.connection.callers.DoGetImpl;
import sivianes.foursquaresearch.model.Venue;

/**
 * Created by Javier on 11/05/2016.
 */
public class GetTrendingVenuesFromLocationImpl implements GetTrendingVenuesFromLocation {
    DoGet doGet;

    @Override
    public void execute(double latitude, double longitude, String accessToken, final Callback callback) {
        doGet = new DoGetImpl();

        final String url = "/venues/trending?client_id=" + FoursquareConstants.CLIENT_ID +
                "&client_secret=" + FoursquareConstants.CLIENT_SECRET +
                "&ll=" + String.valueOf(latitude) + "," + String.valueOf(longitude) +
                //"&query=sushi" +
                "&v=" + FoursquareConstants.CLIENT_VERSION +
                "&m=" + FoursquareConstants.CLIENT_API;

        doGet.execute(url, "", new DoGet.Callback() {
            @Override
            public void OnSuccess(String response) {
                ArrayList<Venue> venuesArray = getVenues(response);

                if (venuesArray != null){
                    callback.OnSuccess(venuesArray);
                }else{
                    callback.OnFailure();
                }
            }

            @Override
            public void OnFailure() {
                callback.OnFailure();
            }
        });
    }

    private ArrayList<Venue> getVenues(String response) {
        JSONObject jsonObj = null;
        ArrayList<Venue> venuesArray = new ArrayList<Venue>();

        try {
            jsonObj = (JSONObject) new JSONTokener(response).nextValue();

            JSONArray venues = (JSONArray) jsonObj.getJSONObject("response").getJSONArray("venues");

            int length = venues.length();

            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    // I only take the first of the array
                    JSONObject jsonVenue = (JSONObject) venues.get(i);
                    Venue venue = new Venue();

                    venue.id = jsonVenue.getString("id");
                    venue.name = jsonVenue.getString("name");

                    JSONObject location = (JSONObject) jsonVenue.getJSONObject("location");

                    Location loc = new Location(LocationManager.GPS_PROVIDER);

                    loc.setLatitude(Double.valueOf(location.getString("lat")));
                    loc.setLongitude(Double.valueOf(location.getString("lng")));

                    venue.location = loc;

                    //TODO: Delete unused fields from class and here
                    //venue.address = location.getString("address");
                    //venue.distance = location.getInt("distance");
                    //venue.hereNow = jsonVenue.getJSONObject("hereNow").getInt("count");
                    //venue.type = jsonVenue.getString("type");

                    venuesArray.add(venue);
                }
            }

        } catch (JSONException e) {
            System.out.println("Hola");
            throw new RuntimeException(e);
        }

        return venuesArray;
    }
}
