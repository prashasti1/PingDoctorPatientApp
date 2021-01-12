package com.cureu.Doctor.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.Login.dto.Data;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PreciptionActivity extends AppCompatActivity {

    RecyclerView recycler_view_Preciption;

    String response="";
    String doctorDetail="";
    TextView clinic_name,name,mobile,email;
  //  Data operator;
    ImageView Preciption_img;
    TextView Preciption_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preciption);

        Preciption_description=findViewById(R.id.Preciption_description);

         Preciption_img=findViewById(R.id.Preciption_img);

        clinic_name=findViewById(R.id.clinic_name);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);

        response=getIntent().getStringExtra("response");

        doctorDetail=getIntent().getStringExtra("doctorDetail");

        Data balanceCheckResponse = new Gson().fromJson(response, Data.class);

        clinic_name.setText(""+balanceCheckResponse.getClinic_name() );
        name.setText("Dr. "+balanceCheckResponse.getName() );
        mobile.setText("Phone : "+balanceCheckResponse.getMobile() );
        email.setText("Email : "+balanceCheckResponse.getEmail() );
        Preciption_description.setText("Description : "+balanceCheckResponse.getPreciption_description() );

       // customerimage

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);

        Log.e("Preciption_img",""+ balanceCheckResponse.getPreciption_img() );



        Glide.with(this)
                .load(balanceCheckResponse.getPreciption_img())
                .apply(requestOptions)
                .into(Preciption_img);

        recycler_view_Preciption=findViewById(R.id.recycler_view_Preciption);


      UtilMethods.INSTANCE.GetMedicineById(this,balanceCheckResponse.getMedicine_id(),null,recycler_view_Preciption);




      Preciption_img.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              View viewpopup = inflater.inflate(R.layout.popupfullimage, null);


              Button okButton = (Button) viewpopup.findViewById(R.id.okButton);
              ImageView popPreciption_img = (ImageView) viewpopup.findViewById(R.id.popPreciption_img);

              final Dialog dialog = new Dialog(PreciptionActivity.this);

              dialog.setCancelable(false);
              dialog.setContentView(viewpopup);
              dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


              Glide.with(PreciptionActivity.this)
                      .load(balanceCheckResponse.getPreciption_img())
                      .apply(requestOptions)
                      .into(popPreciption_img);


              okButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {


                      dialog.dismiss();



                  }
              });



              dialog.show();






            /*  Preciption_img.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
              Preciption_img.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
              Preciption_img.setAdjustViewBounds(false);
              Preciption_img.setScaleType(ImageView.ScaleType.FIT_XY);*/







          }
      });


    }

    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    MedicineListAdapter medicinelistadapter;
    LinearLayoutManager mLayoutManager;



 }