package com.cureu.Doctor.Login.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;


public class OtpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt2;
    String number="";
    String otp="";

    public RelativeLayout otp_submit;
    Loader loader;
    EditText otp_ed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        number=getIntent().getStringExtra("number");
        otp=getIntent().getStringExtra("otp");

        otp_submit=findViewById(R.id.otp_submit);
        otp_ed=findViewById(R.id.otp_ed);
        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);

        otp_submit.setOnClickListener(this);

        txt2=findViewById(R.id.txt2);
        txt2.setText("We have sent you an SMS on +91-"+" "+ number +" \n with a 4 digit verification code");

        otp_ed.setText(""+otp);


    }


    @Override
    public void onClick(View view) {

        if(view==otp_submit){

            if(otp.equalsIgnoreCase(otp_ed.getText().toString().trim())){


                if (UtilMethods.INSTANCE.isNetworkAvialable(OtpActivity.this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String SetType = ""+myPreferences.getString(ApplicationConstant.INSTANCE.SetType, null);

                   // Toast.makeText(this, "ss  "+ SetType, Toast.LENGTH_SHORT).show();

                    if(SetType.equalsIgnoreCase("1")){

                        UtilMethods.INSTANCE.OtpVerification(OtpActivity.this, otp_ed.getText().toString().trim(),number,loader,this);

                    }else if(SetType.equalsIgnoreCase("2")){

                        UtilMethods.INSTANCE.DoctorOtpVerify(OtpActivity.this, otp_ed.getText().toString().trim(),number,loader,this);

                    }else {


                    }



                } else {
                    UtilMethods.INSTANCE.NetworkError(OtpActivity.this, getResources().getString(R.string.network_error_title),
                            getResources().getString(R.string.network_error_message));
                }

            }else {


                Toast.makeText(this, "otp does not match", Toast.LENGTH_SHORT).show();
            }






        }

    }
}