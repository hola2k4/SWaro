package vn.edu.usth.smartwaro.mycloset;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.smartwaro.R;

public class CategoryPagerAdapter extends RecyclerView.Adapter<CategoryPagerAdapter.ViewHolder> {
    private final List<CategoriesFragment.CategoryItem> categories;
    private final OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(CategoriesFragment.CategoryItem category);
    }

    public CategoryPagerAdapter(List<CategoriesFragment.CategoryItem> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_pager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesFragment.CategoryItem category = categories.get(position);
        holder.iconView.setImageResource(category.iconResId);
        holder.nameView.setText(category.name);
        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(category));
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconView;
        final TextView nameView;

        ViewHolder(View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.category_icon);
            nameView = itemView.findViewById(R.id.category_name);
        }
    }
}
