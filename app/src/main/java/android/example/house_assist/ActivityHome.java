package android.example.house_assist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.house_assist.Models.CustomerUser_Data;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class ActivityHome extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    ChipNavigationBar navBar;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private FusedLocationProviderClient mFusedLocationClient;
    private TextView nav_header_name,nav_header_email;
    private RatingBar ratingBar;
    private Button rating_Button,feedbackButton;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View navHeader;
    private Bundle bundle;
    private ProgressDialog progressDialog;
    private Button signOut;
    private BottomNavigationView bottomNavigationView;
    private BottomSheetDialog bottomSheetDialogRating,bottomSheetDialogFeedback;
    private FirebaseFirestore myDB;
    private static String TAG = "Main";
    private String uid, user_type;
    private Double userLat=0.0,userLng=0.0;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadFragment(new HomeFragment());

        navBar = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.home_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView = findViewById(R.id.main_search_view);
        myDB = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        navBar.setOnItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.activity_home_drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.activity_home_navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.whiteTextColor));
        drawer.addDrawerListener(actionBarDrawerToggle);
        navHeader = navigationView.getHeaderView(0);
        nav_header_name = navHeader.findViewById(R.id.nav_header_name);
        nav_header_email = navHeader.findViewById(R.id.nav_header_email);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            Fragment fragment=null;
            switch (menuItem.getItemId())
            {
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    break;
                case R.id.nav_orders:
                    fragment = new OrderFragment();
                    loadFragment(fragment);
                    break;
                case R.id.nav_profile:
                    Intent i = new Intent(this, EditUserProfile.class);
                    i.putExtra("type", user_type);
                    startActivity(i);
                    break;
                case R.id.nav_about_us:
                    bottomSheetDialogRating = new BottomSheetDialog(ActivityHome.this);
                    View bottomrate = getLayoutInflater().inflate(R.layout.rateus_bottomsheet,null);
                    bottomSheetDialogRating.setContentView(bottomrate);
                    ratingBar = bottomrate.findViewById(R.id.ratingBar);
                    rating_Button = bottomrate.findViewById(R.id.ratingButton);
                    bottomSheetDialogRating.show();
                    rating_Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ActivityHome.this,"Thank You"+ratingBar.getRating(),Toast.LENGTH_SHORT).show();
                            bottomSheetDialogRating.hide();
                        }
                    });
                    break;
                case R.id.nav_privacy_policy:
                    bottomSheetDialogFeedback = new BottomSheetDialog(ActivityHome.this);
                    View bottomfeed = getLayoutInflater().inflate(R.layout.bottomsheet_feedback,null);
                    bottomSheetDialogFeedback.setContentView(bottomfeed);
                    feedbackButton = bottomfeed.findViewById(R.id.feedbackButton);
                    bottomSheetDialogFeedback.show();
                    feedbackButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialogFeedback.hide();
                            Toast.makeText(ActivityHome.this,"Thank You",Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case R.id.nav_logout:
                    logout();
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void logout() {

        SharedPreferences sp = getSharedPreferences("UID", Context.MODE_PRIVATE);
        sp.edit().clear().apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ActivityHome.this,Activity_Start.class));
    }

    private void loadFragment(Fragment fragment){

        if (fragment!=null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        } else {
            Toast.makeText(this, "Fragment not found!", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(int i) {

        Fragment fragment = null;
        switch (i){
            case R.id.home:
                fragment = new HomeFragment();
                break;
            case R.id.orders:
                fragment = new OrderFragment();
                break;
            case R.id.message:
                fragment = new MessageFragment();
                break;
        }

        loadFragment(fragment);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem item = menu.findItem(R.id.main_action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.my_location:
                updateLocation();
                break;
        }
        return true;
    }

    private void updateLocation() {
        Intent intent = new Intent(ActivityHome.this, Activity_Map.class);
        intent.putExtra("type","service");
        intent.putExtra("email",uid);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myDB.collection("Users").document(uid).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            CustomerUser_Data data = new CustomerUser_Data();
            data.setName(documentSnapshot.getData().get("name").toString());
            data.setEmail(documentSnapshot.getData().get("email").toString());
            data.setUser_type(documentSnapshot.getData().get("user_type").toString());
            getSupportActionBar().setTitle("Welcome, "+data.getName());
            nav_header_email.setText(data.getEmail());
            nav_header_name.setText(data.getName());
            user_type = data.getUser_type();
        });

    }
}