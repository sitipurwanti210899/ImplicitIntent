package com.gosigitgo.implicit_intent_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KameraActivity extends AppCompatActivity {

    @BindView(R.id.img_result)
    ImageView imgResult;
    @BindView(R.id.btn_galery)
    Button btnGalery;
    @BindView(R.id.btn_kamera)
    Button btnKamera;

    //jadikan global uri
    Uri lokasiFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamera);
        ButterKnife.bind(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},10);
            }return;
        }if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        107);
            }
            return;
        }
    }

    @OnClick({R.id.btn_galery, R.id.btn_kamera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_galery:
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, 1);
                break;
            case R.id.btn_kamera:


                StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                String namaFolder = "camera_implicit";
                File file = new File(Environment.getDataDirectory(), namaFolder);
                if (!file.exists()){
                    //buat folder
                    file.mkdir();
                }

                //tulis data
                File isiFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+namaFolder+"/IMAGE"+
                        currentDate()+".jpg");
                 lokasiFile = Uri.fromFile(isiFile);
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camera.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, lokasiFile);
                startActivityForResult(camera, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //success
                Uri uri = data.getData();
                imgResult.setImageURI(uri);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "batal ambil gambar", Toast.LENGTH_SHORT).show();

            }
        }
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                imgResult.setImageURI(lokasiFile);

            }
        }
    }
    //ambil data/tanggal sekarang
    private String currentDate(){
        DateFormat df =new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Date date= new Date();
        return  df.format(date);
    }
}
