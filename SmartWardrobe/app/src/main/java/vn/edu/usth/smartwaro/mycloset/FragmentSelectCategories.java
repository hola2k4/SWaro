package vn.edu.usth.smartwaro.mycloset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import vn.edu.usth.smartwaro.R;

public class FragmentSelectCategories extends Fragment {

    private RecyclerView recyclerView;
    private OnCategorySelectedListener onCategorySelectedListener;

    // Define the listener interface
    public interface OnCategorySelectedListener {
        void onCategorySelected(CategoriesFragment.CategoryItem category);
    }

    public void setOnCategorySelectedListener(OnCategorySelectedListener listener) {
        this.onCategorySelectedListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_categories, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_select_categories);

        List<CategoriesFragment.CategoryItem> categories = Arrays.asList(
                new CategoriesFragment.CategoryItem("Short Sleeve", R.drawable.ic_category_tops),
                new CategoriesFragment.CategoryItem("Bottoms", R.drawable.ic_category_bottoms),
                new CategoriesFragment.CategoryItem("Dresses", R.drawable.ic_category_dresses),
                new CategoriesFragment.CategoryItem("Outerwear", R.drawable.ic_category_outerwear),
                new CategoriesFragment.CategoryItem("Shoes", R.drawable.ic_category_shoes),
                new CategoriesFragment.CategoryItem("Accessories", R.drawable.ic_category_accessories)
        );

        CategoryAdapter adapter = new CategoryAdapter(requireContext(), categories, category -> {
            if (onCategorySelectedListener != null) {
                onCategorySelectedListener.onCategorySelected(category);
            } else {
                Toast.makeText(getContext(), "Selected: " + category.name, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
