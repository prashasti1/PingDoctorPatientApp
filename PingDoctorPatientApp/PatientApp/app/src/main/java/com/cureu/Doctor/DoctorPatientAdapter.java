package com.cureu.Doctor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;

import java.util.ArrayList;
import java.util.Calendar;


public class DoctorPatientAdapter extends RecyclerView.Adapter<DoctorPatientAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;

    private int mYear, mMonth, mDay, mHour, mMinute;



    public void filter(ArrayList<Datum> newlist) {
        transactionsList=new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title,tv_discuraption,craetedate,enddate,View_Preciption,book_date;
        RecyclerView recycler_view_Preciption;



        public MyViewHolder(View view) {
            super(view);
            tv_title =  view.findViewById(R.id.tv_title);
            tv_discuraption =  view.findViewById(R.id.tv_discuraption);
            craetedate =  view.findViewById(R.id.craetedate);
            enddate =  view.findViewById(R.id.enddate);
            tv_discuraption =  view.findViewById(R.id.tv_discuraption);
            recycler_view_Preciption =  view.findViewById(R.id.recycler_view_Preciption);
            View_Preciption =  view.findViewById(R.id.View_Preciption);
            book_date =  view.findViewById(R.id.book_date);


        }
    }

    public DoctorPatientAdapter(ArrayList<Datum> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public DoctorPatientAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patiend_booking_adapter, parent, false);

        return new DoctorPatientAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final DoctorPatientAdapter.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);

        // +" [  " +operator.getExperience()+"  ]"

        holder.tv_title.setText("Dr.  " + operator.getDoctor_name() );
        holder.enddate.setText("" + operator.getEnd_time());
        holder.craetedate.setText("" + operator.getStart_time());
        holder.tv_discuraption.setText("" + operator.getExperience());
        holder.book_date.setText(" Book Date  :  " + operator.getBook_date());

        UtilMethods.INSTANCE.GetPatientPreciptionhide(mContext,operator.getAppointment_id(),holder.View_Preciption);

        holder.View_Preciption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader;
                loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);




                UtilMethods.INSTANCE.GetPatientPreciption(mContext,operator.getAppointment_id(),holder.recycler_view_Preciption,loader,operator);



            }
        });


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}