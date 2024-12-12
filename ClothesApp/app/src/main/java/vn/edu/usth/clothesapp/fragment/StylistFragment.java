package vn.edu.usth.clothesapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;

import vn.edu.usth.clothesapp.R;


public class StylistFragment extends Fragment {


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stylist, container, false);

        ImageView bodyModel = view.findViewById(R.id.body_model);
        ImageView clothesLayer = view.findViewById(R.id.upper_layer);
        ImageView accessoriesLayer = view.findViewById(R.id.lower_layer);
        ImageView anotherLayer = view.findViewById(R.id.full_layer);

        Button buttonBody = view.findViewById(R.id.button_body);
        Button buttonClothes = view.findViewById(R.id.button_upper);
        Button buttonAccessories = view.findViewById(R.id.button_lower);
        Button buttonAnother = view.findViewById(R.id.button_full);

        buttonBody.setOnClickListener(v -> {
            bodyModel.setVisibility(View.VISIBLE);
            clothesLayer.setVisibility(View.GONE);
            accessoriesLayer.setVisibility(View.GONE);
            anotherLayer.setVisibility(View.GONE);
        });

        buttonClothes.setOnClickListener(v -> {
            bodyModel.setVisibility(View.GONE);
            clothesLayer.setVisibility(View.VISIBLE);
            accessoriesLayer.setVisibility(View.GONE);
            anotherLayer.setVisibility(View.GONE);
        });

        buttonAccessories.setOnClickListener(v -> {
            bodyModel.setVisibility(View.GONE);
            clothesLayer.setVisibility(View.GONE);
            accessoriesLayer.setVisibility(View.VISIBLE);
            anotherLayer.setVisibility(View.GONE);
        });

        buttonAnother.setOnClickListener(v -> {
            bodyModel.setVisibility(View.GONE);
            clothesLayer.setVisibility(View.GONE);
            accessoriesLayer.setVisibility(View.GONE);
            anotherLayer.setVisibility(View.VISIBLE);
        });

        return view;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stylist, container, false);
    }

}