package com.gosigitgo.implicit_intent_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeleponActivity extends AppCompatActivity {

    @BindView(R.id.edt_hp)
    EditText edtHp;
    @BindView(R.id.btn_pilihkontak)
    Button btnPilihkontak;
    @BindView(R.id.btn_panggil_langsung)
    Button btnPanggilLangsung;
    @BindView(R.id.btn_dial)
    Button btnDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telepon);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_pilihkontak, R.id.btn_panggil_langsung, R.id.btn_dial})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pilihkontak:
                //melempar data ke kontak
                Intent kontak = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                kontak.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(kontak, 2);
                break;
            case R.id.btn_panggil_langsung:
                String noTel = edtHp.getText().toString();

                if (TextUtils.isEmpty(noTel)){
                    Toast.makeText(this, "tidak boleh ksoong", Toast.LENGTH_SHORT).show();
                }else {
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:"+ noTel));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 10);
                            return;
                        }
                    }
                    startActivity(call);
                }
                break;
            case R.id.btn_dial:
                String no = edtHp.getText().toString();

                if (TextUtils.isEmpty(no)){
                    Toast.makeText(this, "tidak  boelh kosong", Toast.LENGTH_SHORT).show();
                }else {
                    Intent dial = new Intent(Intent.ACTION_DIAL);
                    dial.setData(Uri.parse("tel:"+ no));
                    startActivity(dial);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    edtHp.setText(nomorTelepon);
                }
            }
        }else {
            Toast.makeText(this, "batal", Toast.LENGTH_SHORT).show();
        }
    }
}
