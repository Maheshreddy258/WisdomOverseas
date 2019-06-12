package com.example.mahesh.wisdomoverseas.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistoryCalls implements Serializable
{

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("LeadName")
    @Expose
    public String leadName;
    @SerializedName("ContactNumber")
    @Expose
    public String contactNumber;
    @SerializedName("Assignedto")
    @Expose
    public String assignedto;
    @SerializedName("CalledBy")
    @Expose
    public String calledBy;
    @SerializedName("Status")
    @Expose
    public Boolean status;
    @SerializedName("CallStatus")
    @Expose
    public String callStatus;
    @SerializedName("RecordFile")
    @Expose
    public String recordFile;
    @SerializedName("ReminderDate")
    @Expose
    public String reminderDate;
    @SerializedName("CallDate")
    @Expose
    public String callDate;
    @SerializedName("FeedBack")
    @Expose
    public String feedBack;
    @SerializedName("AgentName")
    @Expose
    public String agentName;
    @SerializedName("Callfrom")
    @Expose
    public String callfrom;
    @SerializedName("LeadId")
    @Expose
    public Integer leadId;
    private final static long serialVersionUID = 861975044417654649L;

}