package vn.edu.usth.clothesapp.activity;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.clothesapp.ApiService.RetrofitClient;
import vn.edu.usth.clothesapp.db.ClothingItem;
import vn.edu.usth.clothesapp.ApiService.ServiceApi;
import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.adapter.PagerAdapter;
import vn.edu.usth.clothesapp.fragment.ChatFragment;
import vn.edu.usth.clothesapp.fragment.MyClosetFragment;
import vn.edu.usth.clothesapp.fragment.StylistFragment;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String KEY_SELECTED_TAB = "selected_tab";
    private Uri imageUri;
    private int selectedTab = R.id.stylist;

    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;
    WebView webView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the saved image URI and selected tab from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedImageUri = sharedPreferences.getString(KEY_IMAGE_URI, null);
        if (savedImageUri != null) {
            imageUri = Uri.parse(savedImageUri);
        }
        selectedTab = sharedPreferences.getInt(KEY_SELECTED_TAB, R.id.stylist);

        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        serviceApi.getClothingItems().enqueue(new Callback<List<ClothingItem>>() {
            @Override
            public void onResponse(Call<List<ClothingItem>> call, Response<List<ClothingItem>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ClothingItem> clothingItems = response.body();
                    for (ClothingItem item : clothingItems){
                        Log.d(TAG, "Item Name" + item.getItemName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ClothingItem>> call, Throwable t) {
                Log.e(TAG, "Error fetching data: " + t.getMessage());
            }
        });

        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        String itemIdToDelete = "673f05a3aec8cafea591015d"; // Thay bằng ObjectId của item cần xóa
        serviceApi.deleteClothingItem(itemIdToDelete).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Item deleted successfully");
                } else {
                    Log.e("MainActivity", "Error deleting item: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });

        viewPager2 = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        PagerAdapter adapter = new PagerAdapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(3);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.stylist).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.chat).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.my_closet).setChecked(true);
                        break;
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Map<Integer, Integer> menuItemToPositionMap = new HashMap<>();
            menuItemToPositionMap.put(R.id.stylist, 0);
            menuItemToPositionMap.put(R.id.chat, 1);
            menuItemToPositionMap.put(R.id.my_closet, 2);

            Integer position = menuItemToPositionMap.get(item.getItemId());
            if (position != null) {
                viewPager2.setCurrentItem(position, true);
                selectedTab = item.getItemId();
                return true;
            }
            return false;
        });

        // Set the selected tab
        bottomNavigationView.setSelectedItemId(selectedTab);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (imageUri != null) {
            outState.putParcelable(KEY_IMAGE_URI, imageUri);
        }

        // Save the image URI and selected tab to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IMAGE_URI, imageUri != null ? imageUri.toString() : null);
        editor.putInt(KEY_SELECTED_TAB, selectedTab);
        editor.apply();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(KEY_IMAGE_URI);
            selectedTab = savedInstanceState.getInt(KEY_SELECTED_TAB, R.id.stylist);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.setting_button) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.home_button) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
