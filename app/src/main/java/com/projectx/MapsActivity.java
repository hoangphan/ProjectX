package com.projectx;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


public class MapsActivity extends FragmentActivity {
    /////////////////////////////////////////////////////////////////////////////
    //                                                                         //
    //              Attributes belong to GoogleMap                             //
    //                                                                         //
    /////////////////////////////////////////////////////////////////////////////

    PlaceAutocompleteFragment autocompleteFragment;



    private static final String TAG = "MainActivity";

    /////////////////////////////////////////////////////////////////////////////
    //                                                                         //
    //              Attributes belong to MapBox                                //
    //                                                                         //
    /////////////////////////////////////////////////////////////////////////////
    // Used for selecting the current place.
    private static final java.lang.String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiaG9hbmdwaGFuIiwiYSI6ImNqOHN2eXY0ZzBlYmczMnAwbTQyNXVhYWkifQ.BbvO9XZQoo0P8qptKXAcVw";
    private MapView mMapView;
    private MapboxMap mMapboxMap;
    private Place mSelectedPlace = null;
    private Button mPositionButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMapBoxInit(savedInstanceState);

        mGgSearchBoxInit();

    }

    private void mGgSearchBoxInit() {
        //Auto complete part
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        //Auto complete filter setting
        //Set filter only for Vietnam location
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("VN")
                .build();

        autocompleteFragment.setFilter(typeFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                mSelectedPlace = place;
                double latitude = place.getLatLng().latitude;
                double longitude = place.getLatLng().longitude;
                mMapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title("Selected place")
                );

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude)) // Sets the new camera position
                        .zoom(15) // Sets the zoom to level 10
                        .tilt(0) // Set the camera tilt to 20 degrees
                        .build(); // Builds the CameraPosition object from the builder

                //Move camera to new marker position
                mMapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    private void mMapBoxInit(Bundle savedInstanceState){
        Mapbox.getInstance(this.getApplicationContext(), MAPBOX_ACCESS_TOKEN);
        mMapView = (MapView) findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.
                mMapboxMap = mapboxMap;

            }
        });
    }

}
