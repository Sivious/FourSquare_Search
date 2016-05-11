package sivianes.foursquaresearch.framework.foursquare.responsehandler;

import android.content.Context;
import android.content.Intent;

import com.foursquare.android.nativeoauth.FoursquareCancelException;
import com.foursquare.android.nativeoauth.FoursquareDenyException;
import com.foursquare.android.nativeoauth.FoursquareInvalidRequestException;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.FoursquareOAuthException;
import com.foursquare.android.nativeoauth.FoursquareUnsupportedVersionException;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;

import sivianes.foursquaresearch.framework.foursquare.constants.FoursquareConstants;

/**
 * Created by Javier on 10/05/2016.
 */
public class HandleFoursquareConnectionResponseImpl implements HandleFoursquareConnectionResponse {
    private Context context;

    public HandleFoursquareConnectionResponseImpl(Context context) {
        this.context = context;
    }

    @Override
    public void execute(int resultCode, Intent data, Context context, Callback callback) {

        AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
        Exception exception = codeResponse.getException();

        if (exception == null) {
            // Success.
            String code = codeResponse.getCode();
            performTokenExchange(code, callback);

        } else {
            if (exception instanceof FoursquareCancelException) {
                // Cancel.
                // TODO: error to String.xml
                callback.OnCanceled("Canceled");

            } else if (exception instanceof FoursquareDenyException) {
                // Deny.
                // TODO: error to String.xml
                callback.OnDenied("Denied");

            } else if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = exception.getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();

                callback.OnError(errorMessage + " [" + errorCode + "]");

            } else if (exception instanceof FoursquareUnsupportedVersionException) {
                // Unsupported Fourquare app version on the device.
                callback.OnError(exception.toString());

            } else if (exception instanceof FoursquareInvalidRequestException) {
                // Invalid request.
                callback.OnError(exception.toString());

            } else {
                // Error.
                callback.OnError(exception.toString());
            }
        }
    }

    private void performTokenExchange(String code, Callback callback) {
        Intent intent = FoursquareOAuth.getTokenExchangeIntent(context, FoursquareConstants.CLIENT_ID, FoursquareConstants.CLIENT_SECRET, code);
        callback.OnSuccess(intent);
    }
}
