package com.example.mahesh.wisdomoverseas.models.responses;

import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class UpdateInfoResponse implements Serializable
{

    @SerializedName("ID")
    @Expose
    public int iD;
    @SerializedName("Student")
    @Expose
    public String student;
    @SerializedName("ContactNumber")
    @Expose
    public String contactNumber;
    @SerializedName("RM")
    @Expose
    public String rM;
    @SerializedName("LeadBy")
    @Expose
    public String leadBy;
    @SerializedName("ParentsName")
    @Expose
    public String parentsName;
    @SerializedName("AlterntiveNumber")
    @Expose
    public String alterntiveNumber;
    @SerializedName("IsIntrest")
    @Expose
    public Boolean isIntrest;
    @SerializedName("CallBackDate")
    @Expose
    public String callBackDate;
    @SerializedName("Createddate")
    @Expose
    public Date createddate;
    @SerializedName("CallStatus")
    @Expose
    public String callStatus;
    @SerializedName("Remarks")
    @Expose
    public String remarks;
    @SerializedName("FeedBack")
    @Expose
    public String feedBack;
    @SerializedName("Joindate")
    @Expose
    public String joindate;
    @SerializedName("ConvertedBy")
    @Expose
    public String convertedBy;
    @SerializedName("CalledBy")
    @Expose
    public String calledBy;
    @SerializedName("ReminderBy")
    @Expose
    public String reminderBy;
    @SerializedName("FInalFeedBack")
    @Expose
    public String fInalFeedBack;
    @SerializedName("State")
    @Expose
    public String state;
    @SerializedName("City")
    @Expose
    public String city;
    @SerializedName("Status")
    @Expose
    public Integer status;
    @SerializedName("AssignStatus")
    @Expose
    public Boolean assignStatus;
    @SerializedName("AssignedTo")
    @Expose
    public String assignedTo;
    @SerializedName("RecordStatus")
    @Expose
    public Boolean recordStatus;
    @SerializedName("ErrorMessage")
    @Expose
    public String errorMessage;
    @SerializedName("IntrestedCountry")
    @Expose
    public String intrestedCountry;
    @SerializedName("SiblingsInabroad")
    @Expose
    public String siblingsInabroad;
    @SerializedName("SiblingCountry")
    @Expose
    public String siblingCountry;
    @SerializedName("KnowledgeNeeded")
    @Expose
    public Boolean knowledgeNeeded;
    @SerializedName("VisitedIntrest")
    @Expose
    public Boolean visitedIntrest;
    @SerializedName("visitingDate")
    @Expose
    public String visitingDate;
    @SerializedName("LeadStatus")
    @Expose
    public String leadStatus;
    @SerializedName("ReminderDate")
    @Expose
    public String reminderDate;
    @SerializedName("ReminderText")
    @Expose
    public String reminderText;
    @SerializedName("RemninderStatus")
    @Expose
    public Boolean remninderStatus;
    @SerializedName("infoStatus")
    @Expose
    public Boolean infoStatus;
    @SerializedName("Callfrom")
    @Expose
    public String callfrom;
    @SerializedName("ReminderId")
    @Expose
    public Integer reminderId;
    private final static long serialVersionUID = 4108348702267803400L;

}