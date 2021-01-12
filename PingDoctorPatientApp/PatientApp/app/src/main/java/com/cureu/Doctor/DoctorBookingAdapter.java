package com.cureu.Doctor;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cureu.Doctor.Dashbord.dto.Datum;
import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.Utils.ApplicationConstant;
import com.cureu.Doctor.Utils.Loader;
import com.cureu.Doctor.Utils.UtilMethods;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DoctorBookingAdapter extends RecyclerView.Adapter<DoctorBookingAdapter.MyViewHolder>  implements DatePickerDialog.OnDateSetListener {

    private ArrayList<Datum> transactionsList;
    private Activity mContext;
    String Symtom_id = "";
    int appDay=0;
    Calendar[] calendars=new Calendar[100];
    FragmentManager fragmentManager;
    private String currentDay;
Datum doctorDetail;

    public void filter(ArrayList<Datum> newlist) {
        transactionsList = new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public LinearLayout ll;


        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            ll = view.findViewById(R.id.ll);
        }
    }

    public DoctorBookingAdapter(ArrayList<Datum> transactionsList, Activity mContext, String Symtom_id,FragmentManager fragmentManager,String curentDay,Datum doctorDetail) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.Symtom_id = Symtom_id;
        this.fragmentManager=fragmentManager;
        this.currentDay=curentDay;
        this.doctorDetail=doctorDetail;
    }

    @Override
    public DoctorBookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.finftimebooking_adapter, parent, false);

        return new DoctorBookingAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final DoctorBookingAdapter.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);
        //  ArrayList<Datum> sliderLists = sliderImage.getData();
        int patient =Integer.parseInt(operator.getNo_of_patient());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date startdate= null;
        holder.ll.removeAllViews();
        try {
            startdate = sdf.parse(operator.getStart_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time=startdate.getTime();
        Log.e("time:",time+"");
        Date enddate = null;
        try {
            enddate = sdf.parse(operator.getEnd_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long endddate = enddate.getTime();
        Log.e("time:endddate:", endddate + "");
        long slot=(endddate-time)/patient;
        Log.e("time:slot:", slot + "");
        long minutes = TimeUnit.MILLISECONDS.toMinutes(slot);
        Log.e("time:minutes:", minutes + "");
        String sTime="";
        long lotTime=startdate.getTime();
        LinearLayout linearLayout=new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        for(int i=0;i<patient;i++){
            lotTime=lotTime+slot;
            Date dat = null;
            try {
                dat = new Date(lotTime);
                String slotTime;
               int hourOfDay= dat.getHours();
               int minute= dat.getMinutes();
               String format;
                if (hourOfDay == 0) {
                    hourOfDay += 12;
                    format = " AM";
                    format = "";
                } else if (hourOfDay == 12) {
                    format = "";
                    format = " PM";
                } else if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    format = " PM";
                } else {
                    format = " AM";
                }

                if (minute < 10)
                    slotTime=   hourOfDay + ":0" + minute + format;
                else
                slotTime=hourOfDay + ":" + minute + format;
                TextView textView=new TextView(mContext);
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.rectsearch));
                textView.setPadding(10,10,10,10);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(10, 5, 10, 5);
                textView.setLayoutParams(params);
                textView.setText(slotTime);
                textView.setTextSize(13);
                textView.setTag(slotTime);
                sTime=sTime+slotTime+"  ";
                    holder.ll.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(mContext,PatientDetail.class);
                        i.putExtra("dateTime",currentDay+" "+textView.getText().toString());
                        i.putExtra("Doctordetail",new Gson().toJson(doctorDetail));
                        i.putExtra("AppintmentDetail",new Gson().toJson(operator));
                        mContext.startActivity(i);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      //  holder.tv_title.setText(sTime);
//        final Calendar today = Calendar.getInstance();
//        final Calendar twoDaysLater = (Calendar) today.clone();
//        twoDaysLater.add(Calendar.DATE, 15);
//
//        holder.date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // Get Current Date
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//                int day = c.get(Calendar.DAY_OF_WEEK);
//                switch (day) {
//                    case Calendar.MONDAY:
//                        appDay = 2;
//                        break;
//                    case Calendar.TUESDAY:
//                        appDay = 3;
//                        break;
//                    case Calendar.WEDNESDAY:
//                        appDay = 4;
//                        break;
//                    case Calendar.THURSDAY:
//                        appDay = 5;
//                        break;
//                    case Calendar.FRIDAY:
//                        appDay = 6;
//                        break;
//                    case Calendar.SATURDAY:
//                        appDay = 7;
//                        break;
//                    case Calendar.SUNDAY:
//                        appDay = 1;
//                        break;
//                    default:
//                        appDay = 0;
//                        break;
//                }
//                Calendar now = Calendar.getInstance();
//                DatePickerDialog dpd = DatePickerDialog.newInstance(
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                     holder.date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth+"");
//                            }
//                        },
//                        now.get(Calendar.YEAR), // Initial year selection
//                        now.get(Calendar.MONTH), // Initial month selection
//                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
//                );                Calendar calendar1 = Calendar.getInstance();
//
//                int today = currentDay;
//                if ( appDay == today)
//                {
//                    int update = 0;
//                    for (int i = 0; i < calendars.length; i++)
//                    {
//                        final Calendar calendar2 = Calendar.getInstance();
//                        calendar2.get(Calendar.DAY_OF_WEEK);
//                        calendar2.add(Calendar.DATE, update);
//                        calendars[i] = calendar2;
//                        update += 7;
//                    }
//                }
//                else if ( today > appDay)
//                {
//                    int for1 = today - appDay;
//                    for (int i = 0; i < calendars.length; i++)
//                    {
//                        final Calendar calendar2 = Calendar.getInstance();
//                        calendar2.get(Calendar.DAY_OF_WEEK);
//                        calendar2.add(Calendar.DATE, for1);
//                        calendars[i] = calendar2;
//                        for1 += 7;
//                    }
//                }
//                else if ( today < appDay)
//                {
//                    int for2 = (7-appDay)+today;
//                    for (int i = 0; i < calendars.length; i++)
//                    {
//                        final Calendar calendar2 = Calendar.getInstance();
//                        calendar2.get(Calendar.DAY_OF_WEEK);
//                        calendar2.add(Calendar.DATE, for2);
//                        calendars[i] = calendar2;
//                        for2 += 7;
//                    }
//                }
//                dpd.setSelectableDays(calendars);
//                dpd.show(fragmentManager,"fragmentManager");
//
//
//
//
//
//            }
//        });
//
//
//        holder.bookcontinew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!holder.date.getText().toString().trim().equalsIgnoreCase("")) {
//
//                    Loader loader;
//                    loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
//
//
//                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
//                        loader.show();
//                        loader.setCancelable(false);
//                        loader.setCanceledOnTouchOutside(false);
//                        UtilMethods.INSTANCE.BookAppointment(mContext, operator.getDoctor_id(), operator.getId(),
//                                holder.date.getText().toString().trim(), loader, Symtom_id, operator.getNo_of_patient());
//                    } else {
//                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
//                                mContext.getResources().getString(R.string.network_error_message));
//                    }
//
//                } else {
//                    Toast.makeText(mContext, "Appointment Date Select", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}