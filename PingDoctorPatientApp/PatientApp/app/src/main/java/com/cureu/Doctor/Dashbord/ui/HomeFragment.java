package com.cureu.Doctor.Dashbord.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cureu.Doctor.AllSymtomsActivity;
import com.cureu.Doctor.BuildConfig;
import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.FinadDoctorActivity;
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
import com.google.gson.Gson;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.FragmentActivityMessage;
import com.cureu.Doctor.Utils.GlobalBus;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment implements View.OnClickListener ,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    CircleImageView userpic;
    TextView name,contentnumber;
    RecyclerView recycler_view,recycler_view_doctor;
    Loader loader;
    LinearLayout errordeta;
    TextView balanceamount,Logout_tv;

    ViewPager mViewPager;
    LinearLayout dotsCount;
    Handler handler;
    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>() ;
    CustomPagerAdapter mCustomPagerAdapter;
    Integer mDotsCount;
    public static TextView mDotsText[];
    SpecialitiesAdapter mAdapter;
    DoctorListDashboadAdapter doctormAdapter;
    LinearLayoutManager mLayoutManager;

    LinearLayout search,find_doctor,doctorsearch,Consultdoctor,AllSymtoms;

   /////LOcatin///

    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;

    public static double latitude;
    public static double longitude;
    LocationRequest mLocationRequest;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    TextView location,AddressLine,chatButton;
    RelativeLayout share,notification;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home, container, false);

        Getid(v);

        if (checkPlayServices()) {
            buildGoogleApiClient();
        }



        if (shouldAskPermissions()) {
            askPermissions();
        }

        return v;

    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    protected boolean shouldAskPermissions() {

        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

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

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(getActivity(), resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
               // finish();
            }
            return false;
        }
        return true;
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
            if (ContextCompat.checkSelfPermission(getActivity(),
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


        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(GlobalData.latitude, GlobalData.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addresses.get(0).getAddressLine(0)==null){


            }

            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getCountryName();
            String countryName = addresses.get(0).getLocality();

            Log.e("countryName","   cityName  "+  cityName  + "    stateName   "+ stateName  +"   countryName   "+countryName);


            location.setText(""+countryName);
            AddressLine.setText(""+cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    private void Getid(View v) {

        notification=v.findViewById(R.id.notification);
        share=v.findViewById(R.id.share);

        notification.setOnClickListener(this);
        share.setOnClickListener(this);


        location=v.findViewById(R.id.location);
        AddressLine=v.findViewById(R.id.AddressLine);
        chatButton=v.findViewById(R.id.chatButton);


        search=v.findViewById(R.id.search);
        search.setOnClickListener(this);
        chatButton.setOnClickListener(this);

        find_doctor=v.findViewById(R.id.find_doctor);
        find_doctor.setOnClickListener(this);

        doctorsearch=v.findViewById(R.id.doctorsearch);
        doctorsearch.setOnClickListener(this);

        Consultdoctor=v.findViewById(R.id.Consultdoctor);
        Consultdoctor.setOnClickListener(this);


        AllSymtoms=v.findViewById(R.id.AllSymtoms);
        AllSymtoms.setOnClickListener(this);

        mViewPager = (ViewPager)v.findViewById(R.id.pager);
        handler=new Handler();
        dotsCount   = (LinearLayout)v.findViewById(R.id.image_count);


        balanceamount=v.findViewById(R.id.balanceamount);
        Logout_tv=v.findViewById(R.id.Logout_tv);
        Logout_tv.setOnClickListener(this);

        errordeta=v.findViewById(R.id.errordeta);
        contentnumber=v.findViewById(R.id.contentnumber);

        name=v.findViewById(R.id.name);
        userpic=v.findViewById(R.id.userpic);


        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);

        recycler_view=v.findViewById(R.id.recycler_view);
        recycler_view_doctor=v.findViewById(R.id.recycler_view_doctor);

        HitApi();


         userpic.setOnClickListener(this);



    }



    private void GalleryList(String response) {

        sliderImage = new Gson().fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();


        // For Inflate Banner Images...
        if (sliderLists != null && sliderLists.size() > 0) {
            mCustomPagerAdapter = new CustomPagerAdapter(sliderLists, getActivity());
            mViewPager.setAdapter(mCustomPagerAdapter);

            mDotsCount = mViewPager.getAdapter().getCount();

            mDotsText = new TextView[mDotsCount];

            //here we set the dots
            for (int i = 0; i < mDotsCount; i++) {
                mDotsText[i] = new TextView(getActivity());
                mDotsText[i].setText(".");
                mDotsText[i].setTextSize(40);
                mDotsText[i].setTypeface(null, Typeface.BOLD);
                mDotsText[i].setTextColor(android.graphics.Color.GRAY);
                dotsCount.addView(mDotsText[i]);
            }

            mViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });

            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    for (int i = 0; i < mDotsCount; i++) {
                        mDotsText[i].setTextColor(getResources().getColor(R.color.colorBackground));
                    }
                    mDotsText[position].setTextColor(getResources().getColor(R.color.black));
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            postDelayedScrollNext();
        }

    }

    private void postDelayedScrollNext() {
        handler.postDelayed(new Runnable() {
            public void run() {

                try {
                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                        mViewPager.setCurrentItem(0);
                        postDelayedScrollNext();
                        return;
                    }
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    // postDelayedScrollNext(position+1);
                    postDelayedScrollNext();
                }catch (Exception e){

                }
                // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
            }
        }, 3000);

    }

    private void HitApi() {

        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);



        if (UtilMethods.INSTANCE.isNetworkAvialable( getActivity())) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

             UtilMethods.INSTANCE.getbanner(getActivity(), loader);
              UtilMethods.INSTANCE.doctorlist(getActivity(), loader);



        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }


    }



    @Override
    public void onClick(View v) {

        if(v==find_doctor){



            startActivity(new Intent(getActivity(), FinadDoctorActivity.class));


        }



     if(v==doctorsearch){



            startActivity(new Intent(getActivity(), SearchDoctorActivity.class));


        }
        if(v==chatButton){



            startActivity(new Intent(getActivity(), ConsultdoctorActivity.class));


        }

 if(v==Consultdoctor){



            startActivity(new Intent(getActivity(), ConsultdoctorActivity.class));


        }



 if(v==AllSymtoms){



            startActivity(new Intent(getActivity(), AllSymtomsActivity.class));


        }





  if(v==notification){



        }

  if(v==share){

      try {
          Intent shareIntent = new Intent(Intent.ACTION_SEND);
          shareIntent.setType("text/plain");
          shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Cureu");
          String shareMessage= "\n";
          shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
          shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
          startActivity(Intent.createChooser(shareIntent, "choose one"));
      } catch(Exception e) {
          //e.toString();
      }

        }


    }


    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("GalleryList")) {

            GalleryList(activityFragmentMessage.getFrom());


        }else if (activityFragmentMessage.getMessage().equalsIgnoreCase("specialities")) {

            dataParse(activityFragmentMessage.getFrom());

        }else if (activityFragmentMessage.getMessage().equalsIgnoreCase("doctorlist")) {

            dataParsedoctor(activityFragmentMessage.getFrom());

        }

    }

    public void dataParse(String response) {



        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            mAdapter = new SpecialitiesAdapter(sliderLists, getActivity());
               mLayoutManager = new LinearLayoutManager(getActivity());
               // mLayoutManager = new GridLayoutManager(getActivity(),3);
            recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            // recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

            recycler_view.setVisibility(View.VISIBLE);
        } else {
            recycler_view.setVisibility(View.GONE);
        }

    }


    public void dataParsedoctor(String response) {



        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            doctormAdapter = new DoctorListDashboadAdapter(sliderLists, getActivity());
               mLayoutManager = new LinearLayoutManager(getActivity());
             recycler_view_doctor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
             recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(doctormAdapter);

            recycler_view_doctor.setVisibility(View.GONE);
          //  recycler_view_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_view_doctor.setVisibility(View.GONE);
        }

    }


    @Override
    public void onResume() {
        super.onResume();


    }



    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
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
}
