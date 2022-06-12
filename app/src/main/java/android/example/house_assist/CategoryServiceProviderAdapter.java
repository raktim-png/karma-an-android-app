package android.example.house_assist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryServiceProviderAdapter extends RecyclerView.Adapter<CategoryServiceProviderAdapter.MyViewHolder> {

    Context context;
    ArrayList<ServiceProvider> serviceProviderArrayList;

    public CategoryServiceProviderAdapter(Context context, ArrayList<ServiceProvider> serviceProviderArrayList) {
        this.context = context;
        this.serviceProviderArrayList = serviceProviderArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_providers, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ServiceProvider data = serviceProviderArrayList.get(position);
        holder.setIsRecyclable(false);
        holder.name.setText(data.name);
        holder.address.setText(data.address1);
        holder.price.setText("₹ "+data.price);
        holder.cv.setOnClickListener(view -> {
            Intent i = new Intent(context, ServiceProviderProfile.class);
            i.putExtra("server_provider_uid", data.server_provider_uid);
            i.putExtra("name", data.name);
            i.putExtra("address1", data.address1);
            i.putExtra("price", data.price);
            i.putExtra("locality", data.locality);
            i.putExtra("pin_code", data.pin_code);
            i.putExtra("state", data.state);
            i.putExtra("dp", data.dp);
            i.putExtra("phone", data.phone);
            i.putExtra("rating", data.rating);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return serviceProviderArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView name, address, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.cv_service_provider);
            name = itemView.findViewById(R.id.service_provider_name);
            address = itemView.findViewById(R.id.service_provider_address1);
            price = itemView.findViewById(R.id.service_provider_price);

        }
    }
}
