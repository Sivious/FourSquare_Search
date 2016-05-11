package sivianes.foursquaresearch.framework.network;

import java.io.Serializable;

/**
 * Created by Javier on 10/05/2016.
 */
public interface HTTPRequest {

    void get(String url, String params, Callback responseListener);

    interface Callback extends Serializable {
        void onFailureServiceWithError();

        void onSuccessWebService(String response);

        void onNullResponse();
    }
}
