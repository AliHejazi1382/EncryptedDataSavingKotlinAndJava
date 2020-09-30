package com.example.encrypteddatasaving;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String FILE_NAME = "pref";
    public static final String VALUE = "value";
    EncryptedDataSaving dataSaving;
    Button btnSave, btnLoad;
    EditText etInput;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSaving = new EncryptedDataSaving(this, FILE_NAME);
        btnLoad = findViewById(R.id.btnLoad);
        btnSave = findViewById(R.id.btnSave);
        etInput = findViewById(R.id.etInput);
        etInput.setText(dataSaving.getString(VALUE, ""));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = etInput.getText().toString().trim();
                if (!string.isEmpty()){
                    dataSaving.putString(VALUE, string);
                    boolean fail = dataSaving.commit();
                    Toast.makeText(MainActivity.this, "" + fail, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "" + dataSaving.getString(VALUE, "NOT-FOUND"), Toast.LENGTH_SHORT).show();
            }
        });
    }
}