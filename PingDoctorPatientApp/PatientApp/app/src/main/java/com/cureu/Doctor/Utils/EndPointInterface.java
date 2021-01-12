package com.cureu.Doctor.Utils;

import com.cureu.Doctor.Dashbord.dto.GalleryListResponse;
import com.cureu.Doctor.Login.dto.secureLoginResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EndPointInterface {

    @FormUrlEncoded
    @POST("/api/Login")
    Call<secureLoginResponse> secureLogin(@Header("X-API-KEY") String authorization,
                                          @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("/api/DoctorLogin")
    Call<secureLoginResponse> DoctorLogin(@Header("X-API-KEY") String authorization,
                                          @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("/api/OtpVerify")
    Call<secureLoginResponse> verify(@Header("X-API-KEY") String authorization,
                                     @Field("mobile") String mobile,
                                     @Field("otp") String otp);

    @FormUrlEncoded
    @POST("/api/OtpVerify/updatePatient")
    Call<secureLoginResponse> updatePatient(@Header("X-API-KEY") String authorization,
                                            @Field("mobile") String mobile,
                                            @Field("name") String name,
                                            @Field("email") String email,
                                            @Field("patient_id") String patient_id);


    @FormUrlEncoded
    @POST("/api/DoctorLogin/DoctorOtpVerify")
    Call<secureLoginResponse> DoctorOtpVerify(@Header("X-API-KEY") String authorization,
                                              @Field("mobile") String mobile,
                                              @Field("otp") String otp);


    @GET("/api/AllTypes")
    Call<GalleryListResponse> doctorlist(@Header("X-API-KEY") String authorization);


    @GET("/api/Doctors")
    Call<GalleryListResponse> Doctors(@Header("X-API-KEY") String authorization,
                                      @Query("type_id") String id,
                                      @Query("start") Integer start,
                                      @Query("limit") Integer limit
    );

    @GET("/api/Availability/GetAvailability")
    Call<GalleryListResponse> GetAvailability(@Header("X-API-KEY") String authorization,
                                              @Query("doctor_id") String id,
                                              @Query("day") String day);


    @GET("/api/Patient/GetPatientPreciption")
    Call<secureLoginResponse> GetPatientPreciption(@Header("X-API-KEY") String authorization,
                                                   @Query("userId") String userId,
                                                   @Query("appointment_id") String appointment_id);

    @GET("/api/Preciption/GetMedicineById")
    Call<GalleryListResponse> GetMedicineById(@Header("X-API-KEY") String authorization,
                                              @Query("medicine_id") String medicine_id);


    @GET("/api/Payment/chkpayment")
    Call<secureLoginResponse> chkpayment(@Header("X-API-KEY") String authorization,
                                         @Query("doctor_id") String doctor_id,
                                         @Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("/api/Payment")
    Call<secureLoginResponse> Paymentsusses(@Header("X-API-KEY") String authorization,
                                            @Field("doctor_id") String doctor_id,
                                            @Field("amount") String amount,
                                            @Field("user_id") String user_id,
                                            @Field("remark") String remark,
                                            @Field("payment_status") String payment_status);

    @GET("/api/Symtoms/FetchDoctorBySymtom")
    Call<GalleryListResponse> AllSymtimsDoctors(@Header("X-API-KEY") String authorization,
                                                @Query("symtom_id") String id);

    @GET("/api/Appointment/GetAllPatientAppointment")
    Call<GalleryListResponse> GetAllPatientAppointment(@Header("X-API-KEY") String authorization,
                                                       @Query("userId") String id);

    @FormUrlEncoded
    @POST("/api/Appointment/BookAppointment")
    Call<secureLoginResponse> BookAppointment(@Header("X-API-KEY") String authorization,
                                              @Field("doctor_id") String doctor_id,
                                              @Field("slot_id") String slot_id,
                                              @Field("userId") String userId,
                                              @Field("patient_details") String patient_details,
                                              @Field("no_of_patient") String no_of_patient,
                                              @Field("book_date") String date);

    @FormUrlEncoded
    @POST("/api/Availability/SetAvailability")
    Call<secureLoginResponse> SetAvailability(@Header("X-API-KEY") String authorization,
                                              @Field("doctor_id") String id,
                                              @Field("start_time") String start_time,
                                              @Field("end_time") String end_time,
                                              @Field("date") String date);

    @GET("/api/FetchDrByName")
    Call<GalleryListResponse> FetchDrByName(@Header("X-API-KEY") String authorization,
                                            @Query("name") String name,
                                            @Query("start") Integer start,
                                            @Query("limit") Integer limit);

    @GET("/api/Banner")
    Call<GalleryListResponse> getbanner(@Header("X-API-KEY") String authorization);


    @GET("/api/Symtoms/AllSymtoms")
    Call<GalleryListResponse> AllSymtoms(@Header("X-API-KEY") String authorization);

    @FormUrlEncoded
    @POST("/api/Symtoms/FetchSymtom")
    Call<GalleryListResponse> setconsult(@Header("X-API-KEY") String authorization,
                                         @Field("symtom") String symtom,
                                         @Field("userId") String customer_id);


}
