package sivianes.foursquaresearch.logic.connection.venues;

import sivianes.foursquaresearch.framework.foursquare.constants.FoursquareConstants;
import sivianes.foursquaresearch.framework.network.HTTPRequest;
import sivianes.foursquaresearch.framework.network.HTTPRequestImpl;

/**
 * Created by Javier on 11/05/2016.
 */
public class GetVenuesFromLocationImpl implements GetVenuesFromLocation {
    HTTPRequest httpRequest;

    @Override
    public void execute(double latitude, double longitude, String accessToken, Callback callback) {
        httpRequest = new HTTPRequestImpl();

        String ll = String.valueOf(latitude) + "," + String.valueOf(longitude);

        final String url = "/venues/search?client_id=" + FoursquareConstants.CLIENT_ID +
                "&client_secret=" + FoursquareConstants.CLIENT_SECRET +
                "&ll=" + String.valueOf(latitude) +"," + String.valueOf(longitude) +
                "&query=sushi" +
                "&v="+ FoursquareConstants.CLIENT_VERSION +
                "&m=" + FoursquareConstants.CLIENT_API;

        new Thread(new Runnable() {
            public void run() {
                requestGet(url);
            }
        }).start();
    }

    private void requestGet(String url) {
        httpRequest.get(url, "", new HTTPRequest.Callback() {
            @Override
            public void onFailureServiceWithError() {

            }

            @Override
            public void onInvalidFieldValues() {

            }

            @Override
            public void onSuccessWebService(String response) {
                //TODO: Delete this and correct the callback
                System.out.print(response.length());
            }

            @Override
            public void onNullResponse() {

            }

            @Override
            public void onNotModified() {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onAlreadyExists() {

            }
        });
    }
}
