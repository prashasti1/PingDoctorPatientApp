package com.cureu.Doctor.Dashbord.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.FragmentActivityMessage;
import com.cureu.Doctor.Utils.GlobalBus;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class SearchDoctorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText searchghazal;

     LinearLayoutManager mLayoutManager;


    RecyclerView recycler_view;

    GalleryListResponse transactions;
    ArrayList<Datum> transactionsObjects = new ArrayList<>() ;
    DoctorListDashboadAdapter mAdapter;
    ImageView imagedodeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Gertid();

    }

    private void Gertid() {

        imagedodeta=findViewById(R.id.imagedodeta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Doctor Search");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        recycler_view=findViewById(R.id.recycler_view);

        searchghazal=findViewById(R.id.searchghazal);
        searchghazal.setOnClickListener(this);

        recycler_view.setVisibility(View.GONE);
        imagedodeta.setVisibility(View.VISIBLE);

        searchghazal.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){

                    Log.e("Createghazalpage",""+ s +"   start   :  "+ start+"   before   :  "+ before+"   count   :  "+ count);

                    UtilMethods.INSTANCE.FetchDrByName(SearchDoctorActivity.this,searchghazal.getText().toString().trim());


                }else {


                    imagedodeta.setVisibility(View.GONE);
                }

            }
        });

    }


    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getFrom().equalsIgnoreCase("createghazal")) {

            dataParse(activityFragmentMessage.getMessage());

        }
    }

    public void dataParse(String response) {

        Gson gson = new Gson();
        transactions = gson.fromJson(response, GalleryListResponse.class);
        transactionsObjects = transactions.getData();

        if (transactionsObjects.size() > 0) {
            mAdapter = new DoctorListDashboadAdapter(transactionsObjects, this);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
            recycler_view.setVisibility(View.VISIBLE);
             imagedodeta.setVisibility(View.GONE);
        } else {
            recycler_view.setVisibility(View.GONE);
         }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }




    @Override
    public void onClick(View view) {


    }



}