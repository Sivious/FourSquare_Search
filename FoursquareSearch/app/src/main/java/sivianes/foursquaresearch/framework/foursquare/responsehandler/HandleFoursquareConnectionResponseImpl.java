package sivianes.foursquaresearch.framework.foursquare.responsehandler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
    private static final String TAG = "HandleConnectionRespons";
    private Context context;

    public HandleFoursquareConnectionResponseImpl(Context context) {
        this.context = context;
    }

    @Override
    public void execute(int resultCode, Intent data, Context context, Callback callback) {

        AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
        Exception exception = codeResponse.getException();

        if (exception == null) {
            String code = codeResponse.getCode();
            performTokenExchange(code, callback);

        } else {
            if (exception instanceof FoursquareCancelException) {
                callback.OnCanceled();

            } else if (exception instanceof FoursquareDenyException) {
                callback.OnDenied();

            } else if (exception instanceof FoursquareOAuthException) {
                String errorMessage = exception.getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                Log.i(TAG, errorMessage + " [" + errorCode + "]");

                callback.OnError();

            } else if (exception instanceof FoursquareUnsupportedVersionException) {
                Log.i(TAG, exception.toString());
                callback.OnError();

            } else if (exception instanceof FoursquareInvalidRequestException) {
                Log.i(TAG, exception.toString());
                callback.OnError();

            } else {
                Log.i(TAG, exception.toString());
                callback.OnError();
            }
        }
    }

    private void performTokenExchange(String code, Callback callback) {
        Intent intent = FoursquareOAuth.getTokenExchangeIntent(context, FoursquareConstants.CLIENT_ID, FoursquareConstants.CLIENT_SECRET, code);
        callback.OnSuccess(intent);
    }
}
