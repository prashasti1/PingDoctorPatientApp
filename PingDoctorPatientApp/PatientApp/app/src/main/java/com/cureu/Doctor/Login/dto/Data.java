package com.cureu.Doctor.Login.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("medicine_id")
    @Expose
    private String medicine_id;

  @SerializedName("qualification")
    @Expose
    private String qualification;

   @SerializedName("clinic_name")
    @Expose
    private String clinic_name;

    @SerializedName("preciption_img")
    @Expose
    private String preciption_img;

  @SerializedName("preciption_description")
    @Expose
    private String preciption_description;


    public String getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(String medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getPreciption_img() {
        return preciption_img;
    }

    public void setPreciption_img(String preciption_img) {
        this.preciption_img = preciption_img;
    }

    public String getPreciption_description() {
        return preciption_description;
    }

    public void setPreciption_description(String preciption_description) {
        this.preciption_description = preciption_description;
    }

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("message")
    @Expose
    private String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("mobile")
    @Expose
    private String mobile;


   @SerializedName("id")
    @Expose
    private String id;


   @SerializedName("role")
    @Expose
    private String role;

 @SerializedName("patient_id")
    @Expose
    private String patient_id;


    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    @SerializedName("name")
    @Expose
    private String name;


   @SerializedName("email")
    @Expose
    private String email;


 @SerializedName("X-API-KEY")
    @Expose
    private String XAPIKEY;




    ////////////////////////respose///////////////


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getXAPIKEY() {
        return XAPIKEY;
    }

    public void setXAPIKEY(String XAPIKEY) {
        this.XAPIKEY = XAPIKEY;
    }


    //////////////////////////////////////////////

    @SerializedName("doctor_id")
    @Expose
    private String doctorId;
    @SerializedName("specialities")
    @Expose
    private String specialities;

    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("pincode")
    @Expose
    private Object pincode;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getPincode() {
        return pincode;
    }

    public void setPincode(Object pincode) {
        this.pincode = pincode;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
