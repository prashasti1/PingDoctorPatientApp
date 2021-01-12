package com.cureu.Doctor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Login.dto.Data;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.gson.Gson;

public class PatientDetail extends AppCompatActivity {
    public TextView name, specialities, address, qualification, tv_datetime;
    ImageView opImage;
    RadioButton self, other;
    Button submit;
    com.google.android.material.textfield.TextInputEditText PatientName,PatientMobile,Patientage,ClientMobile,Clientemail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        Datum patientDetail = new Gson().fromJson(getIntent().getStringExtra("Doctordetail"), Datum.class);
        String DateTile = getIntent().getStringExtra("dateTime");
        name = findViewById(R.id.name);
        tv_datetime = findViewById(R.id.tv_datetime);
        qualification = findViewById(R.id.qualification);
        address = findViewById(R.id.address);

        PatientName = findViewById(R.id.PatientName);
        PatientMobile = findViewById(R.id.PatientMobile);
        Patientage = findViewById(R.id.Patientage);
        ClientMobile = findViewById(R.id.ClientMobile);
        Clientemail = findViewById(R.id.Clientemail);
        submit = findViewById(R.id.submit);

        name.setText("Dr. " + patientDetail.getName());
        qualification.setText("" + patientDetail.getTypeName());
        opImage = findViewById(R.id.opImage);
        self = findViewById(R.id.self);
        other = findViewById(R.id.other);
        self.setChecked(true);
        opImage = findViewById(R.id.opImage);
        address.setText("" + patientDetail.getExperience() + " years experi" +
                "ance");
        tv_datetime.setText("" + DateTile);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.doctordemo);
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        String Mobile = myPreferences.getString(ApplicationConstant.INSTANCE.number, "");
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        PatientName.setText(balanceCheckResponse.getName());
        PatientMobile.setText(Mobile);
        PatientMobile.setVisibility(View.GONE);
        ClientMobile.setText(Mobile);
        ClientMobile.setEnabled(false);
        Clientemail.setText(balanceCheckResponse.getEmail());

        Glide.with(this)
                .load(patientDetail.getProfilePic())
                .apply(requestOptions)
                .into(opImage);
        self.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
                    String Mobile = myPreferences.getString(ApplicationConstant.INSTANCE.number, "");
                    Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
                    PatientName.setText(balanceCheckResponse.getName());
                    PatientMobile.setText(balanceCheckResponse.getMobile());
                    PatientMobile.setVisibility(View.GONE);
                    ClientMobile.setText(Mobile);
                    Clientemail.setText(balanceCheckResponse.getEmail());
                }

            }
        });
        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
                    Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
                    String Mobile = myPreferences.getString(ApplicationConstant.INSTANCE.number, "");
                    PatientName.setText("");
                    PatientMobile.setText("");
                    PatientMobile.setVisibility(View.VISIBLE);
                    ClientMobile.setText(Mobile);
                    Clientemail.setText(balanceCheckResponse.getEmail());
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PatientName.getText().toString().isEmpty()){
                    PatientName.setError("Please insert patient name");
                    return;
                }if(Patientage.getText().toString().isEmpty()){
                    Patientage.setError("Please insert patient age");
                    return;
                }if(PatientMobile.getText().toString().isEmpty()){
                    PatientMobile.setError("Please insert patient Mobile No");
                    return;
                }
                if (ClientMobile.getText().toString().isEmpty()) {
                    ClientMobile.setError("Please insert patient Mobile No");
                    return;
                }
                if (UtilMethods.INSTANCE.isNetworkAvialable(PatientDetail.this)) {
                    Datum patAppointmentDetail = new Gson().fromJson(getIntent().getStringExtra("AppintmentDetail"), Datum.class);
                    PatientDetailModel patientDetailModel=new PatientDetailModel(PatientName.getText().toString(),
                            PatientMobile.getText().toString(),
                            ClientMobile.getText().toString(),Clientemail.getText().toString(),Patientage.getText().toString());
                   Loader loader = new Loader(PatientDetail.this, android.R.style.Theme_Translucent_NoTitleBar);
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.BookAppointment(PatientDetail.this, patAppointmentDetail.getDoctor_id(), patAppointmentDetail.getId(),
                                tv_datetime.getText().toString().trim(), loader, new Gson().toJson(patientDetailModel), patAppointmentDetail.getNo_of_patient());
                    } else {
                        UtilMethods.INSTANCE.NetworkError(PatientDetail.this, getResources().getString(R.string.network_error_title),
                                PatientDetail.this.getResources().getString(R.string.network_error_message));
                    }
            }
        });
    }
}
