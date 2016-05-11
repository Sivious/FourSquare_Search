package sivianes.foursquaresearch.logic.connection.venues;

/**
 * Created by Javier on 11/05/2016.
 */
public interface GetVenuesFromName {
    void execute(String name, String accessToken, Callback callback);

    interface Callback{
        void OnSuccess();
        void OnFailure();
    }
}
