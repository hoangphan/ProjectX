using Android.App;
using Android.Widget;
using Android.OS;
//using Xamarin.Forms.Maps;
//using Xamarin.Forms;
//using Xamarin;

namespace App2
{


    [Activity(Label = "App2", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            //global::Xamarin.Forms.Forms.Init(this, bundle);
            //
            //Xamarin.FormsMaps.Init(this, bundle);
            //SetContentView(Resource.Layout.layout1);

            mapview
        }
    }
}

