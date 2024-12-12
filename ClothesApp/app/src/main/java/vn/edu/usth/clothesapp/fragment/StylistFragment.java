package vn.edu.usth.clothesapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.edu.usth.clothesapp.R;

public class StylistFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stylist, container, false);

        Button buttonMale = view.findViewById(R.id.button_male);
        Button buttonFemale = view.findViewById(R.id.button_female);

        buttonMale.setOnClickListener(v -> openFragment(new MaleFragment()));
        buttonFemale.setOnClickListener(v -> openFragment(new FemaleFragment()));

        return view;
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Ensure you have a container with this ID
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
