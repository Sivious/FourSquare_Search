package sivianes.foursquaresearch.logic.connection.venues;

import sivianes.foursquaresearch.framework.foursquare.constants.FoursquareConstants;
import sivianes.foursquaresearch.logic.connection.callers.DoGet;
import sivianes.foursquaresearch.logic.connection.callers.DoGetImpl;

/**
 * Created by Javier on 11/05/2016.
 */
public class GetVenuesFromNameImpl implements GetVenuesFromName {

    DoGet doGet;

    @Override
    public void execute(String name, String accessToken, final Callback callback) {
        doGet = new DoGetImpl();

        final String url = "/venues/search?client_id=" + FoursquareConstants.CLIENT_ID +
                "&client_secret=" + FoursquareConstants.CLIENT_SECRET +
                "&intent=global" +
                "&query=" + name +
                "&v=" + FoursquareConstants.CLIENT_VERSION +
                "&m=" + FoursquareConstants.CLIENT_API;

        doGet.execute(url, "", new DoGet.Callback() {
            @Override
            public void OnSuccess() {
                callback.OnSuccess();
            }

            @Override
            public void OnFailure() {
                callback.OnFailure();
            }
        });
    }
}
