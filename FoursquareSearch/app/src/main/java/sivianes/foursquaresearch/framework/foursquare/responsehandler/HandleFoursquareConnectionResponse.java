package sivianes.foursquaresearch.framework.foursquare.responsehandler;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Javier on 10/05/2016.
 */
public interface HandleFoursquareConnectionResponse {

    void execute(int resultCode, Intent data, Context context, Callback callback);

    interface Callback{
        void OnSuccess(Intent intent);
        void OnCanceled();
        void OnDenied();
        void OnError();
    }
}
