package com.projectx.utility;

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


  public static final long VIB_DOT = 150;
  public static final long VIB_DASH = 500;
  public static final long[] VIB_REACH_STEP = {0, 2000};
  public static final long VIB_S_SPACE = 190;
  public static final long VIB_L_SPACE = 680;
  public static final long VIB_XL_SPACE = 1000;
  public static final long VIB_ZERO = 0;
  public static final long[] VIB_NOTHING = {0};
  public static final long[] VIB_PATTERN_DIRECTION_TURN_LEFT = {
          VIB_ZERO,
          VIB_DOT,
          VIB_S_SPACE,
          VIB_DOT,
          VIB_L_SPACE };
  public static final long[] VIB_PATTERN_DIRECTION_TURN_RIGHT = {
          VIB_ZERO,
          VIB_DASH,
          VIB_S_SPACE,
          VIB_DASH,
          VIB_L_SPACE };
  public static final long[] VIB_PATTERN_DIRECTION_STRAIGHT= {
          VIB_ZERO,
          VIB_DOT,
          VIB_XL_SPACE,


 };

  public static final long[] pattern = {
          0,     // start vibrate immediately
          900,   // vibrate 1 900 ms
          100,   // silence 1 100 ms
          800,   // vibrate 2 800 ms
          200,   // silence 2 200 ms
          700,
          300,
          600,
          400,
          500,
          500,
          400,
          600,
          300,
          700,
          200,
          800,
          100,
          900,
  };
}
