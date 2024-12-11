package vn.edu.usth.clothesapp.activity;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.clothesapp.ApiService.RetrofitClient;
import vn.edu.usth.clothesapp.db.ClothingItem;
import vn.edu.usth.clothesapp.ApiService.ServiceApi;
import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.adapter.PagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;
    WebView webView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//        // Tạo một ClothingItem mới
//        ClothingItem newItem = new ClothingItem(
//                "c002", "u002", "Red T-Shirt", "Top", "Casual",
//                "Red", "Cotton", "L", "Nike", "Summer", "Everyday",
//                "http://example.com/clothing.jpg","dadadadasdad"
//        );
//
//        // Gửi dữ liệu tới server
//        Call<ClothingItem> call = serviceApi.addClothingItem(newItem);
//        call.enqueue(new Callback<ClothingItem>() {
//            @Override
//            public void onResponse(Call<ClothingItem> call, Response<ClothingItem> response) {
//                if (response.isSuccessful()) {
//                    Log.d("MainActivity", "Thêm thành công: " + response.body());
//                } else {
//                    Log.e("MainActivity", "Lỗi: " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ClothingItem> call, Throwable t) {
//                Log.e("MainActivity", "Lỗi kết nối: " + t.getMessage());
//            }
//        });


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
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.chat).setChecked(true);
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.my_closet).setChecked(true);
                        break;
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.stylist) {
                    viewPager2.setCurrentItem(0, true);
                } else if (itemId == R.id.chat) {
                    viewPager2.setCurrentItem(1, true);
                } else if (itemId == R.id.my_closet) {
                    viewPager2.setCurrentItem(2, true);
                }
                return true;
            }
        });
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