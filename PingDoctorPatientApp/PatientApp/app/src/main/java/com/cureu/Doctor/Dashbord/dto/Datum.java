package com.cureu.Doctor.Dashbord.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("book_date")
    @Expose
    private String book_date;

    @SerializedName("doctor_name")
    @Expose
    private String doctor_name;

    public String getBook_date() {
        return book_date;
    }

    public void setBook_date(String book_date) {
        this.book_date = book_date;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    @SerializedName("symtom")
    @Expose
    private String symtom;

  @SerializedName("doctor_id")
    @Expose
    private String doctor_id;

 @SerializedName("start_time")
    @Expose
    private String start_time;

 @SerializedName("end_time")
    @Expose
    private String end_time;

 @SerializedName("date")
    @Expose
    private String date;


    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSymtom() {
        return symtom;
    }

    public void setSymtom(String symtom) {
        this.symtom = symtom;
    }

    @SerializedName("description")
    @Expose
    private String description;

  @SerializedName("symtom_id")
    @Expose
    private String symtom_id;


    public String getSymtom_id() {
        return symtom_id;
    }

    public void setSymtom_id(String symtom_id) {
        this.symtom_id = symtom_id;
    }

    @SerializedName("created_date")
    @Expose
    private String created_date;

   @SerializedName("id")
    @Expose
    private String id;

   @SerializedName("appointment_id")
    @Expose
    private String appointment_id;


    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    @SerializedName("banner_img")
    @Expose
    private String banner_img;

  @SerializedName("icon")
    @Expose
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("medicine_name")
    @Expose
    private String medicine_name;


    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    @SerializedName("no_of_patient")
    @Expose
    private String no_of_patient;


    public String getNo_of_patient() {
        return no_of_patient;
    }

    public void setNo_of_patient(String no_of_patient) {
        this.no_of_patient = no_of_patient;
    }

    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("type_name")
    @Expose
    private String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
