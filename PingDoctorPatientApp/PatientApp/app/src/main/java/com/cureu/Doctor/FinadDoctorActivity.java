package com.cureu.Doctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.Dashbord.ui.SearchDoctorActivity;
import com.cureu.Doctor.Dashbord.ui.SpecialitiesAdapterSecend;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.FragmentActivityMessage;
import com.cureu.Doctor.Utils.GlobalBus;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class FinadDoctorActivity extends AppCompatActivity {

    RecyclerView recycler_view_doctor;
    Loader loader;

    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>() ;

    SpecialitiesAdapterSecend doctormAdapter;
    LinearLayoutManager mLayoutManager;
    LinearLayout search_by_doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finad_doctor);

        recycler_view_doctor=findViewById(R.id.recycler_view_doctor);
        search_by_doctor=findViewById(R.id.search_by_doctor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Find & Book");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        search_by_doctor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startActivity(new Intent(FinadDoctorActivity.this, SearchDoctorActivity.class));
                return false;
            }
        });
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
        String str = prefs.getString(ApplicationConstant.INSTANCE.allCategoryDoctor, "");
        dataParse(str);

     //   HitApi();

    }


    private void HitApi() {

        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);

        if (UtilMethods.INSTANCE.isNetworkAvialable( this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);


            UtilMethods.INSTANCE.doctorlist(this, loader);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }

    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
      if (activityFragmentMessage.getMessage().equalsIgnoreCase("specialities")) {

          dataParse(activityFragmentMessage.getFrom());

        }
    }


    public void dataParse(String response) {

        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            doctormAdapter = new SpecialitiesAdapterSecend(sliderLists, this);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view_doctor.setLayoutManager(new GridLayoutManager(this, 2));
            recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(doctormAdapter);

            recycler_view_doctor.setVisibility(View.VISIBLE);
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







}