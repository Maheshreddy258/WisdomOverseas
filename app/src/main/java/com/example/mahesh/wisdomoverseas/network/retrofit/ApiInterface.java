package com.example.mahesh.wisdomoverseas.network.retrofit;

import com.example.mahesh.wisdomoverseas.models.Newresponses.CheckResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewCallBacksResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewInterestedCallResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewPostDataResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewRemindersInfoResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodaycallBacksResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewUpdateInfoResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewUpdateLeadResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.UploadDataResponse;
import com.example.mahesh.wisdomoverseas.models.requests.ReportRequest;
import com.example.mahesh.wisdomoverseas.models.requests.UserLoginRequest;
import com.example.mahesh.wisdomoverseas.models.responses.CountResponse;
import com.example.mahesh.wisdomoverseas.models.responses.FamilyInfoResponse;
import com.example.mahesh.wisdomoverseas.models.responses.HistoryCalls;
import com.example.mahesh.wisdomoverseas.models.responses.MyProfileResponse;
import com.example.mahesh.wisdomoverseas.models.responses.MyReportResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewAllCallsResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewPendingRemindersResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodayCallResponse;
import com.example.mahesh.wisdomoverseas.models.responses.RemindersResponse;
import com.example.mahesh.wisdomoverseas.models.responses.ReportResponse;
import com.example.mahesh.wisdomoverseas.models.responses.UpdateInfoResponse;
import com.example.mahesh.wisdomoverseas.models.responses.UserLoginResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ApiInterface {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("api/Login")
    Call<UserLoginResponse> getServiceResponse(@Body UserLoginRequest request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("api/SendReport")
    Call<ReportResponse> getReportResponse(@Body ReportRequest reportRequest);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/MyProfile?")
    Call<MyProfileResponse> getProfileDetails(@Query("UserName") String UserName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
   /* @GET("api/GetLeadsList?")
   Call<List<TodayCallResponse>> getLeadsResponse(@Query("UserName") String UserName, @Query("Status") Boolean Status);
*/
    @GET("api/MyLeadsListinfo?")
    Call<List<NewTodayCallResponse>> getTodayLeadsResponse(@Query("UserName") String UserName);



    @POST("api/UpdateLead")

    Call<UpdateInfoResponse> getUpdateInfoResponse(@Body UpdateInfoResponse updateInfoResponse);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/MyReports?")
    Call<List<MyReportResponse>> getMyReportResponse(@Query("UserName") String UserName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/RemindersList?")
    Call<List<RemindersResponse>> getRemainderResponse(@Query("UserName") String UserName);





    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
   /* @GET("api/Myleadslist?")
    Call<List<AllCallsResponse>> getAllCallsResponse(@Query("UserName") String UserName);
*/
    @GET("api/MyTotalLeadsinfo?")
    Call<List<NewAllCallsResponse>> getAllCallsResponse(@Query("UserName") String UserName);





    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/callHistory?")
    Call<List<HistoryCalls>> getHistoryCalls(@Query("UserName") String UserName);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/myPendigReminders?")
    Call<List<NewPendingRemindersResponse>> getPendingReminderCalls(@Query("UserName") String UserName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/MyIntrestedLeads?")
    Call<List<NewInterestedCallResponse>> getInterestedCalls(@Query("UserName") String UserName);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/MyPendingCallBacks?")
    Call<List<NewCallBacksResponse>> getCallBackCalls(@Query("UserName") String UserName);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/TodayCallBacks?")
    Call<List<NewTodaycallBacksResponse>> getTodayCallBackResponse(@Query("UserName") String UserName);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/MyRemindersInfo?")
    Call<List<NewRemindersInfoResponse>> getRemindersInfoResponse(@Query("UserName") String UserName);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/GetTodayData?")
    Call<CountResponse> getCountResponse(@Query("UserName") String UserName);




    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/verifyLeadInfo?")
    Call<CheckResponse> getCheckResponse(@Query("ContactNumber") String ContactNumber);




    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/UpdateLeadService?")
    Call<NewUpdateLeadResponse> getUpdateLead(@Query("Id") int Id,@Query("Status") int Status);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("api/LeadFamilyUpdate")
    Call<FamilyInfoResponse> getFamilyInfoResponse(@Body FamilyInfoResponse familyInfoResponse);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("api/PostLeadinfo")
    Call<NewUpdateInfoResponse> getNewUpdateInfoResponse(@Body NewUpdateInfoResponse newUpdateInfoResponse);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("api/PostNewLeadinfo")
    Call<NewPostDataResponse> getPostDataResponse(@Body NewPostDataResponse newPostDataResponse);





    @Multipart
    @POST("api/uploaddata")
   // Call<UploadDataResponse> getUploadedData(@Part MultipartBody.Part file);
    Call<UploadDataResponse> getUploadedData(@Query("id") int id,@Query("fIleName") String fIleName,
                                 @Query("filepath") String filepath, @Part MultipartBody.Part file);



}
