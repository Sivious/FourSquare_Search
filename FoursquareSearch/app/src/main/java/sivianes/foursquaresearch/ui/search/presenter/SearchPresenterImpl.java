package sivianes.foursquaresearch.ui.search.presenter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import sivianes.foursquaresearch.logic.connection.venues.GetTrendingVenuesFromLocation;
import sivianes.foursquaresearch.logic.connection.venues.GetTrendingVenuesFromLocationImpl;
import sivianes.foursquaresearch.logic.connection.venues.GetVenuesFromName;
import sivianes.foursquaresearch.logic.connection.venues.GetVenuesFromNameImpl;
import sivianes.foursquaresearch.logic.repository.RepositoryTokenStore;
import sivianes.foursquaresearch.logic.repository.RepositoryTokenStoreImpl;
import sivianes.foursquaresearch.model.Venue;

/**
 * Created by Javier on 11/05/2016.
 */
public class SearchPresenterImpl implements SearchPresenter {
    GetVenuesFromName getVenuesFromName;
    GetTrendingVenuesFromLocation getTrendingVenuesFromLocation;
    RepositoryTokenStore repositoryTokenStore;
    private Context context;
    private double latitude = 40.7718;
    private double longitude = -73.98377;

    public SearchPresenterImpl(Context context) {
        this.context = context;
    }


    @Override
    public void searchByName(String name) {
        String accessToken = getAccessToken();

        getVenuesFromName = new GetVenuesFromNameImpl();

        getVenuesFromName.execute(latitude, longitude, name, accessToken, new GetVenuesFromName.Callback() {
            @Override
            public void OnSuccess(Venue venue) {
                System.out.println("Success");
                searchVenuesFromLocation(venue);
            }

            @Override
            public void OnFailure() {
                System.out.println("Failure");
            }
        });

    }

    private void searchVenuesFromLocation(final Venue venue) {
        getTrendingVenuesFromLocation = new GetTrendingVenuesFromLocationImpl();

        String accessToken = getAccessToken();

        getTrendingVenuesFromLocation.execute(venue.location.getLatitude(), venue.location.getLongitude(), accessToken, new GetTrendingVenuesFromLocation.Callback() {
            @Override
            public void OnSuccess(ArrayList<Venue> venues) {
                venues.add(venue);
                //view.paintVenues(venues);
            }

            @Override
            public void OnFailure() {

            }
        });
    }

    private String getAccessToken() {
        repositoryTokenStore = new RepositoryTokenStoreImpl(context);
        return repositoryTokenStore.getToken();
    }
}
