package com.cureu.Doctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.FragmentActivityMessage;
import com.cureu.Doctor.Utils.GlobalBus;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class AllSymtomsActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    Loader loader;
    AllSymtomsAdapter mAdapter;

    GalleryListResponse sliderImage;
    ArrayList<Datum> operator = new ArrayList<>();
    android.widget.SearchView SearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchnew);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getid();

    }

    private void getid() {

        recycler_view = findViewById(R.id.recycler_view);
        SearchView = findViewById(R.id.searchghazal);
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
        String str = prefs.getString(ApplicationConstant.INSTANCE.allsympton, "");
        getOperatorList(str);

    }

    public void getOperatorList(String response) {
        sliderImage = new Gson().fromJson(response, GalleryListResponse.class);
        operator = sliderImage.getData();

        if (operator != null && operator.size() > 0) {
            mAdapter = new AllSymtomsAdapter(operator, this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
            SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(operator.contains(query)){
                      String  newText = query.toLowerCase();
                        ArrayList<Datum> newlist = new ArrayList<>();
                        for (Datum op : operator) {
                            String getName = op.getSymtom().toLowerCase();
                            if (getName.contains(newText)) {
                                newlist.add(op);

                            }
                        }
                        mAdapter.filter(newlist);
                    }else{
                        Toast.makeText(AllSymtomsActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = newText.toLowerCase();
                    ArrayList<Datum> newlist = new ArrayList<>();
                    for (Datum op : operator) {
                        String getName = op.getSymtom().toLowerCase();
                        if (getName.contains(newText)) {
                            newlist.add(op);

                        }
                    }
                    mAdapter.filter(newlist);
                    return false;
                }
            });
        } else {


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

}