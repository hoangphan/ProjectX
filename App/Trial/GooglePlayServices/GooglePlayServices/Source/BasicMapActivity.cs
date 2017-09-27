/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// C# Port by Atsushi Eno
// Copyright (C) 2012 Xamarin, Inc. (Apache License, Version 2.0 too)

using System;
using Android.App;
using Android.Gms.Maps;
using Android.Gms.Maps.Model;

using Android.OS;
using Android.Widget;
using Android.Locations;
using System.Collections.Generic;
using System.Linq;
using Android.Util;
using Plugin.Geolocator;
using System.Threading.Tasks;
using System.Text;
using Plugin.Geolocator.Abstractions;
using System.Timers;
using Android.Views;

namespace GooglePlayServicesTest
{
	[Activity (Label = "XA GoogleMapV2 Basic Map")]
	public class BasicMapActivity : Android.Support.V4.App.FragmentActivity, IOnMapReadyCallback, ILocationListener
	{
        static readonly string TAG = "X:" + typeof(BasicMapActivity).Name;
        TextView _addressText;
        Location _currentLocation;
        LocationManager _locationManager;

        string _locationProvider;
        TextView _locationText;

        System.Timers.Timer timer = new System.Timers.Timer();
        bool _lockCall = false;

        public void OnLocationChanged(Location location) {
            //Address address = ReverseGeocodeCurrentLocation();
            //DisplayAddress(address);
        }


        public void OnProviderDisabled(string provider) { }

        public void OnProviderEnabled(string provider) { }

        public void OnStatusChanged(string provider, Availability status, Bundle extras) { }

        /**
     * Note that this may be null if the Google Play services APK is not available.
     */
        protected override void OnCreate (Bundle savedInstanceState)
		{
			base.OnCreate (savedInstanceState);
			SetContentView (Resource.Layout.basic_demo);

			//var mapFragment = ((SupportMapFragment)SupportFragmentManager.FindFragmentById (Resource.Id.map));
			//mapFragment.GetMapAsync (this);

            _locationText = FindViewById<TextView>(Resource.Id.location_text);

            //InitializeLocationManager();

            //timer.Interval = 500;
            //timer.Elapsed += GetCurrentLocation;
            //timer.Enabled = true;

            Button button = FindViewById<Button>(Resource.Id.button_blutooth);
            button.Click += ButtonClicked;


        }

        void ButtonClicked(object sender, EventArgs args)
        {
            _locationText.Text = "Button Clicked";
        }

        public void scanScan_blutooth(View view)
        {
            // Do something in response to button click
        }

        public async void GetCurrentLocation(object sender, ElapsedEventArgs e)
        {
            if (_lockCall == true)
            {
                timer.Enabled = false;
                return;
            }

            _lockCall = true;
            Position position = null;
            try
            {
                var locator = CrossGeolocator.Current;
                locator.DesiredAccuracy = 100;
                position = new Position();
                RunOnUiThread(() =>

                        _locationText.Text = string.Format("Time: {0} \nLat: {1} \nLong: {2} \nAltitude: {3} \nAltitude Accuracy: {4} \nAccuracy: {5} \nHeading: {6} \nSpeed: {7}\n Now: {8}",
                    position.Timestamp, position.Latitude, position.Longitude,
                    position.Altitude, position.AltitudeAccuracy, position.Accuracy, position.Heading, position.Speed, DateTime.Now)

                );
                position = await locator.GetLastKnownLocationAsync();

                if (position != null)
                {
                    //got a cahched position, so let's use it.
                    RunOnUiThread(()=>

                        _locationText.Text = string.Format("Time: {0} \nLat: {1} \nLong: {2} \nAltitude: {3} \nAltitude Accuracy: {4} \nAccuracy: {5} \nHeading: {6} \nSpeed: {7}\n Now: {8}",
                    position.Timestamp, position.Latitude, position.Longitude,
                    position.Altitude, position.AltitudeAccuracy, position.Accuracy, position.Heading, position.Speed, DateTime.Now)
                       
                    );
                    _lockCall = false;
                    timer.Enabled = true;
                    return;
                }

                if (!locator.IsGeolocationAvailable || !locator.IsGeolocationEnabled)
                {
                    //not available or enabled
                    _lockCall = false;
                    timer.Enabled = true;
                    return;
                }

                position = await locator.GetPositionAsync(TimeSpan.FromSeconds(20), null, true);

            }
            catch (Exception ex)
            {
                //Display error as we have timed out or can't get location.
            }

            if (position == null)
            {

                _lockCall = false;
                timer.Enabled = true;
                return;
            }

            var output = string.Format("Time: {0} \nLat: {1} \nLong: {2} \nAltitude: {3} \nAltitude Accuracy: {4} \nAccuracy: {5} \nHeading: {6} \nSpeed: {7}",
                position.Timestamp, position.Latitude, position.Longitude,
                position.Altitude, position.AltitudeAccuracy, position.Accuracy, position.Heading, position.Speed);

            Console.WriteLine(output);
            _lockCall = false;
            timer.Enabled = true;
            return;
        }

        /*void InitializeLocationManager()
        {
            _locationManager = (LocationManager)GetSystemService(LocationService);
            Criteria criteriaForLocationService = new Criteria
            {
                Accuracy = Accuracy.Fine
            };
            IList<string> acceptableLocationProviders = _locationManager.GetProviders(criteriaForLocationService, true);

            if (acceptableLocationProviders.Any())
            {
                _locationProvider = acceptableLocationProviders.First();
            }
            else
            {
                _locationProvider = string.Empty;
            }
            Log.Debug(TAG, "Using " + _locationProvider + ".");
        }

        Address ReverseGeocodeCurrentLocation()
        {
            Geocoder geocoder = new Geocoder(this);
            IList<Address> addressList =
                 geocoder.GetFromLocation(_currentLocation.Latitude, _currentLocation.Longitude, 10);

            Address address = addressList.FirstOrDefault();
            return address;
        }

        void DisplayAddress(Address address)
        {
            if (address != null)
            {
                StringBuilder deviceAddress = new StringBuilder();
                for (int i = 0; i < address.MaxAddressLineIndex; i++)
                {
                    deviceAddress.AppendLine(address.GetAddressLine(i));
                }
                // Remove the last comma from the end of the address.
                _locationText.Text = deviceAddress.ToString();
            }
            else
            {
                _locationText.Text = "Unable to determine the address. Try again in a few minutes.";
            }
        }*/

        /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     */
        public void OnMapReady (GoogleMap map)
		{
			map.AddMarker (new MarkerOptions ().SetPosition (new LatLng (0, 0)).SetTitle ("Marker"));
		}

        protected override void OnResume()
        {
            base.OnResume();
            //_locationManager.RequestLocationUpdates(_locationProvider, 0, 0, this);
        }

        protected override void OnPause()
        {
            base.OnPause();
            //_locationManager.RemoveUpdates(this);
        }
    }
}

