package android.example.house_assist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_ServiceProviderDetails extends AppCompatActivity {

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

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__service_provider_details);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");


        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        //Intialize
        address1 = findViewById(R.id.service_provider_address1);
        address2 = findViewById(R.id.service_provider_address2);
        pincode = findViewById(R.id.service_provider_pincode);
        locality = findViewById(R.id.service_provider_locality);
        state = findViewById(R.id.service_provider_state);
        price  = findViewById(R.id.service_provider_chargingfee);
        btn  = findViewById(R.id.service_provider_submitdetails);
        textView = findViewById(R.id.service_provider_name);
        progressDialog = new ProgressDialog(Activity_ServiceProviderDetails.this);
        progressBar = findViewById(R.id.service_providerdetails_progressbar);
        spinner = (Spinner) findViewById(R.id.serviceprovider_servicespinner);
        List<String> list = new ArrayList<String>();
        list.add("Electrician");
        list.add("Plumber");
        list.add("Carpenter");
        list.add("Massage");
        list.add("Cleaning");
        list.add("Painting");
        list.add("Appliances");
        list.add("Salon");
        list.add("Delivery");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textView.setText("Hello "+getIntent().getStringExtra("name"));
        spinner.setAdapter(dataAdapter);
        myDB = FirebaseFirestore.getInstance();
        btn.setOnClickListener(v -> {
            if(validate()){
                progressBarSet();
            Map<String,Object> map = new HashMap<>();
            map.put("server_provider_uid", uid);
            map.put("service_type",spinner.getSelectedItem().toString());
            map.put("address1",address1.getText().toString().trim());
            map.put("address2",address2.getText().toString().trim());
            map.put("name", name);
            map.put("email", email);
            map.put("phone", phone);
            map.put("locality",locality.getText().toString().trim());
            map.put("state",state.getText().toString().trim());
            map.put("pin_code",pincode.getText().toString().trim());
            map.put("price",price.getText().toString().trim());
            map.put("rating", "5.0");
            myDB.collection(spinner.getSelectedItem().toString()).document(uid).set(map).addOnSuccessListener(aVoid -> {
                Map<String,Object> users = new HashMap<>();
                users.put("user_uid", uid);
                users.put("name", name);
                users.put("email", email);
                users.put("phone", phone);
                users.put("service_type",spinner.getSelectedItem().toString());
                users.put("address1",address1.getText().toString().trim());
                users.put("address2",address2.getText().toString().trim());
                users.put("locality",locality.getText().toString().trim());
                users.put("state",state.getText().toString().trim());
                users.put("pin_code",pincode.getText().toString().trim());
                users.put("price",price.getText().toString().trim());
                users.put("rating", "5.0");
                users.put("user_type" , "service_provider");
                myDB.collection("Users").document(uid).set(users).addOnCompleteListener(task -> {
                    progressBarUnset();
                    Toast.makeText(Activity_ServiceProviderDetails.this, "Welcome",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity_ServiceProviderDetails.this, ActivityHome.class);
                    startActivity(intent);
                }).addOnFailureListener(e -> {
                    progressBarSet();
                    Log.d(TAG,"Failure Details"+e.toString());
                    Toast.makeText(Activity_ServiceProviderDetails.this, "Unable to Update", Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                progressBarSet();
                Log.d(TAG,"Failure Details"+e.toString());
                Toast.makeText(Activity_ServiceProviderDetails.this, "Unable to Update", Toast.LENGTH_SHORT).show();
            });
        }
    });



    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
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
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
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
