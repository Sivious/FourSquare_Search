package sivianes.foursquaresearch.ui.search.presenter;

import android.content.Context;

import sivianes.foursquaresearch.logic.connection.venues.GetVenuesFromName;
import sivianes.foursquaresearch.logic.connection.venues.GetVenuesFromNameImpl;
import sivianes.foursquaresearch.logic.repository.RepositoryTokenStore;
import sivianes.foursquaresearch.logic.repository.RepositoryTokenStoreImpl;

/**
 * Created by Javier on 11/05/2016.
 */
public class SearchPresenterImpl implements SearchPresenter {
    GetVenuesFromName getVenuesFromName;
    RepositoryTokenStore repositoryTokenStore;
    private Context context;

    public SearchPresenterImpl(Context context) {
        this.context = context;
    }


    @Override
    public void searchByName(String name) {
        String accessToken = getAccessToken();

        getVenuesFromName = new GetVenuesFromNameImpl();

        getVenuesFromName.execute(name, accessToken, new GetVenuesFromName.Callback() {
            @Override
            public void OnSuccess() {
                System.out.println("Success");
            }

            @Override
            public void OnFailure() {
                System.out.println("Failure");
            }
        });

    }

    private String getAccessToken() {
        repositoryTokenStore = new RepositoryTokenStoreImpl(context);
        return repositoryTokenStore.getToken();
    }
}
