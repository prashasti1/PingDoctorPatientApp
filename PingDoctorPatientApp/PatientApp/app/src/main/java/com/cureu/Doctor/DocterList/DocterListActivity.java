package com.cureu.Doctor.DocterList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.Loader;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DocterListActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    Loader loader;
    String response;
    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>() ;
    LinearLayoutManager mLayoutManager;

    String typename="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Doctor");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        recycler_view = (RecyclerView)  findViewById(R.id.recycler_view);
        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);

        response=getIntent().getStringExtra("response");
        typename=getIntent().getStringExtra("typename");

        dataParse(""+response);


    }


    public void dataParse(String response) {

    }

}