package com.cureu.Doctor.Dashbord.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
 import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.cureu.Doctor.BuildConfig;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.GooglePlayStoreAppVersionNameLoader;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.rampo.updatechecker.UpdateChecker;
import com.vincent.filepicker.activity.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardMainNew extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  //  TextView  userName,mobile,email;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FrameLayout main_container;
    DrawerLayout drawerLayout;
    private static long back_pressed;
    public static int countBackstack = 0;
    private static final int TIME_DELAY = 2000;
    RelativeLayout call,favarate,card,editlocation,reference;
    TextView location,AddressLine;
    RecyclerView recyclerView;
    TextView ic_logout,Profile,support,Share,orderhistory;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private final static int AUTOCOMPLETE_REQUEST_CODE = 1;
    Loader loader;
    String version="";
    String versionName="";
    int versionCode;

    private Location mLastLocation;

     private GoogleApiClient mGoogleApiClient;

    public static double latitude;
    public static double longitude;
    LocationRequest mLocationRequest;
    TextView tv_version;


    @Override
    protected void permissionGranted() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

       GetId();

        version= GooglePlayStoreAppVersionNameLoader.newVersion;

        getVersionInfo();
        PopUpdate();

        if (checkPlayServices()) {
             buildGoogleApiClient();
        }



    }

    private void GetId() {

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        editlocation=findViewById(R.id.editlocation);
        tv_version=findViewById(R.id.tv_version);
        card=findViewById(R.id.card);
        call=findViewById(R.id.call);
        favarate=findViewById(R.id.favarate);
        reference=findViewById(R.id.reference);
        location=findViewById(R.id.location);
        AddressLine=findViewById(R.id.AddressLine);

        editlocation.setOnClickListener(this);
        card.setOnClickListener(this);
        call.setOnClickListener(this);
        favarate.setOnClickListener(this);
        reference.setOnClickListener(this);
        location.setOnClickListener(this);
        AddressLine.setOnClickListener(this);

        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        main_container = (FrameLayout) findViewById(R.id.main_container);




        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recycler_view);

       ic_logout=findViewById(R.id.ic_logout);

        Profile=findViewById(R.id.Profile);
        support=findViewById(R.id.support);
        Share=findViewById(R.id.Share);
        orderhistory=findViewById(R.id.orderhistory);

        ic_logout.setOnClickListener(this);

        Profile.setOnClickListener(this);
        support.setOnClickListener(this);
        Share.setOnClickListener(this);
        orderhistory.setOnClickListener(this);



        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#2F84C7\">" + "" + "</font>")));

        changeFragment(new HomeFragment());


      //  Gethint();

    }


    private void Gethint() {





        View one = findViewById(R.id.reference);
        int[] oneLocation = new int[2];
        one.getLocationInWindow(oneLocation);
        float oneX = oneLocation[0] + one.getWidth() / 2f;
        float oneY = oneLocation[1] + one.getHeight() / 2f;





    }


    public void OpenUpdateDialog() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.update_available_pop, null);

        TextView tvLater = (TextView) view.findViewById(R.id.tv_later);
        TextView tvOk=(TextView)view.findViewById(R.id.tv_ok);

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMarket(DashboardMainNew.this);
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private static void goToMarket(Context mContext) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UpdateChecker.ROOT_PLAY_STORE_DEVICE + mContext.getPackageName())));
    }

    private  void PopUpdate(){

        // Log.e("version","    versionName    "+versionName +"  version    "+version );

        if(version!=null && !version.equalsIgnoreCase("")){


            if(!versionName.equalsIgnoreCase(version)){

                OpenUpdateDialog();

            }

        }
    }

    private void getVersionInfo() {

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

            tv_version.setText(" Version  :  "+ BuildConfig.VERSION_NAME);

            // Log.e("versionnn","   versionName   "+versionName+"   versionCode  "+  versionCode+"   version  "+  version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(DashboardMainNew.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         switch (requestCode) {

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;


                    default:
                        break;
                }
                break;
        }

       /* if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                location.setText(""+ place.getName());
                AddressLine.setText(""+place.getAddress());


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
               // Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

                // The user canceled the operation.

            }
        }*/
    }


    private void getLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (mLastLocation == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {


                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            GlobalData.latitude = mLastLocation.getLatitude();
            GlobalData.longitude = mLastLocation.getLongitude();


             // Log.e("GlobalData.latitude3", "" + GlobalData.latitude);
            // Log.e("GlobalData.longitude3 ", "" + GlobalData.longitude);

            GetLOcation();
        }

    }

    private void GetLOcation() {

        // GetLocation Location = new GetLocation(this);
        //  latitude = Location.getLatitude();
        // longitude = Location.getLongitude();


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(GlobalData.latitude, GlobalData.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getCountryName();
        String countryName = addresses.get(0).getLocality();

        // Log.e("countryName","   cityName  "+  cityName  + "    stateName   "+ stateName  +"   countryName   "+countryName);


        location.setText(""+countryName);
        AddressLine.setText(""+cityName);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        {
            if(id==R.id.home){

                changeFragment(new HomeFragment());


            }else {

                changeFragment(new HomeFragment());

            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean changeFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (countBackstack > 0) {
            countBackstack = 0;
//            fm.beginTransaction().replace(R.id.main_container, new ServiceFragment()).commit();

        }else if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    private void HitApi() {
        getVersionInfo();
        PopUpdate();

        UtilMethods.INSTANCE.AllSymtoms(this, null);

        /*if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            UtilMethods.INSTANCE.Category(this,null);
            UtilMethods.INSTANCE.offer(this,null);

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.products(this, loader);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }*/

    }

    @Override
    public void onClick(View view) {

        if(view==reference){


            HitApi();


        }

      if(view==favarate){

           // new FavouriteFragment().show(getSupportFragmentManager(),"Dialog");

         //   startActivity(new Intent(this, FavouriteActivity.class));

        }

       if(view==card){





       }


       if(view==Profile){

           //startActivity(new Intent(this, ProfileDetail.class));

           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);


       }

     if(view==support){

        // startActivity(new Intent(this, SupportActivity.class));

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);

     }

     if(view==Share){


         Intent shareIntent =   new Intent(Intent.ACTION_SEND);
         shareIntent.setType("text/plain");
         shareIntent.putExtra(Intent.EXTRA_SUBJECT,"freshapure");
         String app_url = "https://play.google.com/store/apps/details?id=co.wl.freshapure";
         shareIntent.putExtra(Intent.EXTRA_TEXT,app_url);
         startActivity(Intent.createChooser(shareIntent, "Share via"));


         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);

     }

     if(view==orderhistory){


         if (UtilMethods.INSTANCE.isNetworkAvialable( this) ){

             loader.show();
             loader.setCancelable(false);
             loader.setCanceledOnTouchOutside(false);


             UtilMethods.INSTANCE.GetAllPatientAppointment(this, loader);


         } else {
             UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                     getResources().getString(R.string.network_error_message));
         }


         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);




        }


     if(view==ic_logout){


         final SweetAlertDialog alertDialog = new SweetAlertDialog(DashboardMainNew.this);
         alertDialog.setTitle("Alert!");
         alertDialog.setContentText("Do you want to logout?");
         alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
             @Override
             public void onClick(SweetAlertDialog sweetAlertDialog) {
                 alertDialog.dismiss();
             }
         });

         alertDialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
             @Override
             public void onClick(SweetAlertDialog sweetAlertDialog) {

                 UtilMethods.INSTANCE.logout(DashboardMainNew.this);


             }
         });

         alertDialog.show();

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);

        }


      if(view==location){


        /*  List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS,Place.Field.TYPES,Place.Field.ADDRESS,Place.Field.PHONE_NUMBER);

          Intent intent = new Autocomplete.IntentBuilder(
                  AutocompleteActivityMode.FULLSCREEN, fields)
                  .build(DashboardMainNew.this);
          startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);*/

      }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onResume() {

        HitApi();
        super.onResume();

    }

    @Override
    public void onPause() {
         super.onPause();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

        /*    //Click English menu option
            case R.id.action_english:
                LanguageManager.setNewLocale(this, LanguageManager.LANGUAGE_KEY_ENGLISH);

                reLaunchApp();
                return true;


            case R.id.action_arabic:
                LanguageManager.setNewLocale(this, LanguageManager.LANGUAGE_KEY_ARABIC);

                reLaunchApp();

                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }

    protected void reLaunchApp(){

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }



}
