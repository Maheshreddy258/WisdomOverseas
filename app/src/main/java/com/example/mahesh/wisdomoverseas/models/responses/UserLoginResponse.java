package com.example.mahesh.wisdomoverseas.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLoginResponse implements Serializable
{

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("UserName")
    @Expose
    public String userName;
    @SerializedName("Pwd")
    @Expose
    public String pwd;
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("Role")
    @Expose
    public String role;
    @SerializedName("Status")
    @Expose
    public Boolean status;
    @SerializedName("LoginStatus")
    @Expose
    public Boolean loginStatus;
    @SerializedName("ErrorMessage")
    @Expose
    public String errorMessage;
    @SerializedName("Name")
    @Expose
    public String name;
    private final static long serialVersionUID = -4314479336593293440L;

}