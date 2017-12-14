package neolab.vn.facerecognition;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.File;

import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks, View.OnClickListener {

    public static final int PERMISSIONS_REQUEST_CODE = 69;

    public String[] mPermissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA };

    private Service mService;

    private ImageView mUploadImageView;

    private ImageView mPreviewImageView;

    private ProgressRequestBody mProgressRequestBody;

    private NumberProgressBar mNumberProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        /*
         * find views
         */
        mUploadImageView = (ImageView) findViewById(R.id.upload_image_view);
        mUploadImageView.setOnClickListener(this);

        mPreviewImageView = (ImageView) findViewById(R.id.preview_image_view);

        mNumberProgressBar = (NumberProgressBar) findViewById(R.id.number_progress_bar);

        /*
         * Initialize Service
         */
        mService = ServiceGenerator.createService(Service.class);
    }

    @Override
    public void onProgressUpdate(int percentage) {
        // TO DO
        mNumberProgressBar.setProgress(percentage);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }

    public boolean isSpecificPermissionsGranted(String[] permissions) {
        int size = permissions.length;
        for (int index = 0; index < size; index ++) {
            int result = ContextCompat.checkSelfPermission(this, permissions[index]);
            if (result != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public void requestSpecificPermissions(String[] permissions, int message) {
        int size = permissions.length;
        boolean isshouldShowRequestPermissionRationale = false;
        for (int index = 0; index < size; index ++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[index])) {
                isshouldShowRequestPermissionRationale = true;
                break;
            }
        }
        if (isshouldShowRequestPermissionRationale) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    public boolean checkRequestPermissionsResult(int[] grantResults) {
        boolean permissionGranted = true;
        for (int grantResult : grantResults) {
            permissionGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
        }
        return permissionGranted;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_image_view:
                if (isSpecificPermissionsGranted(mPermissions)) {
                    showLocalImages();
                } else {
                    requestSpecificPermissions(mPermissions, R.string.update_info_permission_needed_camera_and_write_external);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (checkRequestPermissionsResult(grantResults)) {
                    showLocalImages();
                } else {
                }
                break;
            default:
                break;
        }
    }

    public void showLocalImages() {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(UploadActivity.this)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected uri
                        if (uri != null)
                            handleUri(uri);
                    }
                })
                .setImageProvider(null)
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }

    private void handleUri(Uri uri) {
        File file = new File(uri.getPath());
        mProgressRequestBody = new ProgressRequestBody(file, this);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), mProgressRequestBody);
        Call<UploadEntity> request = mService.uploadImage(filePart);
        request.enqueue(new Callback<UploadEntity>() {
            @Override
            public void onResponse(Call<UploadEntity> call, Response<UploadEntity> response) {

            }

            @Override
            public void onFailure(Call<UploadEntity> call, Throwable t) {

            }
        });

        Glide.with(getApplicationContext())
                .load(new File(uri.getPath())).into(mPreviewImageView);

    }


}
