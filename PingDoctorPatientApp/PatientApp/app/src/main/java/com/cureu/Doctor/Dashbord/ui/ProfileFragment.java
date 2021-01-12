package com.cureu.Doctor.Dashbord.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.cureu.Doctor.Login.dto.Data;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;
import com.cureu.Doctor.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    LinearLayout aboutus,Privacypolicy,logout,li_payment;
    CircleImageView customerimage;

    Loader loader;
    TextView Name,email,Phoneno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v= inflater.inflate(R.layout.activity_profile, container, false);

       Getid(v);

        return v;
    }

    private void Getid(View v) {

        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);

        Name=v.findViewById(R.id.name);
        email=v.findViewById(R.id.email);
        Phoneno=v.findViewById(R.id.Phoneno);
        customerimage=v.findViewById(R.id.customerimage);

        aboutus=v.findViewById(R.id.aboutus);
         Privacypolicy=v.findViewById(R.id.Privacypolicy);
         logout=v.findViewById(R.id.logout);
        li_payment=v.findViewById(R.id.li_payment);

        customerimage=v.findViewById(R.id.customerimage);
        customerimage.setOnClickListener(this);

        li_payment.setOnClickListener(this);
         logout.setOnClickListener(this);
        Privacypolicy.setOnClickListener(this);
         aboutus.setOnClickListener(this);

        SetData();
    }




    private void SetData() {

        SharedPreferences myPreferences =  getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);



        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);

        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseUrl+"/assets/img/patients/"+balanceCheckResponse.getImage())
                .apply(requestOptions)
                .into(customerimage);



        Name.setText(""+balanceCheckResponse.getName());
        email.setText(""+balanceCheckResponse.getEmail());
        Phoneno.setText(""+balanceCheckResponse.getMobile());


    }







    @Override
    public void onClick(View view) {





       if(view==customerimage){



        //   startActivity(new Intent(getActivity(),ActivityUserProfile.class));



        }


       if(view==li_payment){



        }



       if(view==aboutus){

           Intent i=new Intent(getActivity(),WebViewActivity.class);
           i.putExtra("url","http://cureu.in/");
           i.putExtra("name","About Us");
           startActivity(i);

        }


 if(view==Privacypolicy){

     Intent i=new Intent(getActivity(),WebViewActivity.class);
     i.putExtra("url","http://cureu.in/");
     i.putExtra("name","Privacy Policy");
     startActivity(i);

        }


 if(view==logout){

     final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity());
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

             UtilMethods.INSTANCE.logout(getActivity());
         }
     });

     alertDialog.show();

        }
    }


}