package com.cureu.Doctor;

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
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;

import java.util.ArrayList;


public class AllSymtomsAdapter extends RecyclerView.Adapter<AllSymtomsAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;


    public void filter(ArrayList<Datum> newlist) {
        transactionsList=new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title,tv_discuraption;
        LinearLayout idCardView;


        public MyViewHolder(View view) {
            super(view);
            tv_title =  view.findViewById(R.id.tv_title);
            tv_discuraption =  view.findViewById(R.id.tv_discuraption);
             idCardView =  view.findViewById(R.id.idCardView);

        }
    }

    public AllSymtomsAdapter(ArrayList<Datum> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public AllSymtomsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allsymtoms_adapter, parent, false);

        return new AllSymtomsAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final AllSymtomsAdapter.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);

        holder.tv_title.setText("" + operator.getSymtom());
        holder.tv_discuraption.setText("" + operator.getDescription());





        holder.idCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader;
                loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);


                if (UtilMethods.INSTANCE.isNetworkAvialable( mContext)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);


                     UtilMethods.INSTANCE.AllSymtimsDoctors(mContext, operator.getId(), loader);



                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                            mContext.getResources().getString(R.string.network_error_message));
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}