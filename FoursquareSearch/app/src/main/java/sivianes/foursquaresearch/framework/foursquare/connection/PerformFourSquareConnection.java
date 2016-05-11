package sivianes.foursquaresearch.framework.foursquare.connection;

import android.content.Intent;

/**
 * Created by Javier on 10/05/2016.
 */
public interface PerformFourSquareConnection {

    void execute(Callback callback);

    interface Callback{
        void OnSuccess (Intent intent);
        void OnFailure();
    }
}
