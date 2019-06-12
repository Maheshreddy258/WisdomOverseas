package com.example.mahesh.wisdomoverseas.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountResponse implements Serializable
{

    @SerializedName("ID")
    @Expose
    public Integer iD;
    @SerializedName("UserName")
    @Expose
    public String userName;
    @SerializedName("CallsDone")
    @Expose
    public Integer callsDone;
    @SerializedName("Reminders")
    @Expose
    public Integer reminders;
    @SerializedName("Subject")
    @Expose
    public String subject;
    @SerializedName("Messages")
    @Expose
    public String messages;
    @SerializedName("FileUpload")
    @Expose
    public String fileUpload;
    @SerializedName("Status")
    @Expose
    public Boolean status;
    @SerializedName("CreatedDate")
    @Expose
    public String createdDate;
    @SerializedName("Month")
    @Expose
    public String month;
    @SerializedName("Year")
    @Expose
    public Integer year;
    @SerializedName("RecordStatus")
    @Expose
    public Boolean recordStatus;
    @SerializedName("ErrorMessage")
    @Expose
    public String errorMessage;
    private final static long serialVersionUID = -2378218697955597660L;

}