package sivianes.foursquaresearch.ui.search.view;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import sivianes.foursquaresearch.R;
import sivianes.foursquaresearch.model.Venue;
import sivianes.foursquaresearch.ui.search.presenter.SearchPresenter;
import sivianes.foursquaresearch.ui.search.presenter.SearchPresenterImpl;

/**
 * Created by Javier on 10/05/2016.
 */
public class SearchActivity extends AppCompatActivity implements OnMapReadyCallback, SearchView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final float ZOOM_INITIAL = 12;
    private static final int PERMISSION_REQUEST_SET_UP_WIFI = 100;
    private EditText searchText;
    private Button searchButton;
    private SearchPresenter presenter;
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private Location lastKnownLocation;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        solveDependencies();
        init();
        initLocations();
        initMap();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    private void initLocations() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void initMap() {
        final SearchActivity view = this;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

                mapFragment.getMapAsync(view);
            }
        });
    }

    private void solveDependencies() {
        presenter = new SearchPresenterImpl(this.getApplicationContext());
        presenter.setView(this);
    }

    private void init() {
        searchText = (EditText) findViewById(R.id.search_text_venue);
        searchButton = (Button) findViewById(R.id.seatch_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchText.getText() != null || searchText.getText().length() != 0) {

                    presenter.searchByName(latitude, longitude, searchText.getText().toString());
                } else {
                    showMessage(getApplicationContext().getString(R.string.search_empty_string));
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }


    @Override
    public void paintVenues(final ArrayList<Venue> venues) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                googleMap.clear();
                if (venues != null && googleMap != null) {
                    for (Venue venue : venues) {
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(venue.location.getLatitude(), venue.location.getLongitude())).title(venue.name));
                    }
                }
            }
        });
    }

    @Override
    public void centerMapInLocation(final double latitude, final double longitude) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (googleMap != null) {
                    LatLng myLaLn = new LatLng(latitude, longitude);
                    CameraPosition camPos = new CameraPosition.Builder().target(myLaLn).zoom(ZOOM_INITIAL).bearing(0).tilt(0).build();
                    CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                    googleMap.animateCamera(camUpd3);

                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_SET_UP_WIFI);
            }
        }else{
            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastKnownLocation != null) {
                this.latitude = lastKnownLocation.getLatitude();
                this.longitude = lastKnownLocation.getLongitude();
            }
        }
    }

    @Override
    public void showMessage(String message){
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
