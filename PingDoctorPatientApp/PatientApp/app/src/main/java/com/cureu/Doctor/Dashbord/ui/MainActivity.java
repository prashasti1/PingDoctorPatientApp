package com.cureu.Doctor.Dashbord.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cureu.Doctor.DoctorDashboad.SchedulingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rampo.updatechecker.UpdateChecker;
 import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.GooglePlayStoreAppVersionNameLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    String version="";
    String versionName="";
    int versionCode;
    CircleImageView userpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);


        version= GooglePlayStoreAppVersionNameLoader.newVersion;

        getVersionInfo();

        PopUpdate();

        //loading the default fragment
        loadFragment(new SchedulingFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


    }

    private  void PopUpdate() {

        Log.e("version","    versionName    "+versionName +"  version    "+version );

        if(version!=null && !version.equalsIgnoreCase("")){

            if(!versionName.equalsIgnoreCase(version)){

               OpenUpdateDialog();

            }
        }
    }

    public void OpenUpdateDialog() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.update_available_pop, null);

        TextView tvLater = (TextView) view.findViewById(R.id.tv_later);
        TextView tvOk=(TextView)view.findViewById(R.id.tv_ok);

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMarket(MainActivity.this);
                dialog.dismiss();
                //UtilMethods.INSTANCE.goAnotherActivity((Activity) context,Splash.class);
            }
        });

        dialog.show();
    }

    private static void goToMarket(Context mContext) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UpdateChecker.ROOT_PLAY_STORE_DEVICE + mContext.getPackageName())));
    }

    private void getVersionInfo() {

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

            Log.e("versionnn","   versionName   "+versionName+"   versionCode  "+  versionCode+"   version  "+  version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {


            case R.id.home:

                fragment = new SchedulingFragment();

                break;

            case R.id.Appointmenthistory:

                fragment = new Appointmentt_history_Fragment();

                break;


            case R.id.walletlist:

               // fragment = new Wallet_list_Fragment();

                break;

            case R.id.Setting:

                fragment = new ProfileFragment();

                break;
        }



     return loadFragment(fragment);

     }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}


