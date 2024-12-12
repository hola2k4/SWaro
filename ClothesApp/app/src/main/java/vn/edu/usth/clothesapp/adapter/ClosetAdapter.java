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
        if (showAddButton && position == clothingItems.size()) {
            return ITEM_TYPE_ADD_NEW;
        } else {
            return ITEM_TYPE_CLOTHING;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_CLOTHING) {
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
            ((ClothingViewHolder) holder).bind(clothingItem);
        }
    }

    @Override
    public int getItemCount() {
        return showAddButton ? clothingItems.size() + 1 : clothingItems.size();
    }

    public static class ClothingViewHolder extends RecyclerView.ViewHolder {
        private ImageView clothingImage;

        public ClothingViewHolder(View itemView) {
            super(itemView);
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
            addNewItemButton.setOnClickListener(v -> navigateToUploadImageFragment());
        }

        private void navigateToUploadImageFragment() {
            UploadImageFragment uploadImageFragment = new UploadImageFragment();
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            fragmentActivity.findViewById(R.id.view_pager).setVisibility(View.GONE);

            transaction.replace(R.id.fragment_container, uploadImageFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
