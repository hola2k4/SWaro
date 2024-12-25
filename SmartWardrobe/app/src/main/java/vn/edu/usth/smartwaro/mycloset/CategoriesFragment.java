package vn.edu.usth.smartwaro.mycloset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import vn.edu.usth.smartwaro.R;

public class CategoriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        recyclerView = view.findViewById(R.id.categories_recycler_view);
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        List<CategoryItem> categories = Arrays.asList(
                new CategoryItem("Short Sleeve", R.drawable.ic_category_tops),
                new CategoryItem("Bottoms", R.drawable.ic_category_bottoms),
                new CategoryItem("Dresses", R.drawable.ic_category_dresses),
                new CategoryItem("Outerwear", R.drawable.ic_category_outerwear),
                new CategoryItem("Shoes", R.drawable.ic_category_shoes),
                new CategoryItem("Accessories", R.drawable.ic_category_accessories)
        );

        adapter = new CategoryAdapter(requireContext(), categories, category -> {
            if ("Short Sleeve".equals(category.name)) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ShortSleeveFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    public static class CategoryItem {
        public final String name;
        public final int iconResId;

        public CategoryItem(String name, int iconResId) {
            this.name = name;
            this.iconResId = iconResId;
        }
    }
}
