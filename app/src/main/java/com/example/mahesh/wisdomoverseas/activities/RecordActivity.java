package com.example.mahesh.wisdomoverseas.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodayCallResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.UploadDataResponse;
import com.example.mahesh.wisdomoverseas.models.responses.MyProfileResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 100;
    private static final int REQUEST_CODE = 100;
    TextView mTvrecord,mTvplay,mTvupload,mTvfilename;
    Toolbar toolbar;
    Button mBtnrecord,mBtnStoprecord,mBtnplay,mBtnstopplay,mBtnupload,mBtnselect;
    String PATH_SAVE = "";
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    FloatingActionButton fab_profile_Snackbar;
    ProgressDialog progress;
    SharedPref sharedPref;
    String userName;

    Context mContext;

    final int REQUEST_PERMISSION_CODE = 1000;

    NewTodayCallResponse newTodayCallResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        fab_profile_Snackbar = findViewById(R.id.fab_profile_Snackbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if (!CheckPermissionFromDevice())
            requestPermission();


        //checking the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }


        mTvrecord = findViewById(R.id.tvreordarea);
        mTvplay = findViewById(R.id.tvplayarea);
        mTvupload = findViewById(R.id.tvupload);
        mTvfilename = findViewById(R.id.tvfilename);

        mBtnrecord = findViewById(R.id.btnRecord);
        mBtnStoprecord = findViewById(R.id.btnStopRecord);
        mBtnplay = findViewById(R.id.btnplayRecord);
        mBtnstopplay =findViewById(R.id.btnStopplay);
        mBtnupload = findViewById(R.id.btnupload);
        mBtnselect = findViewById(R.id.btnselect);




            mBtnrecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (CheckPermissionFromDevice())

                    {

                        PATH_SAVE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                            UUID.randomUUID().toString()+"_audiorecord.3gp";

                    setUpMediaRecorder();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mBtnplay.setEnabled(false);
                    mBtnstopplay.setEnabled(false);


                    Toast.makeText(RecordActivity.this, "Recording.....", Toast.LENGTH_SHORT).show();

                    }

                    else{

                        requestPermission();

                    }
                    }
            });


            mBtnStoprecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   mediaRecorder.stop();
                   mBtnStoprecord.setEnabled(false);
                   mBtnplay.setEnabled(true);
                   mBtnrecord.setEnabled(true);
                   mBtnstopplay.setEnabled(false);
                }
            });


            mBtnplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mBtnstopplay.setEnabled(true);
                    mBtnStoprecord.setEnabled(false);
                    mBtnrecord.setEnabled(false);


                    mediaPlayer = new MediaPlayer();

                    try{
                        mediaPlayer.setDataSource(PATH_SAVE);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.start();

                    Toast.makeText(RecordActivity.this, "Playingg....", Toast.LENGTH_SHORT).show();



                }
            });


            mBtnstopplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    mBtnrecord.setEnabled(true);
                    mBtnStoprecord.setEnabled(false);
                    mBtnplay.setEnabled(true);
                    mBtnstopplay.setEnabled(false);

                    if (mediaPlayer !=null){

                        mediaPlayer.stop();
                        mediaPlayer.release();
                        setUpMediaRecorder();
                    }

                }
            });
        mBtnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(RecordActivity.this, uploadActivity.class);
                startActivity(intent);*/

                //chooseFile();


                Uri uri = Uri.parse("http://wo.brandwar.in/Employee/UploadFilesData"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int REQUEST_CODE, int resultCode, final Intent data) {

        //super.onActivityResult(REQUEST_CODE, resultCode, data);

      /*  if ((REQUEST_CODE == FILE_SELECT_CODE) && (resultCode == RESULT_OK)) {

            File file = new File(getRealPathFromURI(data.getData()));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), getRealPathFromURI(data.getData()));
            MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UploadDataResponse> responseBodyCall =  apiInterface.getUploadedData(Integer.parseInt("id"),"fIleName" , "filepath", multipartBody);
            responseBodyCall.enqueue(new Callback<UploadDataResponse>() {
                @Override
                public void onResponse(Call<UploadDataResponse> call, Response<UploadDataResponse> response) {
                    Toast.makeText(RecordActivity.this, "upload file", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<UploadDataResponse> call, Throwable t) {

                    Toast.makeText(RecordActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }*/


                if (REQUEST_CODE == 10 && resultCode == RESULT_OK) {

                    progress = new ProgressDialog(RecordActivity.this);
                    progress.setTitle("Uploading");
                    progress.setMessage("Please wait...");
                    progress.show();

                    final Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                            String content_type = getMimeType(f.getPath());
                            String file_path = f.getAbsolutePath();
//                            mTvfilename.setText(file_path);

/*
                            OkHttpClient client = new OkHttpClient();

                            RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);*/
/*
                            RequestBody request_body = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("type", content_type)
                                    .addFormDataPart("uploaded_file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                                    .build();*/

                            Log.e("uploadinggg....","uploadinggggg");

                         /*   //RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), f);
                            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", f.getName(), mFile);
                            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), f.getName());*/


                             sharedPref = new SharedPref(getApplicationContext());
                            userName = sharedPref.getUserName();
                            File file = new File(getRealPathFromURI(data.getData()));
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), getRealPathFromURI(data.getData()));
                            MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);

                            UploadDataResponse uploadDataResponse = new UploadDataResponse();
                            uploadDataResponse.fIleName = requestFile;
                            uploadDataResponse.filepath=file_path;
                            uploadDataResponse.uploadedBy=userName;
                            uploadDataResponse.status=true;

                            if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {

                                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                                Call<UploadDataResponse> Call =  apiInterface.getUploadedData(Integer.parseInt("id"),"fIleName" , "filepath", multipartBody);
                                Call.enqueue(new Callback<UploadDataResponse>() {
                                    @Override
                                    public void onResponse(Call<UploadDataResponse> call, retrofit2.Response<UploadDataResponse> response) {
                                        progress.dismiss();
                                        Toast.makeText(RecordActivity.this, "Upload file successfully", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onFailure(retrofit2.Call<UploadDataResponse> call, Throwable t) {
                                        progress.dismiss();
                                        Toast.makeText(RecordActivity.this, "Something went Wrong..", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            } else {
                                Snackbar.make(fab_profile_Snackbar, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();

                            }
                        }
                    });

                    t.start();


               /*     // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String  displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();


                    }

                    mTvfilename.setText(displayName);
                    Log.e("filename",""+mTvfilename);
                }
                break;
        }

        super.onActivityResult(REQUEST_CODE, resultCode, data);*/

                   // progress.dismiss();
                }

    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private void chooseFile() {


       // Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.setType("*/*");
        //startActivityForResult(intent, REQUEST_CODE);

        new MaterialFilePicker()
                .withActivity(RecordActivity.this)
                .withRequestCode(10)
                .start();
    }
    private void setUpMediaRecorder() {

        mediaRecorder = new MediaRecorder();
       mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                mediaRecorder.setOutputFile(PATH_SAVE);
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  REQUEST_PERMISSION_CODE:
            {
                if (grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED )
                {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText( this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private boolean CheckPermissionFromDevice() {

        int Write_External_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        return Write_External_storage == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }

    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

}
