package sivianes.foursquaresearch.ui.search.view;

import java.util.ArrayList;

import sivianes.foursquaresearch.model.Venue;

/**
 * Created by Javier on 12/05/2016.
 */
public interface SearchView {

    void paintVenues(ArrayList<Venue> venues);
    void centerMapInLocation(double latitude, double longitude);
}
