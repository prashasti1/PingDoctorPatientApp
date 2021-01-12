package com.cureu.Doctor.PaymentGetway.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Login.dto.Data;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener , View.OnClickListener {

    RelativeLayout button ;

    String amount="";
    String doctorDetail="";
    String namepack="";
    String doctorid="";
    String Symtom_id="";
    String contectnumber="";
    String emailuser="";
    TextView PackageName,tv_amount,tvcredit;
    Loader loader;

    Datum operator;

    public TextView name, specialities, address, qualification;
    ImageView opImage;


    private static final String TAG = PaymentActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_getway);


        Getid();


    }

    private void Getid() {

        loader = new Loader(PaymentActivity.this, android.R.style.Theme_Translucent_NoTitleBar);

        PackageName=findViewById(R.id.PackageName);
        tv_amount=findViewById(R.id.amount);
        tvcredit=findViewById(R.id.tvcredit);

        name = findViewById(R.id.name);
        specialities = findViewById(R.id.specialities);
        qualification = findViewById(R.id.qualification);
        address = findViewById(R.id.address);
        opImage = findViewById(R.id.opImage);


        amount=getIntent().getStringExtra("amount");
        doctorDetail=getIntent().getStringExtra("doctorDetail");
        namepack=getIntent().getStringExtra("namepack");
        doctorid=getIntent().getStringExtra("doctorid");
        Symtom_id=getIntent().getStringExtra("Symtom_id");


        Gson gson = new Gson();
          operator = gson.fromJson(doctorDetail, Datum.class);
        name.setText("" + operator.getName());
        qualification.setText("" + operator.getQualification());
        address.setText("" + operator.getAddress());
        specialities.setText("" + operator.getTypeName());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.doctordemo);


        Glide.with(this)
                .load(operator.getProfilePic())
                .apply(requestOptions)
                .into(opImage);


        PackageName.setText(" Package Name  :   "+  namepack );
        tv_amount.setText(" Package Amount  :   "+  amount );

        tvcredit.setVisibility(View.GONE);

        Checkout.preload(getApplicationContext());
        button =  findViewById(R.id.btn_pay);
        button.setOnClickListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Payment Activity");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

        contectnumber = balanceCheckResponse.getMobile();
        emailuser = balanceCheckResponse.getEmail();


    }

    @Override
    public void onClick(View view) {

        startPayment();

    }

    public void startPayment() {

        int   amount_toPay = Integer.parseInt(amount);

        final Activity activity = this;
        final Checkout co = new Checkout();

        float amountRupees = Float.valueOf(amount_toPay)*100;


        try {
            JSONObject options = new JSONObject();
            options.put("name", "Ping Doctor");
            options.put("description", "   Ping Doctor " );
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amountRupees);
            JSONObject preFill = new JSONObject();
            preFill.put("email", ""+emailuser);
            preFill.put("contact", ""+contectnumber);
            options.put("prefill", preFill);
            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }

        HitApiSusses(razorpayPaymentID);
    }

    private void HitApiSusses(String razorpayPaymentID) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {


            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.Paymentsusses(this, ""+doctorid,""+razorpayPaymentID, loader,amount,operator,Symtom_id);


        } else {
            UtilMethods.INSTANCE.NetworkError(this, this.getResources().getString(R.string.network_error_title),
                    this.getResources().getString(R.string.network_error_message));
        }



    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }


}
