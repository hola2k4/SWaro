package vn.edu.usth.clothesapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.activity.MainActivity;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("ProfileFragment", "onCreateView: ProfileFragment is being created");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.setting_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
