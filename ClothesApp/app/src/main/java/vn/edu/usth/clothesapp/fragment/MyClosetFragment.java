package vn.edu.usth.clothesapp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private ClosetAdapter upperBodyAdapter, lowerBodyAdapter, footwearAdapter;
    private List<ClothingItem> clothingItemsUpperBody, clothingItemsLowerBody, clothingItemsFootwear;

    public MyClosetFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_closet, container, false);

        // Khởi tạo RecyclerView
        rvUpperBody = view.findViewById(R.id.rv_upper_body);
        rvLowerBody = view.findViewById(R.id.rv_lower_body);
        rvFootwear = view.findViewById(R.id.rv_footwear);

        rvUpperBody.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvLowerBody.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFootwear.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Khởi tạo danh sách quần áo (bạn cần khởi tạo chúng trước khi sử dụng)
        clothingItemsUpperBody = new ArrayList<>();
        clothingItemsLowerBody = new ArrayList<>();
        clothingItemsFootwear = new ArrayList<>();

        // Thêm mẫu quần áo vào danh sách (tuỳ chỉnh cho phù hợp)
        clothingItemsUpperBody.add(new ClothingItem("Áo thun", R.drawable.tanktop));
        clothingItemsUpperBody.add(new ClothingItem("Áo sơ mi", R.drawable.cloth2));

        clothingItemsLowerBody.add(new ClothingItem("Quần jeans", R.drawable.lower1));
        clothingItemsLowerBody.add(new ClothingItem("Quần tây", R.drawable.lower2));

        clothingItemsFootwear.add(new ClothingItem("Giày thể thao", R.drawable.fw1));

        // Khởi tạo adapter với các danh sách đã khởi tạo
        upperBodyAdapter = new ClosetAdapter(clothingItemsUpperBody, true, requireActivity());
        lowerBodyAdapter = new ClosetAdapter(clothingItemsLowerBody, true, requireActivity());
        footwearAdapter = new ClosetAdapter(clothingItemsFootwear, true, requireActivity());

        // Thiết lập adapter cho RecyclerView
        rvUpperBody.setAdapter(upperBodyAdapter);
        rvLowerBody.setAdapter(lowerBodyAdapter);
        rvFootwear.setAdapter(footwearAdapter);

        return view;
    }

    // Phương thức này sẽ được gọi từ UploadImageFragment sau khi chụp ảnh
    public void addImageToRecyclerView(ClothingItem clothingItem) {
        if (clothingItemsUpperBody != null) {
            clothingItemsUpperBody.add(clothingItem);
            upperBodyAdapter.notifyItemInserted(clothingItemsUpperBody.size() - 1); // Cập nhật RecyclerView
        }

        if (clothingItemsLowerBody != null) {
            clothingItemsLowerBody.add(clothingItem);
            lowerBodyAdapter.notifyItemInserted(clothingItemsLowerBody.size() - 1); // Cập nhật RecyclerView
        }

        if (clothingItemsFootwear != null) {
            clothingItemsFootwear.add(clothingItem);
            footwearAdapter.notifyItemInserted(clothingItemsFootwear.size() - 1); // Cập nhật RecyclerView
        }
    }
}
