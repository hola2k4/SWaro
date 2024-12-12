package vn.edu.usth.clothesapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.adapter.ClosetAdapter;
import vn.edu.usth.clothesapp.model.ClothingItem;

import java.util.ArrayList;
import java.util.List;

public class MyClosetFragment extends Fragment {

    private RecyclerView rvUpperBody, rvLowerBody, rvFootwear;

    public MyClosetFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_closet, container, false);

        rvUpperBody = view.findViewById(R.id.rv_upper_body);
        rvLowerBody = view.findViewById(R.id.rv_lower_body);
        rvFootwear = view.findViewById(R.id.rv_footwear);

        rvUpperBody.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvLowerBody.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFootwear.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<ClothingItem> clothingItemsUpperBody = new ArrayList<>();
        List<ClothingItem> clothingItemsLowerBody = new ArrayList<>();
        List<ClothingItem> clothingItemsFootwear = new ArrayList<>();

        // Add sample clothing items
        clothingItemsUpperBody.add(new ClothingItem(R.drawable.tanktop));
        clothingItemsUpperBody.add(new ClothingItem(R.drawable.cloth2));

        clothingItemsLowerBody.add(new ClothingItem(R.drawable.lower1));
        clothingItemsLowerBody.add(new ClothingItem(R.drawable.lower2));

        clothingItemsFootwear.add(new ClothingItem(R.drawable.fw1));

        // Set up adapter for each RecyclerView
        ClosetAdapter upperBodyAdapter = new ClosetAdapter(clothingItemsUpperBody, true, requireActivity());
        ClosetAdapter lowerBodyAdapter = new ClosetAdapter(clothingItemsLowerBody, true, requireActivity());
        ClosetAdapter footwearAdapter = new ClosetAdapter(clothingItemsFootwear, true, requireActivity());


        rvUpperBody.setAdapter(upperBodyAdapter);
        rvLowerBody.setAdapter(lowerBodyAdapter);
        rvFootwear.setAdapter(footwearAdapter);

        return view;
    }
}
