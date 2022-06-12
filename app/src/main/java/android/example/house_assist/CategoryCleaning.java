package android.example.house_assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CategoryCleaning extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<ServiceProvider> serviceProviderArrayList;
    CategoryServiceProviderAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_cleaning);

        rv = findViewById(R.id.rv_cleaning);
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

        db.collection("Cleaning").addSnapshotListener((value, error) -> {
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