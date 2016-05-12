package sivianes.foursquaresearch.logic.connection.venues;

import android.location.Location;
import android.location.LocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import sivianes.foursquaresearch.framework.foursquare.constants.FoursquareConstants;
import sivianes.foursquaresearch.logic.connection.callers.DoGet;
import sivianes.foursquaresearch.logic.connection.callers.DoGetImpl;
import sivianes.foursquaresearch.model.Venue;

/**
 * Created by Javier on 11/05/2016.
 */
public class GetVenuesFromNameImpl implements GetVenuesFromName {

    DoGet doGet;

    @Override
    public void execute(double latitude, double longitude, String name, String accessToken, final Callback callback) {
        doGet = new DoGetImpl();

        final String url = "/venues/search?client_id=" + FoursquareConstants.CLIENT_ID +
                "&client_secret=" + FoursquareConstants.CLIENT_SECRET +
                "&v=" + FoursquareConstants.CLIENT_VERSION +
                "&ll=" + String.valueOf(latitude) + "," + String.valueOf(longitude) +
                "&query=" + name;

        doGet.execute(url, "", new DoGet.Callback() {
            @Override
            public void OnSuccess(String response) {
                Venue venue = getVenues(response);

                if (venue != null) {
                    callback.OnSuccess(venue);
                } else {
                    callback.OnNoResults();
                }
            }

            @Override
            public void OnFailure() {
                callback.OnFailure();
            }
        });
    }

    private Venue getVenues(String response) {
        JSONObject jsonObj = null;
        Venue venue = new Venue();

        try {
            jsonObj = (JSONObject) new JSONTokener(response).nextValue();

            JSONArray venues = (JSONArray) jsonObj.getJSONObject("response").getJSONArray("venues");

            int length = venues.length();

            if (length > 0) {
                // I only take the first of the array
                JSONObject jsonVenue = (JSONObject) venues.get(0);

                venue.id = jsonVenue.getString("id");
                venue.name = jsonVenue.getString("name");

                JSONObject location = (JSONObject) jsonVenue.getJSONObject("location");

                Location loc = new Location(LocationManager.GPS_PROVIDER);

                loc.setLatitude(Double.valueOf(location.getString("lat")));
                loc.setLongitude(Double.valueOf(location.getString("lng")));

                venue.location = loc;

                return venue;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return venue;
        }
    }
}
