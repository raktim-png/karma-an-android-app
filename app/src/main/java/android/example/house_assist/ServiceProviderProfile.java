package android.example.house_assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.example.house_assist.Models.CustomerUser_Data;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServiceProviderProfile extends AppCompatActivity {

    private final static String TAG = "ServiceProviderProfile";
    CardView cv;
    ImageView ivDp;
    TextView tvName, tvLocality, tvAddress, tvState, tvPinCode, tvPrice, tvPhone, tvRating;
    Button btn;
    String dp, name, locality, address, state, pin_code, phone, price, customer_uid, server_provider_uid, rating;
    FirebaseFirestore myDB;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_provider_profile);

        Intent intent = getIntent();
        server_provider_uid = intent.getStringExtra("server_provider_uid");
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address1");
        price = intent.getStringExtra("price");
        locality = intent.getStringExtra("locality");
        pin_code = intent.getStringExtra("pin_code");
        state = intent.getStringExtra("state");
        dp = intent.getStringExtra("dp");
        phone = intent.getStringExtra("phone");
        rating = intent.getStringExtra("rating");

        ivDp = findViewById(R.id.service_provider_dp);
        tvName = findViewById(R.id.service_provider_name);
        tvLocality = findViewById(R.id.service_provider_locality);
        tvAddress = findViewById(R.id.service_provider_address1);
        tvState = findViewById(R.id.service_provider_state);
        tvPinCode = findViewById(R.id.service_provider_pincode);
        tvPrice = findViewById(R.id.service_provider_price);
        tvPhone = findViewById(R.id.service_provider_phone);
        tvRating = findViewById(R.id.service_provider_rating);
        cv = findViewById(R.id.cv_phone);
        btn = findViewById(R.id.btn_service_provider);

        myDB = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        customer_uid = user.getUid();

        initData();

    }

    private void initData() {

        tvName.setText(name);
        tvLocality.setText(locality);
        tvAddress.setText(address);
        tvState.setText(state);
        tvPinCode.setText(pin_code);
        tvPhone.setText(phone);
        tvPrice.setText("₹ "+price);
        tvRating.setText(rating);
        cv.setOnClickListener(view -> {
            makeCall();
        });
        btn.setOnClickListener(view -> {
            myDB.collection("Users").document(customer_uid).get().addOnCompleteListener(task -> {
                DocumentSnapshot documentSnapshot = task.getResult();
                CustomerUser_Data data = new CustomerUser_Data();
                String customer_name = String.valueOf(documentSnapshot.get("name"));
                String customer_address = String.valueOf(documentSnapshot.get("address"));
                Log.d(TAG, "customer_name: "+customer_name);
                Log.d(TAG, "customer_address: "+customer_address);
                insertData(customer_name, customer_address);
            });
        });
    }

    private void insertData(String customer_name, String customer_address) {
        Map<String,Object> map = new HashMap<>();
        map.put("customer_name", customer_name);
        map.put("customer_address", customer_address);
        map.put("customer_uid", customer_uid);
        map.put("server_provider_uid", server_provider_uid);
        map.put("address1",address);
        map.put("name", name);
        map.put("locality",locality);
        map.put("state",state);
        map.put("pin_code",pin_code);
        map.put("price",price);
        map.put("phone", phone);
        myDB.collection("ServiceRequests").document(server_provider_uid).set(map).addOnSuccessListener(aVoid -> {
            btn.setEnabled(false);
            btn.setText("BOOKED");
            btn.setBackgroundColor(getColor(R.color.colorAccent2));
        }).addOnFailureListener(e -> {
            //Log.d(TAG,"Failure Details"+e.toString());
            Toast.makeText(this, "Unable to Update due to "+e.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    private void makeCall() {
        if (ContextCompat.checkSelfPermission(ServiceProviderProfile.this, Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ServiceProviderProfile.this, new String[]{Manifest.permission.CALL_PHONE}, 6);
        } else {
            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Objects.equals(customer_uid, server_provider_uid)){
            Toast.makeText(this, "HELLO! "+name, Toast.LENGTH_SHORT).show();
            cv.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
        }
    }
}