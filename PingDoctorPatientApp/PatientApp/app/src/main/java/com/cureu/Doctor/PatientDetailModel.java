package com.cureu.Doctor;

import android.view.View;

public class PatientDetailModel {
   private String PatientName;
    private String PatientMobile;
    private String ClientMobile;
    private String Clientemail;

    public PatientDetailModel(String patientName, String patientMobile, String clientMobile, String clientemail, String patientAge) {
        PatientName = patientName;
        PatientMobile = patientMobile;
        ClientMobile = clientMobile;
        Clientemail = clientemail;
        PatientAge = patientAge;
    }

    private String PatientAge;
}
