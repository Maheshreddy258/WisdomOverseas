package com.example.mahesh.wisdomoverseas.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LeadsResponse implements Serializable
{

    @SerializedName("ID")
    @Expose
    public Integer iD;
    @SerializedName("Student")
    @Expose
    public String student;
    @SerializedName("ContactNumber")
    @Expose
    public String contactNumber;
    @SerializedName("RM")
    @Expose
    public Object rM;
    @SerializedName("LeadBy")
    @Expose
    public String leadBy;
    @SerializedName("ParentsName")
    @Expose
    public String parentsName;
    @SerializedName("AlterntiveNumber")
    @Expose
    public Object alterntiveNumber;
    @SerializedName("IsIntrest")
    @Expose
    public Object isIntrest;
    @SerializedName("CallBackDate")
    @Expose
    public Object callBackDate;
    @SerializedName("Createddate")
    @Expose
    public Object createddate;
    @SerializedName("CallStatus")
    @Expose
    public Object callStatus;
    @SerializedName("Remarks")
    @Expose
    public Object remarks;
    @SerializedName("FeedBack")
    @Expose
    public Object feedBack;
    @SerializedName("Joindate")
    @Expose
    public Object joindate;
    @SerializedName("ConvertedBy")
    @Expose
    public Object convertedBy;
    @SerializedName("CalledBy")
    @Expose
    public Object calledBy;
    @SerializedName("ReminderBy")
    @Expose
    public Object reminderBy;
    @SerializedName("FInalFeedBack")
    @Expose
    public Object fInalFeedBack;
    @SerializedName("State")
    @Expose
    public Object state;
    @SerializedName("City")
    @Expose
    public Object city;
    @SerializedName("Status")
    @Expose
    public Integer status;
    @SerializedName("AssignStatus")
    @Expose
    public Object assignStatus;
    @SerializedName("AssignedTo")
    @Expose
    public Object assignedTo;
    @SerializedName("RecordStatus")
    @Expose
    public Boolean recordStatus;
    @SerializedName("ErrorMessage")
    @Expose
    public Object errorMessage;
    @SerializedName("IntrestedCountry")
    @Expose
    public Object intrestedCountry;
    @SerializedName("SiblingsInabroad")
    @Expose
    public Object siblingsInabroad;
    @SerializedName("SiblingCountry")
    @Expose
    public Object siblingCountry;
    @SerializedName("KnowledgeNeeded")
    @Expose
    public Object knowledgeNeeded;
    @SerializedName("VisitedIntrest")
    @Expose
    public Object visitedIntrest;
    @SerializedName("visitingDate")
    @Expose
    public Object visitingDate;
    @SerializedName("LeadStatus")
    @Expose
    public Object leadStatus;
    @SerializedName("ReminderDate")
    @Expose
    public String reminderDate;
    @SerializedName("ReminderText")
    @Expose
    public Object reminderText;
    @SerializedName("RemninderStatus")

    @Expose
    public Boolean remninderStatus;
    private final static long serialVersionUID = 3054746359457660372L;

}
