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

public class OrderServiceProviderProfile extends AppCompatActivity {

    private final static String TAG = "ServiceProviderProfile";
    ImageView ivDp;
    TextView tvName, tvLocality, tvAddress, tvState, tvPinCode, tvPrice, tvPhone, tvRating;
    String dp, name, locality, address, state, pin_code, phone, price, server_provider_uid, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_service_provider_profile);

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
        tvRating = findViewById(R.id.service_provider_rating);

        initData();
    }

    private void initData() {
        tvName.setText(name);
        tvLocality.setText(locality);
        tvAddress.setText(address);
        tvState.setText(state);
        tvPinCode.setText(pin_code);
        tvPrice.setText("₹ "+price);
        tvRating.setText(rating);
    }

}