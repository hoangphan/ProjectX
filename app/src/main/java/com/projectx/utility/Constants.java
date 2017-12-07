package com.projectx.utility;

import android.support.v4.content.res.TypedArrayUtils;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Constants {

  public static final double MPH_DOUBLE = 2.2369;
  public static final String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiaG9hbmdwaGFuIiwiYSI6ImNqOHN2eXY0ZzBlYmczMnAwbTQyNXVhYWkifQ.BbvO9XZQoo0P8qptKXAcVw";
  public static final String PLACE_LOCATION_EXTRA = "PLACE_LOCATION_EXTRA";

  public static final String PLACE_LOCATION_A_LONG = "PLACE_LOCATION_A_LONG";
  public static final String PLACE_LOCATION_A_LAT = "PLACE_LOCATION_A_LAT";
  public static final String PLACE_LOCATION_A_ATT = "PLACE_LOCATION_A_ATT";

  public static final String PLACE_LOCATION_B_LONG = "PLACE_LOCATION_B_LONG";
  public static final String PLACE_LOCATION_B_LAT = "PLACE_LOCATION_B_LAT";
  public static final String PLACE_LOCATION_B_ATT = "PLACE_LOCATION_B_ATT";

  public static final String DAYS = " days ";
  public static final String HOUR = " hr ";
  public static final String MINUTE = " min ";
  public static final String SECONDS = " seconds";
  public static final String MILES = " mi";
  public static final double METER_MULTIPLIER = .00062137;
  public static final String DURATION_ILLEGAL_ARGUMENT = "Duration must be greater than zero.";
  public static final int STRING_BUILDER_CAPACITY = 64;


  //----------
  //Vibration patterns


  public static final long VIB_DOT = 120;
  public static final long VIB_DASH = 500;
  public static final long[] VIB_REACH_STEP = {0, 2000, 780};
  public static final long VIB_S_SPACE = 350;
  public static final long VIB_L_SPACE = 980;
  public static final long VIB_XL_SPACE = 1000;
  public static final long VIB_ZERO = 0;
  public static final long[] VIB_NOTHING = {0};
  //3 DOTs 1 DASH
  public static final long[] VIB_REACH_DESTINATION = {VIB_ZERO, VIB_DOT, VIB_S_SPACE, VIB_DOT, VIB_S_SPACE, VIB_DOT, VIB_S_SPACE, VIB_DASH, VIB_L_SPACE};
  public static final long[] VIB_PATTERN_DIRECTION_TURN_LEFT = {
          VIB_ZERO,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DOT,
          VIB_L_SPACE };
  public static final long[] VIB_PATTERN_DIRECTION_LIGHT_LEFT = {
          VIB_ZERO,
          VIB_DOT,
          VIB_L_SPACE };
  public static final long[] VIB_PATTERN_DIRECTION_SHARP_LEFT = {
          VIB_ZERO,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DOT,
          VIB_L_SPACE, };
  public static final long[] VIB_PATTERN_DIRECTION_TURN_RIGHT = {
          VIB_ZERO,
          VIB_DASH,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_L_SPACE };
  public static final long[] VIB_PATTERN_DIRECTION_LIGHT_RIGHT = {
          VIB_ZERO,
          VIB_DASH,
          VIB_L_SPACE };
  public static final long[] VIB_PATTERN_DIRECTION_SHARP_RIGHT = {
          VIB_ZERO,
          VIB_DASH,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_L_SPACE };
  public static final long[] VIB_PATTERN_DIRECTION_STRAIGHT= {
          VIB_ZERO,
          VIB_DASH,
          VIB_S_SPACE,
          VIB_DOT,
          VIB_L_SPACE
  };

  public static final long[] VIB_PATTERN_DIRECTION_FORWARD= {
          VIB_ZERO,
          VIB_DASH,
          VIB_S_SPACE,
          VIB_DOT,
          VIB_L_SPACE
  };
  public static final long[] VIB_PATTERN_DIRECTION_BACKWARD= {
          VIB_ZERO,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_L_SPACE
  };

    public static final long[] VIB_PATTERN_DIRECTION_ROUNDABOUT_LEFT = {
            VIB_ZERO,
            VIB_DOT,
            VIB_S_SPACE,
            VIB_DASH,
            VIB_S_SPACE,

            VIB_DOT,
            VIB_S_SPACE,
            VIB_DOT,
            VIB_L_SPACE
    };

  public static final long[] VIB_PATTERN_DIRECTION_ROUNDABOUT_SLIGHT_LEFT = {
          VIB_ZERO,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_S_SPACE,

          VIB_DOT,
          VIB_L_SPACE
  };

  public static final long[] VIB_PATTERN_DIRECTION_ROUNDABOUT_SHARP_LEFT = {
          VIB_ZERO,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_S_SPACE,

          VIB_DOT,
          VIB_S_SPACE,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DOT,
          VIB_L_SPACE
  };

    public static final long[] VIB_PATTERN_DIRECTION_ROUNDABOUT_RIGHT = {
            VIB_ZERO,
            VIB_DOT,
            VIB_S_SPACE,
            VIB_DASH,
            VIB_S_SPACE,

            VIB_DASH,
            VIB_S_SPACE,
            VIB_DASH,
            VIB_L_SPACE
    };

  public static final long[] VIB_PATTERN_DIRECTION_ROUNDABOUT_SLIGHT_RIGHT = {
          VIB_ZERO,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_S_SPACE,

          VIB_DASH,
          VIB_L_SPACE
  };

  public static final long[] VIB_PATTERN_DIRECTION_ROUNDABOUT_SHARP_RIGHT = {
          VIB_ZERO,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_S_SPACE,

          VIB_DASH,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_L_SPACE
  };

    public static final long[] VIB_PATTERN_DIRECTION_ROUNDABOUT_FORWARD = {
            VIB_ZERO,
            VIB_DOT,
            VIB_S_SPACE,
            VIB_DASH,
            VIB_S_SPACE,

            VIB_DASH,
            VIB_S_SPACE,
            VIB_DOT,
            VIB_L_SPACE
    };



}
