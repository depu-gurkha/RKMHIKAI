package online.rkmhikai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Assignment extends AppCompatActivity implements View.OnClickListener{

    ProgressBar progressBar;
    Button btnUpload, btnDownlaod;
    private int STORAGE_PERMISSION_CODE = 1;
    String assignmentUrl,loaclstorage;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        progressBar=findViewById(R.id.pb_assignment);
        btnUpload=findViewById(R.id.btnUpload);
        btnDownlaod =findViewById(R.id.btn_dwnld);
        btnDownlaod.setOnClickListener(this);
        btnUpload.setOnClickListener(this );
        assignmentUrl=getIntent().getStringExtra("link");
        loaclstorage=getIntent().getStringExtra("loacation");
        fileName =getIntent().getStringExtra("fileName");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_dwnld:
                Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
                startDownloading(assignmentUrl);
                break;
            case R.id.btnUpload:
                Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    CropImage.startPickImageActivity(this);
                    // Log.d("Crop Image", ).toString());

                } else {

                    requestStoragePermission();

                }
        }
    }
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Assignment.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Assignment.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(Assignment.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Assignment.this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Assignment.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void startDownloading(String url){
        //get url/text from edit text

        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        Log.i("TAG", "startDownloading: "+url);
        Log.i("TAG", "Filename: "+ URLUtil.guessFileName(url,null,null));

        //create download request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //Allow types of network to download
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Downloading File...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis()); //get current timestamp as file name
//        request.setDestinationInExternalFilesDir(getApplicationContext(),"/Videos/abc","Hello-"+System.currentTimeMillis()+".jpg");
//        request.setDestinationInExternalFilesDir(getApplicationContext(), String.valueOf(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url,null,null));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);



        //get download service and enque file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue((request));
    }
}