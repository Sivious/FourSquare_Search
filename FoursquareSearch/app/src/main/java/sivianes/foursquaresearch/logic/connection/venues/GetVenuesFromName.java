package sivianes.foursquaresearch.logic.connection.venues;

import sivianes.foursquaresearch.model.Venue;

/**
 * Created by Javier on 11/05/2016.
 */
public interface GetVenuesFromName {
    void execute(double latitud, double longitud, String name, String accessToken, Callback callback);

    interface Callback{
        void OnSuccess(Venue venue);
        void OnFailure();
        void OnNoResults();
    }
}
