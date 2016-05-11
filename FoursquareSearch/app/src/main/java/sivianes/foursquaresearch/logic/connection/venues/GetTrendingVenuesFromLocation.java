package sivianes.foursquaresearch.logic.connection.venues;

import java.util.ArrayList;

import sivianes.foursquaresearch.model.Venue;

/**
 * Created by Javier on 11/05/2016.
 */
public interface GetTrendingVenuesFromLocation {
    void execute(double latitude, double longitude, String accessToken, Callback callback);

    interface Callback{
        void OnSuccess(ArrayList<Venue> venues);
        void OnFailure();
    }
}
