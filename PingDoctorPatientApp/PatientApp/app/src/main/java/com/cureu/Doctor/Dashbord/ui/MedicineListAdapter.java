package com.cureu.Doctor.Dashbord.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.R;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;

import java.util.ArrayList;


public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title,countpage;
        LinearLayout idCardView;
        ImageView opImage;


        public MyViewHolder(View view) {
            super(view);
            countpage =  view.findViewById(R.id.countpage);
            tv_title =  view.findViewById(R.id.tv_title);
             opImage =  view.findViewById(R.id.opImage);
            idCardView =  view.findViewById(R.id.idCardView);

        }
    }

    public MedicineListAdapter(ArrayList<Datum> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MedicineListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medivine_list_adapter, parent, false);

        return new MedicineListAdapter.MyViewHolder(itemView);

    }



    @Override
    public void onBindViewHolder(final MedicineListAdapter.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);

        int count =position+1;
        holder.countpage.setText(""+count +". ");

        holder.tv_title.setText(" " + operator.getMedicine_name().replace("\"",""));


    }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}