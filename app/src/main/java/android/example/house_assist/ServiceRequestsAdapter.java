package android.example.house_assist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceRequestsAdapter extends RecyclerView.Adapter<ServiceRequestsAdapter.MyViewHolder> {

    private static final String TAG = "ServiceReqAdapter";
    Context context;
    ArrayList<ServiceProvider> serviceRequestsArrayList;
    FirebaseFirestore db;
    FirebaseUser user;
    String uid;

    public ServiceRequestsAdapter(Context context, ArrayList<ServiceProvider> serviceRequestsArrayList) {
        this.context = context;
        this.serviceRequestsArrayList = serviceRequestsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_requests, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();
        db = FirebaseFirestore.getInstance();

        ServiceProvider data = serviceRequestsArrayList.get(position);
        holder.setIsRecyclable(false);
        holder.name.setText(data.customer_name);
        holder.address.setText(data.customer_address);
        holder.reject.setOnClickListener(view -> {
            db.collection("ServiceRequests").document(uid).delete();
                notifyItemRemoved(position);
        });
        holder.accept.setOnClickListener(view -> {
            Map<String,Object> map = new HashMap<>();
            map.put("customer_uid", data.customer_uid);
            map.put("server_provider_uid", data.server_provider_uid);
            map.put("address1", data.address1);
            map.put("name", data.name);
            map.put("locality",data.locality);
            map.put("state", data.state);
            map.put("pin_code", data.pin_code);
            map.put("price", data.price);
            map.put("phone", data.phone);
            db.collection("Orders").add(map).addOnSuccessListener(aVoid -> {
                Toast.makeText(context, "CONFIRMED", Toast.LENGTH_SHORT).show();
                db.collection("ServiceRequests").document(uid).delete();
                notifyItemRemoved(position);
            }).addOnFailureListener(e -> {
                Log.d(TAG, "Failure Details due to "+e.toString());
                Toast.makeText(context, "Unable to Update due to "+e.toString(), Toast.LENGTH_SHORT).show();
            });
        });
    }


    @Override
    public int getItemCount() {
        return serviceRequestsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView name, address, price;
        Button reject, accept;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.cv_service_requests);
            name = itemView.findViewById(R.id.service_requests_name);
            address = itemView.findViewById(R.id.service_requests_address);
            reject = itemView.findViewById(R.id.btn_reject);
            accept = itemView.findViewById(R.id.btn_accept);

        }
    }
}
