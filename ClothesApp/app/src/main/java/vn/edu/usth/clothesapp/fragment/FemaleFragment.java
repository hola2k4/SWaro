package vn.edu.usth.clothesapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import vn.edu.usth.clothesapp.R;


public class FemaleFragment extends Fragment {

    private ImageView modelFemale, fullBlazer, fullTShirt, fullShirt, fullCardigan, fullBreast;
    private ImageButton buttonFullBlazer, buttonFullTShirt, buttonFullShirt, buttonFullCardigan, buttonFullBreast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_female, container, false);

        // Tham chiếu các ImageView
        modelFemale = view.findViewById(R.id.model_female);
        fullBlazer = view.findViewById(R.id.full_blazer);
        fullTShirt = view.findViewById(R.id.full_t_shirt);
        fullShirt = view.findViewById(R.id.full_shirt);
        fullCardigan = view.findViewById(R.id.full_cardigan);
        fullBreast = view.findViewById(R.id.full_breast);

        // Tham chiếu các nút
        buttonFullBlazer = view.findViewById(R.id.button_full_blazer);
        buttonFullTShirt = view.findViewById(R.id.button_full_t_shirt);
        buttonFullShirt = view.findViewById(R.id.button_full_shirt);
        buttonFullCardigan = view.findViewById(R.id.button_full_cardigan);
        buttonFullBreast = view.findViewById(R.id.button_full_breast);

        // Xử lý sự kiện nút bấm
        buttonFullBlazer.setOnClickListener(v -> showOutfit(fullBlazer));
        buttonFullTShirt.setOnClickListener(v -> showOutfit(fullTShirt));
        buttonFullShirt.setOnClickListener(v -> showOutfit(fullShirt));
        buttonFullCardigan.setOnClickListener(v -> showOutfit(fullCardigan));
        buttonFullBreast.setOnClickListener(v -> showOutfit(fullBreast));

        return view;
    }

    private void showOutfit(ImageView selectedOutfit) {
        // Đặt tất cả các ImageView về trạng thái "gone"
        modelFemale.setVisibility(View.VISIBLE);
        fullBlazer.setVisibility(View.GONE);
        fullTShirt.setVisibility(View.GONE);
        fullShirt.setVisibility(View.GONE);
        fullCardigan.setVisibility(View.GONE);
        fullBreast.setVisibility(View.GONE);

        // Hiển thị ImageView được chọn
        selectedOutfit.setVisibility(View.VISIBLE);
        modelFemale.setVisibility(View.GONE); // Ẩn model cơ bản khi chọn outfit
    }
}
