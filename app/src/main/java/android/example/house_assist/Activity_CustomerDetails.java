package android.example.house_assist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_CustomerDetails extends AppCompatActivity {

    private EditText address1,address2,pincode,state,locality;
    private Spinner spinner;
    private TextView textView;
    private ProgressBar progressBar;
    private static final String TAG="ServiceDetails";
    private String name, email, phone, uid;
    private Button btn;
    private ProgressDialog progressDialog;
    private EditText price;
    private FirebaseFirestore myDB;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        address1 = findViewById(R.id.customer_address1);
        address2 = findViewById(R.id.customer_address2);
        pincode = findViewById(R.id.customer_pincode);
        locality = findViewById(R.id.customer_locality);
        state = findViewById(R.id.customer_state);
        btn  = findViewById(R.id.customer_submit_details);
        progressDialog = new ProgressDialog(this);
        progressBar = findViewById(R.id.service_providerdetails_progressbar);

        myDB = FirebaseFirestore.getInstance();
        btn.setOnClickListener(v -> {
            if(validate()){
                progressBarSet();
                Map<String,Object> users = new HashMap<>();
                users.put("user_uid", uid);
                users.put("name", name);
                users.put("email", email);
                users.put("address1", address1.getText().toString());
                users.put("address2",address2.getText().toString().trim());
                users.put("phone", phone);
                users.put("locality",locality.getText().toString().trim());
                users.put("state",state.getText().toString().trim());
                users.put("pin_code",pincode.getText().toString().trim());
                users.put("user_type" , "customer");
                    myDB.collection("Users").document(uid).set(users).addOnCompleteListener(task -> {
                        progressBarUnset();
                        Toast.makeText(this, "Welcome",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, ActivityHome.class);
                        startActivity(intent);
                    }).addOnFailureListener(e -> {
                        progressBarSet();
                        Log.d(TAG,"Failure Details"+e.toString());
                        Toast.makeText(this, "Unable to Update", Toast.LENGTH_SHORT).show();
                    });
                }
        });
    }

    private boolean validate()
    {
        boolean istrue = true;
        if(address1.getText().toString().equals(""))
        {
            address1.setError("Please enter address1.");
            address1.requestFocus();
            istrue = false;
        }

        if(address2.getText().toString().equals(""))
        {
            address2.setError("Please enter address2.");
            address2.requestFocus();
            istrue = false;
        }
        if(pincode.getText().toString().equals(""))
        {
            pincode.setError("Please enter pincode.");
            pincode.requestFocus();
            istrue = false;
        }
        if(state.getText().toString().equals(""))
        {
            state.setError("Please enter state.");
            state.requestFocus();
            istrue = false;
        }
        if(locality.getText().toString().equals(""))
        {
            locality.setError("Please enter locality.");
            locality.requestFocus();
            istrue = false;
        }
        return istrue;
    }

    void progressBarSet()
    {
        progressDialog.setMessage("Registering In!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void progressBarUnset()
    {
        progressDialog.dismiss();
    }

}