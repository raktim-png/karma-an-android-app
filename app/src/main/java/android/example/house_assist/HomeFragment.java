package android.example.house_assist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    ImageView plumber, carpenter, electrician, massage, cleaning, paint, appliances, saloon, delivery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        plumber = view.findViewById(R.id.category_plumber);
        carpenter = view.findViewById(R.id.category_carpenter);
        electrician = view.findViewById(R.id.category_electrician);
        massage = view.findViewById(R.id.category_massage);
        cleaning = view.findViewById(R.id.category_cleaning);
        paint = view.findViewById(R.id.category_paint);
        appliances = view.findViewById(R.id.category_appliances);
        saloon = view.findViewById(R.id.category_saloon);
        delivery = view.findViewById(R.id.category_delivery);

        plumber.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), CategoryPlumber.class)));
        carpenter.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), CategoryCarpenter.class)));
        electrician.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), CategoryElectrician.class)));
        massage.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), CategoryMassage.class)));
        cleaning.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), CategoryCleaning.class)));
        paint.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), CategoryPaint.class)));
        appliances.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), CategoryAppliances.class)));
        saloon.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), CategorySaloon.class)));
        delivery.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), CategoryDelivery.class)));

        return view;
    }

    /*@SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.category_plumber:
                startActivity(new Intent(getActivity(), CategoryPlumber.class));
                break;
            case R.id.category_carpenter:
                startActivity(new Intent(getActivity(), CategoryCarpenter.class));
                break;
            case R.id.category_electrician:
                startActivity(new Intent(getActivity(), CategoryElectrician.class));
                break;
            case R.id.category_massage:
                startActivity(new Intent(getActivity(), CategoryMassage.class));
                break;
            case R.id.category_cleaning:
                startActivity(new Intent(getActivity(), CategoryCleaning.class));
                break;
            case R.id.category_paint:
                startActivity(new Intent(getActivity(), CategoryPaint.class));
                break;
            case R.id.category_appliances:
                startActivity(new Intent(getActivity(), CategoryAppliances.class));
                break;
            case R.id.category_saloon:
                startActivity(new Intent(getActivity(), CategorySaloon.class));
                break;
            case R.id.category_delivery:
                startActivity(new Intent(getActivity(), CategoryDelivery.class));
                break;
            default:
                Toast.makeText(view.getContext(), "Please select a valid service.", Toast.LENGTH_SHORT).show();
        }
    }*/
}