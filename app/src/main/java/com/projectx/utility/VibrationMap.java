package com.projectx.utility;

import com.projectx.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by minhtri on 12/6/2017.
 */

import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_ARRIVE;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_CONTINUE;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_DEPART;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_END_OF_ROAD;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_FORK;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_MERGE;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_NEW_NAME;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_NOTIFICATION;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_OFF_RAMP;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_ON_RAMP;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_ROTARY;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_ROUNDABOUT;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_ROUNDABOUT_TURN;
import static com.mapbox.services.android.Constants.STEP_MANEUVER_TYPE_TURN;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.STEP_MANEUVER_MODIFIER_LEFT;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.STEP_MANEUVER_MODIFIER_RIGHT;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.STEP_MANEUVER_MODIFIER_SHARP_LEFT;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.STEP_MANEUVER_MODIFIER_SHARP_RIGHT;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.STEP_MANEUVER_MODIFIER_SLIGHT_LEFT;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.STEP_MANEUVER_MODIFIER_SLIGHT_RIGHT;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.STEP_MANEUVER_MODIFIER_STRAIGHT;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.STEP_MANEUVER_MODIFIER_UTURN;

public class VibrationMap {
    private Map<String, long[]> vibrationMap;


    public VibrationMap() {
        vibrationMap = new HashMap<>();
        vibrationMap.put(STEP_MANEUVER_TYPE_TURN + STEP_MANEUVER_MODIFIER_UTURN,
                Constants.VIB_PATTERN_DIRECTION_BACKWARD);
        vibrationMap.put(STEP_MANEUVER_TYPE_CONTINUE + STEP_MANEUVER_MODIFIER_UTURN,
                Constants.VIB_PATTERN_DIRECTION_BACKWARD);

        vibrationMap.put(STEP_MANEUVER_TYPE_CONTINUE + STEP_MANEUVER_MODIFIER_STRAIGHT,
                Constants.VIB_PATTERN_DIRECTION_STRAIGHT);

        vibrationMap.put(STEP_MANEUVER_TYPE_ARRIVE + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_REACH_DESTINATION);
        vibrationMap.put(STEP_MANEUVER_TYPE_ARRIVE + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_REACH_DESTINATION);
        vibrationMap.put(STEP_MANEUVER_TYPE_ARRIVE,
                Constants.VIB_REACH_DESTINATION);

        vibrationMap.put(STEP_MANEUVER_TYPE_DEPART + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_NOTHING);
        vibrationMap.put(STEP_MANEUVER_TYPE_DEPART + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_NOTHING);
        vibrationMap.put(STEP_MANEUVER_TYPE_DEPART, Constants.VIB_NOTHING);

        vibrationMap.put(STEP_MANEUVER_TYPE_TURN + STEP_MANEUVER_MODIFIER_SHARP_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_SHARP_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_TURN + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_TURN_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_TURN + STEP_MANEUVER_MODIFIER_SLIGHT_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_LIGHT_RIGHT);

        vibrationMap.put(STEP_MANEUVER_TYPE_TURN + STEP_MANEUVER_MODIFIER_SHARP_LEFT,
                Constants.VIB_PATTERN_DIRECTION_SHARP_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_TURN + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_PATTERN_DIRECTION_TURN_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_TURN + STEP_MANEUVER_MODIFIER_SLIGHT_LEFT,
                Constants.VIB_PATTERN_DIRECTION_LIGHT_LEFT);

        vibrationMap.put(STEP_MANEUVER_TYPE_MERGE + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_NOTHING);
        vibrationMap.put(STEP_MANEUVER_TYPE_MERGE + STEP_MANEUVER_MODIFIER_SLIGHT_LEFT,
                Constants.VIB_NOTHING);
        vibrationMap.put(STEP_MANEUVER_TYPE_MERGE + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_NOTHING);
        vibrationMap.put(STEP_MANEUVER_TYPE_MERGE + STEP_MANEUVER_MODIFIER_SLIGHT_RIGHT,
                Constants.VIB_NOTHING);
        vibrationMap.put(STEP_MANEUVER_TYPE_MERGE + STEP_MANEUVER_MODIFIER_STRAIGHT,
                Constants.VIB_NOTHING);

        vibrationMap.put(STEP_MANEUVER_TYPE_ON_RAMP + STEP_MANEUVER_MODIFIER_SHARP_LEFT,
                Constants.VIB_PATTERN_DIRECTION_SHARP_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ON_RAMP + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_PATTERN_DIRECTION_TURN_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ON_RAMP + STEP_MANEUVER_MODIFIER_SLIGHT_LEFT,
                Constants.VIB_PATTERN_DIRECTION_LIGHT_LEFT);

        vibrationMap.put(STEP_MANEUVER_TYPE_ON_RAMP + STEP_MANEUVER_MODIFIER_SHARP_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_SHARP_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ON_RAMP + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_TURN_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ON_RAMP + STEP_MANEUVER_MODIFIER_SLIGHT_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_LIGHT_RIGHT);

        vibrationMap.put(STEP_MANEUVER_TYPE_OFF_RAMP + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_NOTHING);
        vibrationMap.put(STEP_MANEUVER_TYPE_OFF_RAMP + STEP_MANEUVER_MODIFIER_SLIGHT_LEFT,
                Constants.VIB_NOTHING);

        vibrationMap.put(STEP_MANEUVER_TYPE_OFF_RAMP + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_NOTHING);
        vibrationMap.put(STEP_MANEUVER_TYPE_OFF_RAMP + STEP_MANEUVER_MODIFIER_SLIGHT_RIGHT,
                Constants.VIB_NOTHING);

        vibrationMap.put(STEP_MANEUVER_TYPE_FORK + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_PATTERN_DIRECTION_TURN_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_FORK + STEP_MANEUVER_MODIFIER_SLIGHT_LEFT,
                Constants.VIB_PATTERN_DIRECTION_LIGHT_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_FORK + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_TURN_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_FORK + STEP_MANEUVER_MODIFIER_SLIGHT_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_LIGHT_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_FORK + STEP_MANEUVER_MODIFIER_STRAIGHT,
                Constants.VIB_PATTERN_DIRECTION_STRAIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_FORK, Constants.VIB_NOTHING);

        vibrationMap.put(STEP_MANEUVER_TYPE_END_OF_ROAD + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_PATTERN_DIRECTION_TURN_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_END_OF_ROAD + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_TURN_RIGHT);

        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT + STEP_MANEUVER_MODIFIER_SHARP_LEFT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SHARP_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT + STEP_MANEUVER_MODIFIER_SLIGHT_LEFT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SLIGHT_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT + STEP_MANEUVER_MODIFIER_SHARP_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SHARP_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT + STEP_MANEUVER_MODIFIER_SLIGHT_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SLIGHT_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT + STEP_MANEUVER_MODIFIER_STRAIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_FORWARD);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT, Constants.VIB_NOTHING);

        vibrationMap.put(STEP_MANEUVER_TYPE_ROTARY + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROTARY + STEP_MANEUVER_MODIFIER_SHARP_LEFT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SHARP_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROTARY + STEP_MANEUVER_MODIFIER_SLIGHT_LEFT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SLIGHT_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROTARY + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROTARY + STEP_MANEUVER_MODIFIER_SHARP_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SHARP_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROTARY + STEP_MANEUVER_MODIFIER_SLIGHT_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SLIGHT_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROTARY + STEP_MANEUVER_MODIFIER_STRAIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_FORWARD);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROTARY, Constants.VIB_NOTHING);

        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT_TURN + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_ROUNDABOUT_TURN + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_RIGHT);

        vibrationMap.put(STEP_MANEUVER_TYPE_NOTIFICATION + STEP_MANEUVER_MODIFIER_LEFT,
                Constants.VIB_PATTERN_DIRECTION_TURN_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_NOTIFICATION + STEP_MANEUVER_MODIFIER_SHARP_LEFT,
                Constants.VIB_PATTERN_DIRECTION_SHARP_LEFT);
        vibrationMap.put(STEP_MANEUVER_TYPE_NOTIFICATION + STEP_MANEUVER_MODIFIER_SLIGHT_LEFT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SLIGHT_LEFT);

        vibrationMap.put(STEP_MANEUVER_TYPE_NOTIFICATION + STEP_MANEUVER_MODIFIER_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_TURN_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_NOTIFICATION + STEP_MANEUVER_MODIFIER_SHARP_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SHARP_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_NOTIFICATION + STEP_MANEUVER_MODIFIER_SLIGHT_RIGHT,
                Constants.VIB_PATTERN_DIRECTION_ROUNDABOUT_SLIGHT_RIGHT);
        vibrationMap.put(STEP_MANEUVER_TYPE_NOTIFICATION + STEP_MANEUVER_MODIFIER_STRAIGHT,
                Constants.VIB_PATTERN_DIRECTION_STRAIGHT);

        vibrationMap.put(STEP_MANEUVER_TYPE_NEW_NAME + STEP_MANEUVER_MODIFIER_STRAIGHT,
                Constants.VIB_PATTERN_DIRECTION_STRAIGHT);
    }

    public long[] getVibrationResource(String vibration) {
        if (vibrationMap.get(vibration) != null) {
            return vibrationMap.get(vibration);
        } else {
            return Constants.VIB_NOTHING;
        }
    }
}
