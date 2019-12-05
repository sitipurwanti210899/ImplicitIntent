package com.gosigitgo.implicit_intent_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SMSActivity extends AppCompatActivity {

    @BindView(R.id.edt_nohp)
    EditText edtNohp;
    @BindView(R.id.btn_pilihkontak)
    Button btnPilihkontak;
    @BindView(R.id.edt_pesan)
    EditText edtPesan;
    @BindView(R.id.btn_kirim_langsung)
    Button btnKirimLangsung;
    @BindView(R.id.btn_intent_sms)
    Button btnIntentSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);

        // Periziann permission
        //https://developer.android.com/training/permissions/requesting
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        10);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Toast.makeText(this, "permission telah aktif", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick({R.id.btn_pilihkontak, R.id.btn_kirim_langsung, R.id.btn_intent_sms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pilihkontak:
                //melempar data ke kontak
                Intent kontak = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                kontak.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(kontak, 2);
                break;
            case R.id.btn_kirim_langsung:
                String noTelp = edtNohp.getText().toString();
                String bodySms = edtPesan.getText().toString();

                if (TextUtils.isEmpty(noTelp)|| TextUtils.isEmpty(bodySms)){
                    Toast.makeText(this, "tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(noTelp, null, bodySms, null, null);
                        Toast.makeText(this,"Berhasil", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_intent_sms:
                String noTel = edtNohp.getText().toString();
                String sms = edtPesan.getText().toString();

                if (TextUtils.isEmpty(noTel) || TextUtils.isEmpty(sms)){
                    Toast.makeText(this, "Tak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intentSms=new Intent(Intent.ACTION_SENDTO);
                    intentSms.setData(Uri.parse("smsto:"+Uri.encode(noTel)));
                    intentSms.putExtra("sms_body", sms);
                    startActivity(intentSms);
                }
                break;
        }
    }
    //panggil method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //cek
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                Cursor cursor;
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        null,
                        null,
                        null,
                        null);
                //ambil datanya
                if (cursor != null && cursor.moveToNext()){
                    String nomorTelepon = cursor.getString(0);
                    edtNohp.setText(nomorTelepon);
                }
            }
        }else {
            Toast.makeText(this, "batal", Toast.LENGTH_SHORT).show();
        }
    }
}
