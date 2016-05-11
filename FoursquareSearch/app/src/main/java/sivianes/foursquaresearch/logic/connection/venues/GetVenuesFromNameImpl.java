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
public class GetVenuesFromNameImpl implements GetVenuesFromName {

    DoGet doGet;

    @Override
    public void execute(String name, String accessToken, final Callback callback) {
        doGet = new DoGetImpl();

        final String url = "/venues/search?client_id=" + FoursquareConstants.CLIENT_ID +
                "&client_secret=" + FoursquareConstants.CLIENT_SECRET +
                "&intent=global" +
                "&query=" + name +
                "&v=" + FoursquareConstants.CLIENT_VERSION +
                "&m=" + FoursquareConstants.CLIENT_API;

        doGet.execute(url, "", new DoGet.Callback() {
            @Override
            public void OnSuccess(String response) {
                getVenues(response);
                callback.OnSuccess();
            }

            @Override
            public void OnFailure() {
                callback.OnFailure();
            }
        });
    }

    private void getVenues(String response) {
        JSONObject jsonObj = null;
        ArrayList<Venue> venueList = new ArrayList<Venue>();

        try {
            jsonObj = (JSONObject) new JSONTokener(response).nextValue();

            JSONArray groups = (JSONArray) jsonObj.getJSONObject("response").getJSONArray("groups");

            int length = groups.length();

            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    JSONObject group = (JSONObject) groups.get(i);
                    JSONArray items = (JSONArray) group.getJSONArray("items");

                    int ilength = items.length();

                    for (int j = 0; j < ilength; j++) {
                        JSONObject item = (JSONObject) items.get(j);

                        Venue venue = new Venue();

                        venue.id = item.getString("id");
                        venue.name = item.getString("name");

                        JSONObject location = (JSONObject) item.getJSONObject("location");

                        Location loc = new Location(LocationManager.GPS_PROVIDER);

                        loc.setLatitude(Double.valueOf(location.getString("lat")));
                        loc.setLongitude(Double.valueOf(location.getString("lng")));

                        venue.location = loc;
                        venue.address = location.getString("address");
                        venue.distance = location.getInt("distance");
                        venue.hereNow = item.getJSONObject("hereNow").getInt("count");
                        venue.type = group.getString("type");

                        venueList.add(venue);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
