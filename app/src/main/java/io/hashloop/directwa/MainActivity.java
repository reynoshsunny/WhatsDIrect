package io.hashloop.directwa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    EditText edit_number;
    Button btnSend, btnShare, btnClear;
    String URL = "https://wa.me/";
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_number = findViewById(R.id.edit_txt);
        btnSend = findViewById(R.id.btn_send);
        btnShare = findViewById(R.id.btn_share);
        btnClear = findViewById(R.id.btn_clear);
        ccp = findViewById(R.id.ccp);

        btnSend.setOnClickListener(v -> {

            if (edit_number.getText().toString().length() < 5) {
                edit_number.setError("Invalid number");
            } else {
                ccp.registerCarrierNumberEditText(edit_number);

                if (ccp.isValidFullNumber()) {
                    Uri uri = Uri.parse(URL + ccp.getFullNumberWithPlus());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    edit_number.setError("Invalid number");
                }
            }
        });

        btnClear.setOnClickListener(v -> {
            edit_number.setText("");
        });

        btnShare.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            i.putExtra(Intent.EXTRA_TEXT, "Hey, Send direct message to WhatsApp with out saving contact details through this simple app \n https://play.google.com/store/apps/details?id=io.hashloop.directwa");
            startActivity(Intent.createChooser(i, "Share URL"));
        });
    }
}
