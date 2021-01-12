package com.cureu.Doctor.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cureu.Doctor.DOctoeByIdActivity;
import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.Dashbord.ui.DashboardMainNew;
import com.cureu.Doctor.Dashbord.ui.DoctorListDashboadAdapter;
import com.cureu.Doctor.Dashbord.ui.MainActivity;
import com.cureu.Doctor.Dashbord.ui.MedicineListAdapter;
import com.cureu.Doctor.Dashbord.ui.PreciptionActivity;
import com.cureu.Doctor.DocterList.DocterListActivity;
import com.cureu.Doctor.DoctorBookingAdapter;
import com.cureu.Doctor.Login.dto.Data;
import com.cureu.Doctor.Login.ui.OtpActivity;
import com.cureu.Doctor.PaymentGetway.ui.PaymentActivity;
import com.cureu.Doctor.Register.ui.RegisterActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.cureu.Doctor.Dashbord.ui.ActivityUserProfile;
import com.cureu.Doctor.Login.dto.secureLoginResponse;
import com.cureu.Doctor.Login.ui.LoginActivity;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Splash.ui.Splash;
import com.vincent.filepicker.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;

public enum UtilMethods {

    INSTANCE;

    public void FetchDrByName(final Context context, final String val) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("Createghazal", "usertoken   :  " + "token" + "     :   vall     " + val);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.FetchDrByName(header, val,0,200);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Createghazalres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {

                        try {


                            FragmentActivityMessage activityActivityMessage =
                                    new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "createghazal");
                            GlobalBus.getBus().post(activityActivityMessage);

                        } catch (Exception ex) {

                            //  UtilMethods.INSTANCE.Failed(context, " Invalid behar mein keep writing as per count below ", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {

                    //  Toast.makeText(context, "Invalid Behar mein keep writing as per count below", Toast.LENGTH_SHORT).show();


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void locationreposeval(Context context, String locationreposeval) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.locationreposeval, locationreposeval);
        editor.commit();

    }


    public String getRecentLogin(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String key = myPrefs.getString(ApplicationConstant.INSTANCE.regRecentLoginPref, null);
        return key;
    }

    public void setRecentLogin(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.regRecentLoginPref, key);
        editor.commit();
    }

    public void Processing(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Processing")
                .setContentText(message)
                .setCustomImage(R.drawable.processing)
                .show();
    }

    public void Successful(final Context context, final String message, final int i, final Activity register) {

        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.setTitleText(context.getResources().getString(R.string.successful_title));
        pDialog.setContentText(message);
        pDialog.setCustomImage(AppCompatResources.getDrawable(context, R.drawable.ic_tick));
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (i == 1) {

                    context.startActivity(new Intent(context, LoginActivity.class));
                    sweetAlertDialog.dismiss();
                    register.finish();

                } else if (i == 2) {

                    sweetAlertDialog.dismiss();
                    register.finish();

                } else {

                    sweetAlertDialog.dismiss();

                }
            }
        });
        pDialog.show();
    }

    public void Failed(final Context context, final String message, final int i) {

        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.setTitleText(context.getResources().getString(R.string.attention_error_title));
        pDialog.setContentText(message);
        pDialog.setCustomImage(AppCompatResources.getDrawable(context, R.drawable.ic_cancel_black_24dp));
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (i == 1) {

                    sweetAlertDialog.dismiss();

                   /* context.startActivity(new Intent(context, LoginActivity.class));
                    sweetAlertDialog.dismiss();*/
                } else if (i == 10) {

                    context.startActivity(new Intent(context, ActivityUserProfile.class));
                    sweetAlertDialog.dismiss();
                } else {
                    sweetAlertDialog.dismiss();
                }
            }
        });
        pDialog.show();
    }

    public void NetworkError(final Context context, String title, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setCustomImage(AppCompatResources.getDrawable(context, R.drawable.ic_connection_lost_24dp))
                .show();
    }

    public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    TextView textotp, resendotp, resend;
    public EditText edMobileFwp;
    public TextInputLayout tilMobileFwp;

    public Button FwdokButton;
    public Button cancelButton;
    private static CountDownTimer countDownTimer;


    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                resendotp.setText(hms);//set text
            }

            public void onFinish() {

                resendotp.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);

                countDownTimer = null;//set CountDownTimer to null
                //  resendotp.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }

    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    public void setLoginrespose(Context context, String Loginrespose, String one) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.Loginrespose, Loginrespose);
        editor.putString(ApplicationConstant.INSTANCE.one, one);
        editor.commit();

    }

    public void setnumber(Context context, String number) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.number, number);
        editor.commit();
    }

    public void SetType(Context context, String SetType) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.SetType, SetType);
        editor.commit();
    }

    public void secureLogin(final Context context, final String edMobile, final Loader loader, final Activity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("login", " , email : " + edMobile);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.secureLogin(header, edMobile);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {

                            if (response.body().getData() != null) {


                                Intent intent = new Intent(context, OtpActivity.class);
                                intent.putExtra("number", "" + edMobile);
                                intent.putExtra("otp", "" + response.body().getData().getOtp());
                                context.startActivity(intent);
                                loginActivity.finish();


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, "Sorry Wrong Password or Email...", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //  UtilMethods.INSTANCE.Failed(context,"please check login id and password",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DoctorLogin(final Context context, final String edMobile, final Loader loader, final LoginActivity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("login", " , email : " + edMobile);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.DoctorLogin(header, edMobile);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                if (!response.body().getData().getStatus().equalsIgnoreCase("0")) {

                                    Intent intent = new Intent(context, OtpActivity.class);
                                    intent.putExtra("number", "" + edMobile);
                                    intent.putExtra("otp", "" + response.body().getData().getOtp());
                                    context.startActivity(intent);
                                    loginActivity.finish();


                                } else {

                                    UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);
                                }


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            // UtilMethods.INSTANCE.Failed(context,"Sorry Wrong Password or Email...",0);

                            Intent intent = new Intent(context, OtpActivity.class);
                            intent.putExtra("number", "" + edMobile);
                            intent.putExtra("otp", "" + response.body().getData().getOtp());
                            context.startActivity(intent);
                            loginActivity.finish();

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //  UtilMethods.INSTANCE.Failed(context,"please check login id and password",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void OtpVerification(final Activity context, final String otp_user, final String number, final Loader loader, final OtpActivity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("login", " , email : " + otp_user + "  " + number);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.verify(header, number, otp_user);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                                if (response.body().getData().getName().equalsIgnoreCase("")) {

                                    // Toast.makeText(context, "1111111111111", Toast.LENGTH_SHORT).show();


                                    Intent intent = new Intent(context, RegisterActivity.class);
                                    intent.putExtra("number", "" + number);
                                    intent.putExtra("patient_id", "" + response.body().getData().getPatient_id());
                                    context.startActivity(intent);


                                } else {

                                    UtilMethods.INSTANCE.setLoginrespose(context, "" + new Gson().toJson(response.body().getData()).toString(), "1");
                                    UtilMethods.INSTANCE.setnumber(context, number);
                                    Intent intent = new Intent(context, DashboardMainNew.class);
                                    context.startActivity(intent);
                                    context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    loginActivity.finish();

                                }


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, "Sorry Wrong Password or Email...", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "please check login id and password", 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePatient(final Activity context, final String name, final String email, String patient_id, final String number,
                              final Loader loader, final Activity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("login", " , email : " + name + "  " + number + "  email  " + email);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.updatePatient(header, number, name, email, patient_id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                                UtilMethods.INSTANCE.secureLogin(context, number, loader, loginActivity);


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, "Sorry Wrong Password or Email...", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "please check login id and password", 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DoctorOtpVerify(final Activity context, final String otp_user, final String number, final Loader loader, final OtpActivity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("login", " , email : " + otp_user + "  " + number);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.DoctorOtpVerify(header, number, otp_user);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                UtilMethods.INSTANCE.setLoginrespose(context, "" + new Gson().toJson(response.body().getData()).toString(), "1");
                                UtilMethods.INSTANCE.setnumber(context, number);

                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
                                context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                loginActivity.finish();


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, "Sorry Wrong Password or Email...", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "please check login id and password", 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setconsult(final Context context, String consulttext, final Loader loader, final Activity activity) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();


        Log.e("getbanner", "   name   " + member_id + "   consulttext  " + consulttext);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.setconsult(header, consulttext, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getStatus().equalsIgnoreCase("1")) {

                                UtilMethods.INSTANCE.Successful(context, "" + response.body().getMessage(), 2, activity);


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Successful(context, "We will be back to you soon", 0, activity);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void AllSymtoms(final Context context, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();


        Log.e("getbanner", "   name   " + member_id + "   consulttext  " + "consulttext");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.AllSymtoms(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(ApplicationConstant.INSTANCE.allsympton, new Gson().toJson(response.body()).toString());
                                    editor.commit();
                                }
                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getbanner(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("getbanner", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.getbanner(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("GalleryList",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);

                            } else {

                                // UtilMethods.INSTANCE.Failed(context,""+response.body().getMessage(),0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Doctors(final Context context, String id, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctorsaaaaaaaaaa", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.Doctors(header, id,0,200);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                    intent.putExtra("type", "2");
                                    context.startActivity(intent);
                                } else {
                                    UtilMethods.INSTANCE.Failed(context, "No doctor found", 0);
                                }

                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetAvailability(final Context context, String id, String day, final Loader loader) {
        loader.show();
        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("GetAvailability", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAvailability(header, id, day);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("GetAvailabilityres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("Appointment",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);


                            } else {
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("Available",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                                Toast.makeText(context, "Doctor is not Available today", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void chkpayment(final Activity context, final String id, final String day, final Datum operator, final Loader loader, final String Symtom_id) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        Log.e("Doctors", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.chkpayment(header, id, member_id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {
                    Log.e("chkpayment", "is : " + new Gson().toJson(response.body()).toString()); if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            if (response.body().getData() != null) {
                                if (response.body().getData().getStatus().equalsIgnoreCase("0")) {

                                    Intent intent = new Intent(context, PaymentActivity.class);
                                    intent.putExtra("amount", "1000");
                                    intent.putExtra("namepack", "1");
                                    intent.putExtra("doctorid", id);
                                    intent.putExtra("Symtom_id", Symtom_id);
                                    intent.putExtra("doctorDetail", new Gson().toJson(operator));
                                    context.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                    intent.putExtra("response", "" + new Gson().toJson(operator));
                                    intent.putExtra("type", "1");
                                    intent.putExtra("doctorDetail", new Gson().toJson(operator));
                                    context.startActivity(intent);
                                    context.finish();
                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetPatientPreciption(final Context context, final String appointment_id, RecyclerView recycler_view_Preciption, final Loader loader, Datum operator) {

        String header = ApplicationConstant.INSTANCE.Headertoken;
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String userId = balanceCheckResponse.getPatient_id();

        Log.e("GetPatientPreciption", "   name   " + userId + "  appointment_id   :  " + appointment_id);

        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.GetPatientPreciption(header, userId, appointment_id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("GetPatientPreciptionres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                Intent intent = new Intent(context, PreciptionActivity.class);
                                intent.putExtra("response", "" + new Gson().toJson(response.body().getData()).toString());
                                intent.putExtra("doctorDetail", new Gson().toJson(operator));
                                context.startActivity(intent);


                                //  intent.putExtra("doctorDetail", new Gson().toJson(operator));
                                //     UtilMethods.INSTANCE.GetMedicineById(context,response.body().getData().getMedicine_id(),null,recycler_view_Preciption);


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetPatientPreciptionhide(final Context context, final String appointment_id, TextView View_Preciption) {

        String header = ApplicationConstant.INSTANCE.Headertoken;
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String userId = balanceCheckResponse.getPatient_id();

        Log.e("GetPatientPreciption", "   name   " + userId + "  appointment_id   :  " + appointment_id);

        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.GetPatientPreciption(header, userId, appointment_id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("GetPatientPreciptionres", "is : " + new Gson().toJson(response.body()).toString() + "  mmmm   :  " + response.body().getData());


                    if (response != null) {

                        try {

                            if (response.body().getData() != null) {

                                View_Preciption.setVisibility(View.VISIBLE);

                            } else {

                                // View_Preciption.setVisibility(View.GONE);

                                View_Preciption.setClickable(false);
                                View_Preciption.setText("  No Prescription");
                                View_Preciption.setTextColor(context.getResources().getColor(R.color.red));

                            }


                        } catch (Exception ex) {

                            //  View_Preciption.setVisibility(View.GONE);
                            View_Preciption.setClickable(false);
                            View_Preciption.setText("  No Prescription");
                            View_Preciption.setTextColor(context.getResources().getColor(R.color.red));


                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    //   View_Preciption.setVisibility(View.GONE);
                    View_Preciption.setClickable(false);
                    View_Preciption.setText("  No Prescription");
                    View_Preciption.setTextColor(context.getResources().getColor(R.color.red));


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Paymentsusses(final Context context, final String DOctorid, String id, final Loader loader, String amount, final Datum operator, final String Symtom_id) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        Log.e("Doctors", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.Paymentsusses(header, "" + DOctorid, "" + amount, "" + member_id, "xyxyxyrgcv ", "1");
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                                if (response.body().getData().getStatus().equalsIgnoreCase("1")) {


                                    UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 0, null);


                                    Loader loader;
                                    loader = new Loader(context, android.R.style.Theme_Translucent_NoTitleBar);


                                    if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {

                                        loader.show();
                                        loader.setCancelable(false);
                                        loader.setCanceledOnTouchOutside(false);
                                        //  UtilMethods.INSTANCE.GetAvailability(context, DOctorid,"Monday",operator , loader,Symtom_id);
                                    } else {
                                        UtilMethods.INSTANCE.NetworkError(context, context.getResources().getString(R.string.network_error_title),
                                                context.getResources().getString(R.string.network_error_message));
                                    }


                                } else {
                                    UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);

                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void AllSymtimsDoctors(final Context context, String id, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.AllSymtimsDoctors(header, id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                                Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                intent.putExtra("type", "3");

                                context.startActivity(intent);

                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    MedicineListAdapter medicinelistadapter;
    LinearLayoutManager mLayoutManager;


    public void GetMedicineById(final Context context, String medicine_id, final Loader loader, RecyclerView recycler_view_Preciption) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + medicine_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetMedicineById(header, medicine_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                String responsenew = new Gson().toJson(response.body()).toString();

                                Gson gson = new Gson();
                                sliderImage = gson.fromJson(responsenew, GalleryListResponse.class);
                                sliderLists = sliderImage.getData();

                                if (sliderLists.size() > 0) {
                                    medicinelistadapter = new MedicineListAdapter(sliderLists, context);
                                    mLayoutManager = new LinearLayoutManager(context);
                                    recycler_view_Preciption.setLayoutManager(mLayoutManager);
                                    recycler_view_Preciption.setItemAnimator(new DefaultItemAnimator());
                                    recycler_view_Preciption.setAdapter(medicinelistadapter);
                                    recycler_view_Preciption.setVisibility(View.VISIBLE);
                                } else {
                                    recycler_view_Preciption.setVisibility(View.GONE);
                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetAllPatientAppointment(final Context context, final Loader loader) {

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllPatientAppointment(header, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {
                                Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                intent.putExtra("type", "4");
                                context.startActivity(intent);
                            } else {

                                UtilMethods.INSTANCE.Failed(context, "No Data Found", 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetAllPatientAppointment2(final Context context, final Loader loader) {

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllPatientAppointment(header, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {
//                                Intent intent = new Intent(context, DOctoeByIdActivity.class);
//                                intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
//                                intent.putExtra("type", "4");
//                                context.startActivity(intent);
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("Doctorsres",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                            } else {

                                UtilMethods.INSTANCE.Failed(context, "No Data Found", 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void SetAvailability(final Context context, String id, String start_time, String end_time, String date, final Loader loader) {


        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctorsvvv", "   name   " + member_id + "   vv   " + start_time + "    vv   " + end_time + "  dd    " + date);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.SetAvailability(header, member_id, start_time, end_time, date);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("Doctorsvvvres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {

                                UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 0, null);

                            } else {

                                UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BookAppointment(final Context context, String doctor_id, String slot_id, String date, final Loader loader, String patient_details, String no_of_patient) {


        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("BookAppointment", "   name   " + member_id + "   vv   " + doctor_id + "    vv   " + slot_id + "  dd    " +
                date + "  symtom_id    " + patient_details);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.BookAppointment(header, doctor_id, slot_id, member_id, patient_details, no_of_patient, date);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("BookAppointment", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {

                                UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 0, null);

                            } else {

                                UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doctorlist(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("specialities", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.doctorlist(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("specialitiesres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(ApplicationConstant.INSTANCE.allCategoryDoctor, new Gson().toJson(response.body()).toString());
                                    editor.commit();
                                }
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("specialities",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void logout(final Context context) {

        UtilMethods.INSTANCE.setLoginrespose(context, "", "");

        Intent startIntent = new Intent(context, Splash.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(startIntent);

    }

    public boolean isValidMobile(String mobile) {

        String mobilePattern = "^(?:0091|\\\\+91|0)[6-9][0-9]{9}$";
        String mobileSecPattern = "[6-9][0-9]{9}$";

        if (mobile.matches(mobilePattern) || mobile.matches(mobileSecPattern)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public void setgetKeyId(Context context, String token) {


        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.token, token);
        editor.commit();


    }
}