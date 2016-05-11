package sivianes.foursquaresearch.framework.foursquare.responsehandler;

import android.content.Intent;

import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.FoursquareOAuthException;
import com.foursquare.android.nativeoauth.model.AccessTokenResponse;

/**
 * Created by Javier on 10/05/2016.
 */
public class HandlerFoursquareTokenResponseImpl implements HandlerFoursquareTokenResponse {
    @Override
    public void execute(int resultCode, Intent data, Callback callback) {
        AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(resultCode, data);
        Exception exception = tokenResponse.getException();

        if (exception == null) {
            String accessToken = tokenResponse.getAccessToken();
            callback.OnSuccess(accessToken);

        } else {
            if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = ((FoursquareOAuthException) exception).getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                callback.OnError(errorMessage + " [" + errorCode + "]");

            } else {
                callback.OnError("OAuth error");
            }
        }
    }
}
