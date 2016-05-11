package sivianes.foursquaresearch.framework.foursquare.connection;

import android.content.Context;
import android.content.Intent;

import com.foursquare.android.nativeoauth.FoursquareOAuth;

import sivianes.foursquaresearch.framework.foursquare.constants.FoursquareConstants;

/**
 * Created by Javier on 10/05/2016.
 */
public class PerformFourSquareConnectionImpl implements PerformFourSquareConnection {
    private Context context;

    public PerformFourSquareConnectionImpl(Context context) {
        this.context = context;
    }

    @Override
    public void execute(Callback callback) {
        Intent intent = FoursquareOAuth.getConnectIntent(context, FoursquareConstants.CLIENT_ID);

        if (intent!= null){
            callback.OnSuccess(intent);
        }else{
            callback.OnFailure();
        }
    }
}
