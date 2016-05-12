package sivianes.foursquaresearch.framework.foursquare.responsehandler;

import android.content.Intent;

/**
 * Created by Javier on 10/05/2016.
 */
public interface HandlerFoursquareTokenResponse {
    void execute(int resultCode, Intent data, Callback callback);

    interface Callback{
        void OnSuccess(String token);
        void OnError();
    }
}
