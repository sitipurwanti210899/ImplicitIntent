package com.gosigitgo.implicit_intent_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailActivity extends AppCompatActivity {

    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_subjek)
    EditText edtSubjek;
    @BindView(R.id.edt_body)
    EditText edtBody;
    @BindView(R.id.btn_kirim)
    Button btnKirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_kirim)
    public void onViewClicked() {
        String tujuan = edtEmail.getText().toString();
        String subjek = edtSubjek.getText().toString();
        String bodyemail = edtBody.getText().toString();

        //jika kosong
        if (TextUtils.isEmpty(tujuan) || TextUtils.isEmpty(subjek) || TextUtils.isEmpty(bodyemail)){
            Toast.makeText(this, "tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else {
        //jika tidak kosong langsung ngirim
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{tujuan});
            intent.putExtra(Intent.EXTRA_SUBJECT, subjek);
            intent.putExtra(Intent.EXTRA_TEXT, bodyemail);
            intent.setType("message/rfc822");
            startActivity(intent);
        }
    }
}
