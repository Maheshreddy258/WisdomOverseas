package com.example.mahesh.wisdomoverseas.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FamilyInfoResponse implements Serializable
{

    @SerializedName("ID")
    @Expose
    public Integer iD;
    @SerializedName("StudentLeadId")
    @Expose
    public Integer studentLeadId;
    @SerializedName("StudentName")
    @Expose
    public String studentName;
    @SerializedName("FatherName")
    @Expose
    public String fatherName;
    @SerializedName("ContactNumber")
    @Expose
    public String contactNumber;
    @SerializedName("Altenative")
    @Expose
    public String altenative;
    @SerializedName("Occupation")
    @Expose
    public String occupation;
    @SerializedName("Sector")
    @Expose
    public String sector;
    @SerializedName("IncomeStatus")
    @Expose
    public String incomeStatus;
    @SerializedName("IncomeinLacks")
    @Expose
    public String incomeinLacks;
    @SerializedName("Cast")
    @Expose
    public String cast;
    @SerializedName("Children")
    @Expose
    public Integer children;
    @SerializedName("EntranceCourse")
    @Expose
    public String entranceCourse;
    @SerializedName("QulaifiedTest")
    @Expose
    public Boolean qulaifiedTest;
    @SerializedName("QualifiedEanmName")
    @Expose
    public String qualifiedEanmName;
    @SerializedName("Rank")
    @Expose
    public Integer rank;
    @SerializedName("RankAttempts")
    @Expose
    public Integer rankAttempts;
    @SerializedName("PrefLocatin")
    @Expose
    public String prefLocatin;
    @SerializedName("CountryName")
    @Expose
    public String countryName;
    @SerializedName("Ssc")
    @Expose
    public Double ssc;
    @SerializedName("Inter")
    @Expose
    public Double inter;
    @SerializedName("InterPassout")
    @Expose
    public Integer interPassout;
    @SerializedName("City")
    @Expose
    public String city;
    @SerializedName("State")
    @Expose
    public String state;
    @SerializedName("Address")
    @Expose
    public String address;
    @SerializedName("Area")
    @Expose
    public String area;
    @SerializedName("LeadContact")
    @Expose
    public String leadContact;
    @SerializedName("RecordStatus")
    @Expose
    public Boolean recordStatus;
    @SerializedName("ErrorMessage")
    @Expose
    public String errorMessage;
    private final static long serialVersionUID = 4123663353367707046L;

}