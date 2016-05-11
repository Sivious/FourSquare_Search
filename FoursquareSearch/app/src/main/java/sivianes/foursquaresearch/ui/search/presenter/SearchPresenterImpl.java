package sivianes.foursquaresearch.ui.search.presenter;

import android.content.Context;

import sivianes.foursquaresearch.logic.connection.venues.GetVenuesFromLocation;
import sivianes.foursquaresearch.logic.connection.venues.GetVenuesFromLocationImpl;
import sivianes.foursquaresearch.logic.repository.RepositoryTokenStore;
import sivianes.foursquaresearch.logic.repository.RepositoryTokenStoreImpl;

/**
 * Created by Javier on 11/05/2016.
 */
public class SearchPresenterImpl implements SearchPresenter {
    GetVenuesFromLocation getVenuesFromLocation;
    RepositoryTokenStore repositoryTokenStore;
    private Context context;

    public SearchPresenterImpl(Context context) {
        this.context = context;
    }


    @Override
    public void searchFromLocation() {
        double exampleLatitude = 40.77180;
        double exampleLongitude = -73.98377;
        String accessToken = getAccessToken();

        getVenuesFromLocation = new GetVenuesFromLocationImpl();

        getVenuesFromLocation.execute(exampleLatitude, exampleLongitude, accessToken, new GetVenuesFromLocation.Callback() {
            @Override
            public void OnSuccess() {
                System.out.println("");
            }

            @Override
            public void OnFailure() {
                System.out.println("");
            }
        });

    }

    private String getAccessToken() {
        repositoryTokenStore = new RepositoryTokenStoreImpl(context);
        return repositoryTokenStore.getToken();
    }
}
