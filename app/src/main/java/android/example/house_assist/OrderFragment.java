package android.example.house_assist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    private static final String TAG = "OrderFrag";
    Context context;
    TextView tvServiceRequests, tvMyOrders;
    RecyclerView rvRequests, rvOrders;
    ArrayList<ServiceProvider> serviceRequestsArrayList;
    ArrayList<ServiceProvider> ordersArrayList;
    ServiceRequestsAdapter adapter;
    OrdersAdapter adapter2;
    FirebaseFirestore db;
    FirebaseUser user;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);
        context = view.getContext();
        tvServiceRequests = view.findViewById(R.id.tv_service_requests);
        rvRequests = view.findViewById(R.id.rv_service_requests);
        rvRequests.setHasFixedSize(true);
        rvRequests.setLayoutManager(new LinearLayoutManager(context));
        tvServiceRequests.setVisibility(View.GONE);
        rvRequests.setVisibility(View.GONE);

        tvMyOrders = view.findViewById(R.id.tv_orders);
        rvOrders = view.findViewById(R.id.rv_orders);
        rvOrders.setHasFixedSize(true);
        rvOrders.setLayoutManager(new LinearLayoutManager(context));
        tvMyOrders.setVisibility(View.GONE);
        rvOrders.setVisibility(View.GONE);


        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        db = FirebaseFirestore.getInstance();
        serviceRequestsArrayList = new ArrayList<ServiceProvider>();
        ordersArrayList = new ArrayList<ServiceProvider>();
        adapter = new ServiceRequestsAdapter(context ,serviceRequestsArrayList);
        adapter2 = new OrdersAdapter(context ,ordersArrayList);
        rvRequests.setAdapter(adapter);
        rvOrders.setAdapter(adapter2);

        EventChangeListener();
        getOrders();
        Log.d(TAG, "ordersArrayList: "+ordersArrayList);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void EventChangeListener() {

        db.collection("ServiceRequests").whereEqualTo("server_provider_uid", uid).addSnapshotListener((value, error) -> {
            assert value != null;

            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    serviceRequestsArrayList.add(dc.getDocument().toObject(ServiceProvider.class));
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void getOrders() {

        db.collection("Orders").whereEqualTo("customer_uid", uid).addSnapshotListener((value, error) -> {
            assert value != null;
            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    ordersArrayList.add(dc.getDocument().toObject(ServiceProvider.class));
                }
                adapter2.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        DocumentReference userRef = db.collection("Users").document(uid);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String user_type = document.getString("user_type");
                    assert user_type != null;
                    if (user_type.equals("service_provider")){
                        tvServiceRequests.setVisibility(View.VISIBLE);
                        rvRequests.setVisibility(View.VISIBLE);
                    }
                    if (user_type.equals("customer")){
                        tvMyOrders.setVisibility(View.VISIBLE);
                        rvOrders.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}