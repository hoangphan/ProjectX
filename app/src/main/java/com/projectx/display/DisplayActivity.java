package com.projectx.display;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;


import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.location.LostLocationEngine;
import com.mapbox.services.android.navigation.v5.milestone.MilestoneEventListener;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.api.directions.v5.models.LegStep;
import com.mapbox.services.api.directions.v5.models.StepManeuver;
import com.mapbox.services.api.utils.turf.TurfConstants;
import com.mapbox.services.api.utils.turf.TurfHelpers;
import com.mapbox.services.commons.models.Position;
import com.projectx.R;
import com.projectx.utility.Constants;
import com.projectx.utility.ManeuverMap;
import com.projectx.utility.VibrationMap;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.services.android.telemetry.location.LocationEnginePriority.HIGH_ACCURACY;
import static com.projectx.utility.Constants.MAPBOX_ACCESS_TOKEN;
import static com.projectx.utility.Constants.MPH_DOUBLE;
import static com.projectx.utility.Constants.PLACE_LOCATION_B_LAT;
import static com.projectx.utility.Constants.PLACE_LOCATION_B_LONG;

public class DisplayActivity extends AppCompatActivity implements LocationEngineListener,
  ProgressChangeListener, MilestoneEventListener, TextToSpeech.OnInitListener {

  private static final String TAG = DisplayActivity.class.getSimpleName();
  private static final String ARRIVAL_STRING_FORMAT = "%tl:%tM %tp%n";
  private static final String DECIMAL_FORMAT = "###.#";
  private static final String MILES_STRING_FORMAT = "%s miles";
  private static final String FEET_STRING_FORMAT = "%s feet";

  @BindView(R.id.stepText)
  TextView stepText;
  @BindView(R.id.mphText)
  TextView mphText;
  @BindView(R.id.distanceText)
  TextView stepDistanceText;
  @BindView(R.id.distanceRemainingText)
  TextView routeDistanceText;
  @BindView(R.id.timeRemainingText)
  TextView timeRemainingText;
  @BindView(R.id.arrivalText)
  TextView arrivalText;
  @BindView(R.id.maneuverImage)
  ImageView maneuverImage;
  @BindView(R.id.stepProgressBar)
  ProgressBar stepProgressBar;

  private Position currentUserPosition;
  private MapboxNavigation navigation;
  private LocationEngine locationEngine;
  private boolean mirroring;
//  private TextToSpeech tts;

  private DirectionsRoute mRoute;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display);
    ButterKnife.bind(this);
      //Flag for setSystemUiVisibility(int): View has requested that the system navigation be temporarily hidden.
      hideNavigationFullscreen();

    //tts = new TextToSpeech(this, this);



    activateLocationEngine();
    initMapboxNavigation();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    navigation.onDestroy();
    deactivateLocationEngine();
  }

  @OnClick(R.id.fabMirrorView)
  public void onMirrorClick() {
    View contentView = findViewById(android.R.id.content);
    if (mirroring) {
      contentView.setScaleY(1);
      mirroring = false;
    } else {
      contentView.setScaleY(-1);
      mirroring = true;
    }
    hideNavigationFullscreen();
  }

  @SuppressWarnings({"MissingPermission"})
  @Override
  public void onConnected() {
    locationEngine.requestLocationUpdates();

    if (locationEngine.getLastLocation() != null) {
      Location lastLocation = locationEngine.getLastLocation();
      currentUserPosition = Position.fromLngLat(lastLocation.getLongitude(), lastLocation.getLatitude());
      checkIntentExtras();
    }
  }

  @Override
  public void onLocationChanged(Location location) {
    currentUserPosition = Position.fromLngLat(location.getLongitude(), location.getLatitude());
    calculateMph(location);
  }

  @Override
  public void onProgressChange(Location location, RouteProgress routeProgress) {
    Log.d(TAG, "onProgressChange called");
    updateUi(routeProgress);
  }

  @Override
  public void onMilestoneEvent(RouteProgress routeProgress, String instruction, int identifier) {
    //tts.speak(instruction, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  @Override
  public void onInit(int status) {
    //tts.setLanguage(Locale.getDefault());
  }

  private void hideNavigationFullscreen() {
    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
      | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }

  private void activateLocationEngine() {
    locationEngine = LostLocationEngine.getLocationEngine(this);
    locationEngine.setPriority(HIGH_ACCURACY);
    locationEngine.setInterval(0);
    locationEngine.setFastestInterval(1000);
    locationEngine.addLocationEngineListener(this);
    locationEngine.activate();
  }

  private void deactivateLocationEngine() {
    locationEngine.removeLocationUpdates();
    locationEngine.removeLocationEngineListener(this);
    locationEngine.deactivate();
  }

  private void initMapboxNavigation() {
    navigation = new MapboxNavigation(this, MAPBOX_ACCESS_TOKEN);
    navigation.addProgressChangeListener(this);
    navigation.addMilestoneEventListener(this);
  }

  private void checkIntentExtras() {
//      if (getIntent().hasExtra(PLACE_LOCATION_EXTRA)) {
          double tLong = getIntent().getDoubleExtra(PLACE_LOCATION_B_LONG, 0.0);
          double tLat = getIntent().getDoubleExtra(PLACE_LOCATION_B_LAT,0.0);
          Position destination = Position.fromLngLat(tLong, tLat);

          if (currentUserPosition != null) {
              getRoute(currentUserPosition, destination);
          } else {
              Toast.makeText(this, "Current Location is null", Toast.LENGTH_LONG).show();
          }
//      }
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
                        if (response.body() == null) {
                            return;
                        } else if (response.body().getRoutes().size() < 1) {
                            return;
                        }
                        mRoute = response.body().getRoutes().get(0);
                        navigation.setLocationEngine(locationEngine);
                        navigation.startNavigation(mRoute);
                    }
                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

  private void calculateMph(Location location) {
    if (location.hasSpeed()) {
      int speed = (int) (location.getSpeed() * MPH_DOUBLE);
      mphText.setText(String.valueOf(speed));
    } else {
      mphText.setText(String.valueOf(0));
    }
  }

  private void updateUi(RouteProgress progress) {
    extractLegStep(progress);
    stepDistanceText.setText(formatStepDistanceRemaining(progress.currentLegProgress()
      .currentStepProgress().distanceRemaining()));
    routeDistanceText.setText(formatRouteDistanceRemaining(progress.distanceRemaining()));
    timeRemainingText.setText(formatTimeRemaining(progress.durationRemaining()));
    arrivalText.setText(formatArrivalTime(progress.durationRemaining()));
    setStepProgressBar(progress.currentLegProgress().currentStepProgress().fractionTraveled());
  }

  private void extractLegStep(RouteProgress progress) {
    LegStep upComingStep = progress.currentLegProgress().upComingStep();
    if (upComingStep != null) {
      maneuverImage.setImageResource(obtainManeuverResource(upComingStep));
      // Get instance of Vibrator from current Context
      Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
      // Vibrate for 400 milliseconds
      if( progress.currentLegProgress().durationRemaining() < 5)
      {
        v.vibrate(Constants.VIB_REACH_STEP, 0);
      }
      else {
        v.vibrate(obtainVibrationResource(upComingStep), 0);
      }
      if (upComingStep.getManeuver() != null) {
        if (!TextUtils.isEmpty(upComingStep.getName())) {
          stepText.setText(upComingStep.getName());
        } else if (!TextUtils.isEmpty(upComingStep.getManeuver().getInstruction())) {
          stepText.setText(upComingStep.getManeuver().getInstruction());
        }
      }
    }
  }

  private static int obtainManeuverResource(LegStep step) {
    ManeuverMap maneuverMap = new ManeuverMap();
    if (step != null && step.getManeuver() != null) {
      StepManeuver maneuver = step.getManeuver();
      if (!TextUtils.isEmpty(maneuver.getModifier())) {
        return maneuverMap.getManeuverResource(maneuver.getType() + maneuver.getModifier());
      } else {
        return maneuverMap.getManeuverResource(maneuver.getType());
      }
    }
    return R.drawable.maneuver_starting;
  }

  private static long[] obtainVibrationResource(LegStep step) {
    VibrationMap vibrationMap = new VibrationMap();
    if (step != null && step.getManeuver() != null) {
      StepManeuver maneuver = step.getManeuver();
      if (!TextUtils.isEmpty(maneuver.getModifier())) {
        return vibrationMap.getVibrationResource(maneuver.getType() + maneuver.getModifier());
      } else {
        return vibrationMap.getVibrationResource(maneuver.getType());
      }
    }
    return Constants.VIB_NOTHING;
  }

  private static String formatTimeRemaining(double routeDurationRemaining) {
    long seconds = (long) routeDurationRemaining;

    if (seconds < 0) {
      throw new IllegalArgumentException(Constants.DURATION_ILLEGAL_ARGUMENT);
    }

    long days = TimeUnit.SECONDS.toDays(seconds);
    seconds -= TimeUnit.DAYS.toSeconds(days);
    long hours = TimeUnit.SECONDS.toHours(seconds);
    seconds -= TimeUnit.HOURS.toSeconds(hours);
    long minutes = TimeUnit.SECONDS.toMinutes(seconds);
    seconds -= TimeUnit.MINUTES.toSeconds(minutes);
    long sec = TimeUnit.SECONDS.toSeconds(seconds);

    if (seconds >= 30) {
      minutes = minutes + 1;
    }

    StringBuilder sb = new StringBuilder(Constants.STRING_BUILDER_CAPACITY);
    if (days != 0) {
      sb.append(days);
      sb.append(Constants.DAYS);
    }
    if (hours != 0) {
      sb.append(hours);
      sb.append(Constants.HOUR);
    }
    if (minutes != 0) {
      sb.append(minutes);
      sb.append(Constants.MINUTE);
    }
    if (days == 0 && hours == 0 && minutes == 0) {
      sb.append(sec);
      sb.append(Constants.SECONDS);
    }

    return ("Time: " + sb.toString());
  }

  public static String formatArrivalTime(double routeDurationRemaining) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.SECOND, (int) routeDurationRemaining);

    return "Arrival: " + String.format(Locale.US, ARRIVAL_STRING_FORMAT,
      calendar, calendar, calendar);
  }

  private static String formatRouteDistanceRemaining(double routeDistanceRemaining) {
    double miles = routeDistanceRemaining * Constants.METER_MULTIPLIER;
    DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
    miles = Double.valueOf(df.format(miles));

    return ("Distance: " + miles + Constants.MILES);
  }

  private static String formatStepDistanceRemaining(double distance) {
    String formattedString;
    if (TurfHelpers.convertDistance(distance, TurfConstants.UNIT_METERS, TurfConstants.UNIT_FEET) > 1099) {
      distance = TurfHelpers.convertDistance(distance, TurfConstants.UNIT_METERS, TurfConstants.UNIT_MILES);
      DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
      double roundedNumber = (distance / 100 * 100);
      formattedString = String.format(Locale.US, MILES_STRING_FORMAT, df.format(roundedNumber));
    } else {
      distance = TurfHelpers.convertDistance(distance, TurfConstants.UNIT_METERS, TurfConstants.UNIT_FEET);
      int roundedNumber = ((int) Math.round(distance)) / 100 * 100;
      formattedString = String.format(Locale.US, FEET_STRING_FORMAT, roundedNumber);
    }
    return "In " + formattedString;
  }

  private void setStepProgressBar(float fractionRemaining) {
    ObjectAnimator animation = ObjectAnimator.ofInt(stepProgressBar, "progress",
      Math.round(fractionRemaining * 10000));
    animation.setInterpolator(new LinearInterpolator());
    animation.setDuration(1000);
    animation.start();
  }
}
