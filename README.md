# EncryptedDataSaving
### Introduction
Today, data protection is very important so Google considers building a class named `EncryptionSharedPreferences` which is improving `SharedPreferences` instance.
### Problem
This class is in `androidx.security.crypto` package, But working with it in the new version of this library is difficult; so I have prepared you a source code that has a class.
You can do all of works that you can do with `EncryptionSharedPreferences`, `SharedPreferences` and `SharedPreferences.Editor`.Working with it is very similar to `SharedPreferences` and it's super easy.
### Example
At the firs add this line in `gradle.build`:
`implementation "androidx.security:security-crypto:1.1.0-alpha02"`

    public class MainActivity extends AppCompatActivity{

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
    
    


