package com.cureu.Doctor.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cureu.Doctor.AllSymtomsAdapter;
import com.cureu.Doctor.Dashbord.dto.AllConsultAdapter;
import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.Login.dto.Data;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ConsultdoctorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText consultemassage,edMobile;
    Button btsubmit;
    Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultdoctor);

        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Doctor Consult");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        consultemassage = findViewById(R.id.consultemassage);
        btsubmit = findViewById(R.id.btsubmit);
        edMobile = findViewById(R.id.edMobile);
        btsubmit.setOnClickListener(this);
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
        String str = prefs.getString(ApplicationConstant.INSTANCE.allsympton, "");
        GalleryListResponse sliderImage = new Gson().fromJson(str, GalleryListResponse.class);
        String balanceResponse = prefs.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        edMobile.setText(balanceCheckResponse.getMobile());
        if (sliderImage.getData() != null) {
            ArrayList arrayList = new ArrayList<>();
            for (int i = 0; i < sliderImage.getData().size(); i++) {
//                TextView tv = new TextView(this);
//                tv.setTag(sliderImage.getData().get(i).getId());
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                );
//                params.setMargins(10, 10, 10, 10);
//                tv.setLayoutParams(params);
//                tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                tv.setPadding(10,2,10,2);
//                Drawable img = this.getResources().getDrawable(R.drawable.ic_cancel_black_24dp);
//                img.setBounds(0, 0, 60, 60);
//                tv.setCompoundDrawables(null, null, img, null);
//                tv.setText(sliderImage.getData().get(i).getSymtom());
//                consultemassage.addView(tv);

            }
//            AllConsultAdapter mAdapter = new AllConsultAdapter(sliderImage.getData(), this);
//            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
//            consultemassage.setLayoutManager(mLayoutManager);
//            consultemassage.setItemAnimator(new DefaultItemAnimator());
//            consultemassage.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(View view) {


        if (view == btsubmit) {

            if (consultemassage.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(this, "problem   Can not be Empty", Toast.LENGTH_SHORT).show();


            } else {

                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.setconsult(this, consultemassage.getText().toString().trim(), loader, this);

                } else {
                    UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                            getResources().getString(R.string.network_error_message));
                }
            }


        }


    }
}