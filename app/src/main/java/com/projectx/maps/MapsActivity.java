package com.projectx.maps;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.services.android.location.LostLocationEngine;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.location.LocationEnginePriority;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;
import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.commons.models.Position;
import com.projectx.R;
import com.projectx.display.DisplayActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.projectx.utility.Constants.PLACE_LOCATION_A_ATT;
import static com.projectx.utility.Constants.PLACE_LOCATION_A_LAT;
import static com.projectx.utility.Constants.PLACE_LOCATION_A_LONG;
import static com.projectx.utility.Constants.PLACE_LOCATION_B_ATT;
import static com.projectx.utility.Constants.PLACE_LOCATION_B_LAT;
import static com.projectx.utility.Constants.PLACE_LOCATION_B_LONG;


public class MapsActivity extends FragmentActivity implements LocationEngineListener, PermissionsListener {
    /////////////////////////////////////////////////////////////////////////////
    //                                                                         //
    //              Attributes belong to GoogleMap                             //
    //                                                                         //
    /////////////////////////////////////////////////////////////////////////////

    PlaceAutocompleteFragment autocompleteFragment;
    private Place mSelectedPlace;

    /////////////////////////////////////////////////////////////////////////////
    //                                                                         //
    //              Attributes belong to MapBox                                //
    //                                                                         //
    /////////////////////////////////////////////////////////////////////////////
    // Used for selecting the current place.
    private MapView mMapView;
    private MapboxMap mMapboxMap;

    private PermissionsManager permissionsManager;
    private LocationLayerPlugin locationPlugin;
    private LocationEngine locationEngine;

    private LatLng userCoord;
    private Location userLocation;
    private Position userPosition;

    private LatLng destinationCoord;
    private Position destinationPosition;
    private Marker destinationMarker;

    private DirectionsRoute currentRoute;
    private NavigationMapRoute navigationMapRoute;

    /////////////////////////////////////////////////////////////////////////////
    //                                                                         //
    //              Common Attributes                                          //
    //                                                                         //
    /////////////////////////////////////////////////////////////////////////////

    private Button startNavButton;
    private static final String TAG_M = "MainActivity";
    private static final String TAG_R = "DirectionsActivity";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // BP: get instance of mapbox as early as possible
        Mapbox.getInstance(this, getString(R.string.access_token));

        // for UI
        setContentView(R.layout.activity_maps);
        navButton_Init();

        // google search box
        mGgSearchBoxInit();

        // mapbox initialization
        mMapBoxInit(savedInstanceState);
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
                Log.i(TAG_M, "Place: " + place.getName());
                mSelectedPlace = place;
                double latitude = place.getLatLng().latitude;
                double longitude = place.getLatLng().longitude;

                if (destinationMarker != null) {
                    mMapboxMap.removeMarker(destinationMarker);
                }

                destinationMarker = mMapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(place.getName().toString())
                );

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude)) // Sets the new camera position
                        .zoom(15) // Sets the zoom to level 10
                        .tilt(0) // Set the camera tilt to 20 degrees
                        .build(); // Builds the CameraPosition object from the builder

                //Move camera to new marker position
                mMapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

                // enable start navigation button
                // could get route and navigate from current position to the selected place
                destinationPosition = Position.fromCoordinates(longitude, latitude);
                userPosition = Position.fromCoordinates(userCoord.getLongitude(), userCoord.getLatitude());
                getRoute(userPosition, destinationPosition);
                startNavButton.setEnabled(true);
                startNavButton.setBackgroundResource(R.color.mapbox_blue);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG_M, "An error occurred: " + status);
            }
        });
    }

    private void mMapBoxInit(Bundle savedInstanceState) {

        mMapView = (MapView) findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.
                mMapboxMap = mapboxMap;

                enableLocationPlugin();

                mMapboxMap.setOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng point) {

                        if (destinationMarker != null) {
                            mMapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = point;

                        destinationMarker = mMapboxMap.addMarker(new MarkerOptions()
                                .position(destinationCoord).title("hello there"));

                        destinationPosition = Position.fromCoordinates(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        userPosition = Position.fromCoordinates(userCoord.getLongitude(), userCoord.getLatitude());
                        getRoute(userPosition, destinationPosition);
                        startNavButton.setEnabled(true);
                        startNavButton.setBackgroundResource(R.color.mapbox_blue);
                    };
                });
            }
        });

    }

    private void navButton_Init() {
        startNavButton = (Button) findViewById(R.id.startNav);
        startNavButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Position origin = userPosition;
                Position destination = destinationPosition;

                // Pass in your Amazon Polly pool id for speech synthesis using Amazon Polly
                // Set to null to use the default Android speech synthesizer
                String awsPoolId = null;

                boolean simulateRoute = false;

                // Call this method with Context from within an Activity
                //NavigationLauncher.startNavigation(MapsActivity.this, origin, destination,
                //        awsPoolId, simulateRoute);
                startDisplaying();

            }
        });
    }

    private void startDisplaying() {
        Intent display = new Intent(this, DisplayActivity.class);
        display.putExtra(PLACE_LOCATION_A_LONG, userPosition.getLongitude());
        display.putExtra(PLACE_LOCATION_A_LAT, userPosition.getLongitude());
        display.putExtra(PLACE_LOCATION_A_ATT, userPosition.getAltitude());

        display.putExtra(PLACE_LOCATION_B_LONG, destinationPosition.getLongitude());
        display.putExtra(PLACE_LOCATION_B_LAT, destinationPosition.getLongitude());
        display.putExtra(PLACE_LOCATION_B_ATT, destinationPosition.getAltitude());
        startActivity(display);
    }

    private void getRoute(Position origin, Position destination) {
        NavigationRoute.builder()
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG_R, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG_R, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().getRoutes().size() < 1) {
                            Log.e(TAG_R, "No routes found");
                            return;
                        }

                        currentRoute = response.body().getRoutes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mMapView, mMapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG_R, "Error: " + throwable.getMessage());
                    }
                });
    }

    private void activateLocationEngine() {
        locationEngine = new LostLocationEngine(MapsActivity.this);
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();
    }

    @SuppressWarnings( {"MissingPermission"})
    private void initializeUserLocation()
    {
        // Create an instance of LOST location engine// open source location engine activation
        // this is only allowed, when permission is granted to instantiate the location engine
        activateLocationEngine();

        // then now initialize user location
        getUserLocation();

        // to display the user location into the map
        locationPlugin = new LocationLayerPlugin(mMapView, mMapboxMap, locationEngine);
        locationPlugin.setLocationLayerEnabled(LocationLayerMode.TRACKING);
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationPlugin() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeUserLocation();
        } else {
            
            // request location permission
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void getUserLocation() {
        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            userLocation = lastLocation;
            setCameraPosition(userLocation);
        } else {
            locationEngine.addLocationEngineListener(this);
        }
        userCoord = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
    }

    private void setCameraPosition(Location location) {
        mMapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 13));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            initializeUserLocation();
        } else {
            finish();
            Log.i(TAG_M, "Come on, please give me the permission!");
        }
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            userLocation = location;
            setCameraPosition(location);
            locationEngine.removeLocationEngineListener(this);
        }
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        if (locationEngine != null) {
            locationEngine.requestLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStart();
        }
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStop();
        }
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (locationEngine != null) {
            locationEngine.deactivate();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }



}
