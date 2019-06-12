package com.example.mahesh.wisdomoverseas.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RemindersResponse implements Serializable
{

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("Reminderdate")
    @Expose
    public String reminderdate;
    @SerializedName("Leadid")
    @Expose
    public Integer leadid;
    @SerializedName("EmployeeName")
    @Expose
    public String employeeName;
    @SerializedName("ReminderText")
    @Expose
    public String reminderText;
    @SerializedName("Status")
    @Expose
    public Boolean status;
    @SerializedName("CreateDate")
    @Expose
    public String createDate;
    @SerializedName("StudentName")
    @Expose
    public String studentName;
    @SerializedName("StudnetNumber")
    @Expose
    public String studnetNumber;
    private final static long serialVersionUID = 625126076909235748L;

}
