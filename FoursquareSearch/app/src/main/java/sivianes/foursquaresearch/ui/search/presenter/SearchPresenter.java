package sivianes.foursquaresearch.ui.search.presenter;

import sivianes.foursquaresearch.ui.search.view.SearchActivity;

/**
 * Created by Javier on 11/05/2016.
 */
public interface SearchPresenter {
    void setView(SearchActivity view);
    void searchByName(double latitude, double longitude, String name);
}
