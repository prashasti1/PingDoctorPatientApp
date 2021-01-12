package com.cureu.Doctor.Dashbord.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cureu.Doctor.DOctoeByIdActivity;
import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.DocterList.DoctorBookingActivity;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DoctorListDashboadAdapter extends RecyclerView.Adapter<DoctorListDashboadAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Activity mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, specialities, address, qualification,expwrence;
        ImageView opImage;
        Button idCardView, id_chatnow;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            specialities = view.findViewById(R.id.specialities);
            qualification = view.findViewById(R.id.qualification);
            address = view.findViewById(R.id.address);
            opImage = view.findViewById(R.id.opImage);
            idCardView = view.findViewById(R.id.idCardView);
            id_chatnow = view.findViewById(R.id.id_chatnow);
            expwrence = view.findViewById(R.id.expwrence);

        }
    }

    public DoctorListDashboadAdapter(ArrayList<Datum> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public DoctorListDashboadAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_dashboad_adapter, parent, false);

        return new DoctorListDashboadAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final DoctorListDashboadAdapter.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);

        holder.name.setText("Dr. " + operator.getName());
        holder.qualification.setText("" + operator.getTypeName());
        holder.address.setText("" + operator.getAddress());
        holder.specialities.setText("" + operator.getTypeName());
        holder.expwrence.setText("" + operator.getExperience()+" years experiance");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.doctordemo);


        Glide.with(mContext)
                .load(operator.getProfilePic())
                .apply(requestOptions)
                .into(holder.opImage);

        holder.idCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DOctoeByIdActivity.class);
                intent.putExtra("response", "" + new Gson().toJson(operator));
                intent.putExtra("type", "1");
                intent.putExtra("doctorDetail", new Gson().toJson(operator));
                mContext.startActivity(intent);
                mContext.finish();

//                Loader loader;
//                loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
//
//
//                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
//
//                    loader.show();
//                    loader.setCancelable(false);
//                    loader.setCanceledOnTouchOutside(false);
//
//                    Calendar calendar = Calendar.getInstance();
//                    Date date = calendar.getTime();
//                    UtilMethods.INSTANCE.chkpayment(mContext, operator.getId(), new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()), operator, loader, operator.getSymtom_id());
//
//                } else {
//                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
//                            mContext.getResources().getString(R.string.network_error_message));
//                }




            }
        });


        holder.id_chatnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.e("getMobile", "" + operator.getMobile());

                String url = "https://api.whatsapp.com/send?phone=" + "+91" + operator.getMobile();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}