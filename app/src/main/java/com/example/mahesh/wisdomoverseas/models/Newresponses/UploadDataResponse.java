package com.example.mahesh.wisdomoverseas.models.Newresponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import okhttp3.RequestBody;

public class UploadDataResponse implements Serializable
{

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("FIleName")
    @Expose
    public RequestBody fIleName;
    @SerializedName("Filepath")
    @Expose
    public String filepath;
    @SerializedName("UploadedBy")
    @Expose
    public String uploadedBy;
    @SerializedName("Createddate")
    @Expose
    public String createddate;
    @SerializedName("Status")
    @Expose
    public Boolean status;
    private final static long serialVersionUID = -7898715794702750706L;

}
