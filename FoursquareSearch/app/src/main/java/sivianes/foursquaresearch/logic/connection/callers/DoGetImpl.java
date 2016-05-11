package sivianes.foursquaresearch.logic.connection.callers;

import sivianes.foursquaresearch.framework.network.HTTPRequest;
import sivianes.foursquaresearch.framework.network.HTTPRequestImpl;

/**
 * Created by Javier on 11/05/2016.
 */
public class DoGetImpl implements DoGet {
    private HTTPRequest httpRequest;

    public DoGetImpl() {
        this.httpRequest = new HTTPRequestImpl();
    }

    @Override
    public void execute(final String url, final String params, final Callback callback) {
        new Thread(new Runnable() {
            public void run() {
                httpRequest.get(url, params, new HTTPRequest.Callback() {
                    @Override
                    public void onFailureServiceWithError() {
                        callback.OnFailure();
                    }

                    @Override
                    public void onSuccessWebService(String response) {
                        callback.OnSuccess(response);
                    }

                    @Override
                    public void onNullResponse() {
                        callback.OnFailure();
                    }
                });
            }
        }).start();
    }
}
