package vn.edu.usth.clothesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.fragment.UploadImageFragment;
import vn.edu.usth.clothesapp.model.ClothingItem;

import java.util.List;

public class ClosetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_CLOTHING = 0;  // Loại item quần áo
    private static final int ITEM_TYPE_ADD_NEW = 1;  // Loại item thêm mới (Add New Button)

public class ClosetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_CLOTHING = 0;
    private static final int ITEM_TYPE_ADD_NEW = 1;


    private List<ClothingItem> clothingItems;
    private boolean showAddButton;
    private FragmentActivity fragmentActivity;

    public ClosetAdapter(List<ClothingItem> clothingItems, boolean showAddButton, FragmentActivity fragmentActivity) {
        this.clothingItems = clothingItems;
        this.showAddButton = showAddButton;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public int getItemViewType(int position) {

        // Kiểm tra nếu muốn hiển thị nút "Add New" ở cuối danh sách
        if (showAddButton && position == clothingItems.size()) {
            return ITEM_TYPE_ADD_NEW;  // Hiển thị item thêm mới (Add New Button)
        } else {
            return ITEM_TYPE_CLOTHING;  // Hiển thị item quần áo bình thường

        if (showAddButton && position == clothingItems.size()) {
            return ITEM_TYPE_ADD_NEW;
        } else {
            return ITEM_TYPE_CLOTHING;

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_CLOTHING) {

            // Nếu là item quần áo, tạo view cho item_holder.xml
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holder, parent, false);
            return new ClothingViewHolder(view);
        } else if (viewType == ITEM_TYPE_ADD_NEW) {
            // Nếu là item "Add New", tạo view cho upload_item.xml
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_item, parent, false);
            return new AddNewItemViewHolder(view);  // Trả về ViewHolder cho "Add New"
        }
        return null; // Trả về null nếu không nhận diện được kiểu viewType

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holder, parent, false);
            return new ClothingViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_item, parent, false);
            return new AddNewItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ClothingViewHolder) {
            ClothingItem clothingItem = clothingItems.get(position);

            ((ClothingViewHolder) holder).bind(clothingItem); // Gắn dữ liệu vào ViewHolder
        } else if (holder instanceof AddNewItemViewHolder) {
            // Nếu là item "Add New", bạn có thể xử lý gì đó ở đây nếu cần

            ((ClothingViewHolder) holder).bind(clothingItem);

        }
    }

    @Override
    public int getItemCount() {

        return showAddButton ? clothingItems.size() + 1 : clothingItems.size();  // Nếu muốn thêm item "Add New" ở cuối
    }

    // ViewHolder cho item quần áo

        return showAddButton ? clothingItems.size() + 1 : clothingItems.size();
    }


    public static class ClothingViewHolder extends RecyclerView.ViewHolder {
        private ImageView clothingImage;

        public ClothingViewHolder(View itemView) {
            super(itemView);

            clothingImage = itemView.findViewById(R.id.imgItem);  // Link tới ImageView trong item_holder.xml
        }

        public void bind(ClothingItem clothingItem) {
            // Kiểm tra và hiển thị ảnh từ URI hoặc từ tài nguyên
            if (clothingItem.hasImageUri()) {
                clothingImage.setImageURI(clothingItem.getImageUri());  // Hiển thị ảnh từ URI (chụp ảnh)
            } else if (clothingItem.hasImageRes()) {
                clothingImage.setImageResource(clothingItem.getImageRes());  // Hiển thị ảnh từ tài nguyên (drawable)
            }
        }
    }

    // ViewHolder cho item thêm mới (Add New Item Button)

            clothingImage = itemView.findViewById(R.id.iv_clothing_image);
        }

        public void bind(ClothingItem clothingItem) {
            clothingImage.setImageResource(clothingItem.getImageRes());
        }
    }


    public class AddNewItemViewHolder extends RecyclerView.ViewHolder {
        public AddNewItemViewHolder(View itemView) {
            super(itemView);
            Button addNewItemButton = itemView.findViewById(R.id.upload_new_item_button);

            addNewItemButton.setOnClickListener(v -> navigateToUploadImageFragment());  // Xử lý click vào nút "Add New"

            addNewItemButton.setOnClickListener(v -> navigateToUploadImageFragment());

        }

        private void navigateToUploadImageFragment() {
            UploadImageFragment uploadImageFragment = new UploadImageFragment();
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();


            fragmentActivity.findViewById(R.id.view_pager).setVisibility(View.GONE);  // Ẩn view pager nếu có

            transaction.replace(R.id.fragment_container, uploadImageFragment);  // Thay đổi fragment
            transaction.addToBackStack(null);  // Thêm vào backstack để có thể quay lại
            transaction.commit();  // Thực thi giao dịch fragment
        }
    }

    // Phương thức để thêm ảnh vào danh sách và cập nhật RecyclerView
    public void addNewImage(ClothingItem clothingItem) {
        clothingItems.add(clothingItem); // Thêm item vào danh sách
        notifyItemInserted(clothingItems.size() - 1); // Cập nhật RecyclerView
    }

}



            fragmentActivity.findViewById(R.id.view_pager).setVisibility(View.GONE);

            transaction.replace(R.id.fragment_container, uploadImageFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}

