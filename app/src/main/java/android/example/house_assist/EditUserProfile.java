package android.example.house_assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.example.house_assist.Models.CustomerUser_Data;
import android.example.house_assist.Models.ServiceProvider_Data;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditUserProfile extends AppCompatActivity {

    private static final String TAG ="TAG" ;
    private LinearLayout layout;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private FirebaseFirestore db;
    private String type, uid;
    private CustomerUser_Data user_data;
    private ServiceProvider_Data serviceProviderData;
    private EditText address1,address2,pincode,state,locality,mobile;
    private TextView name,service;
    private Button update;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();
        toolbar = findViewById(R.id.edit_toolbar);
        db = FirebaseFirestore.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layout = findViewById(R.id.edit_linearlayout);
        address1 = findViewById(R.id.edit_address1);
        name = findViewById(R.id.edit_name);
        service = findViewById(R.id.edit_service);
        coordinatorLayout = findViewById(R.id.edit_coordinatorlayout);
        address2 = findViewById(R.id.edit_address2);
        pincode = findViewById(R.id.edit_pincode);
        locality = findViewById(R.id.edit_locality);
        progressBar = findViewById(R.id.edit_progressbar);
        mobile = findViewById(R.id.edit_mobile);
        state = findViewById(R.id.edit_state);
        update = findViewById(R.id.edit_update);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        update.setOnClickListener(view -> {
            updateUserData();
        });
    }

    private void updateUserData() {

        Map<String,Object> update =new HashMap<>();
        update.put("mobile",mobile.getText().toString());
        update.put("address1",address1.getText().toString().trim());
        update.put("address2",address2.getText().toString().trim());
        update.put("locality",locality.getText().toString().trim());
        update.put("state",state.getText().toString().trim());
        update.put("pin_code",pincode.getText().toString().trim());
        db.collection("Users").document(uid).update(update).addOnSuccessListener(unused -> {
            Toast.makeText(this, "Your details are updated successfully!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Sorry! There was a error due to "+e.toString(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (type.equals("service_provider")){
            fetchServiceProvider();
        }
        if (type.equals("customer")){
            fetchCustomer();
        }
    }

    private void fetchCustomer() {
        db.collection("Users").document(uid).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            CustomerUser_Data data = new CustomerUser_Data();
            data.setName(documentSnapshot.getData().get("name").toString());
            data.setPhone(documentSnapshot.getData().get("phone").toString());
            data.setEmail(documentSnapshot.getData().get("email").toString());
            data.setAddress1(documentSnapshot.getData().get("address1").toString());
            data.setAddress2(documentSnapshot.getData().get("address2").toString());
            data.setPincode(documentSnapshot.getData().get("pin_code").toString());
            data.setLocality(documentSnapshot.getData().get("locality").toString());
            data.setState(documentSnapshot.getData().get("state").toString());
            data.setUser_type(documentSnapshot.getData().get("user_type").toString());
            name.setText(data.getName());
            mobile.setText(data.getPhone());
            address1.setText(data.getAddress1());
            address2.setText(data.getAddress2());
            service.setText(data.getEmail());
            pincode.setText(data.getPincode());
            locality.setText(data.getLocality());
            state.setText(data.getState());
        });
    }

    private void fetchServiceProvider() {
        db.collection("Users").document(uid).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            CustomerUser_Data data = new CustomerUser_Data();
            data.setName(documentSnapshot.getData().get("name").toString());
            data.setPhone(documentSnapshot.getData().get("phone").toString());
            data.setEmail(documentSnapshot.getData().get("email").toString());
            data.setAddress1(documentSnapshot.getData().get("address1").toString());
            data.setAddress2(documentSnapshot.getData().get("address2").toString());
            data.setPincode(documentSnapshot.getData().get("pin_code").toString());
            data.setLocality(documentSnapshot.getData().get("locality").toString());
            data.setState(documentSnapshot.getData().get("state").toString());
            data.setUser_type(documentSnapshot.getData().get("user_type").toString());
            name.setText(data.getName());
            mobile.setText(data.getPhone());
            address1.setText(data.getAddress1());
            address2.setText(data.getAddress2());
            service.setText(data.getEmail());
            pincode.setText(data.getPincode());
            locality.setText(data.getLocality());
            state.setText(data.getState());
        });
    }
}