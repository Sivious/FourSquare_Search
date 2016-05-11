package sivianes.foursquaresearch.logic.connection.callers;

/**
 * Created by Javier on 11/05/2016.
 */
public interface DoGet {

    void execute(String url, String params, Callback callback);

    interface Callback {
        void OnSuccess(String response);

        void OnFailure();
    }
}
