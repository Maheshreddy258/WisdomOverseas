package com.example.mahesh.wisdomoverseas.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyProfileResponse implements Serializable
{

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("Address")
    @Expose
    public String address;
    @SerializedName("Designation")
    @Expose
    public String designation;
    @SerializedName("Role")
    @Expose
    public String role;
    @SerializedName("Status")
    @Expose
    public Boolean status;
    @SerializedName("JOD")
    @Expose
    public String jOD;
    @SerializedName("DOB")
    @Expose
    public Object dOB;
    @SerializedName("EmpType")
    @Expose
    public String empType;
    @SerializedName("Country")
    @Expose
    public String country;
    @SerializedName("State")
    @Expose
    public String state;
    @SerializedName("City")
    @Expose
    public String city;
    private final static long serialVersionUID = 3320780447390812471L;

}
