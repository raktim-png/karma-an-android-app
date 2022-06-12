package android.example.house_assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CategoryAppliances extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<ServiceProvider> serviceProviderArrayList;
    CategoryServiceProviderAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_appliances);

        rv = findViewById(R.id.rv_appliances);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        serviceProviderArrayList = new ArrayList<ServiceProvider>();
        adapter = new CategoryServiceProviderAdapter(this ,serviceProviderArrayList);
        rv.setAdapter(adapter);

        EventChangeListener();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void EventChangeListener() {

        db.collection("Appliances").addSnapshotListener((value, error) -> {
            assert value != null;
            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    serviceProviderArrayList.add(dc.getDocument().toObject(ServiceProvider.class));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}