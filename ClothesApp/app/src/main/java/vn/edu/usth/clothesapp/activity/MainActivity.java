package vn.edu.usth.clothesapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.clothesapp.ApiService.RetrofitClient;
import vn.edu.usth.clothesapp.db.ClothingItem;
import vn.edu.usth.clothesapp.ApiService.ServiceApi;
import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.firebase.FirebaseService;
import vn.edu.usth.clothesapp.fragment.ChatFragment;
import vn.edu.usth.clothesapp.fragment.MyClosetFragment;
import vn.edu.usth.clothesapp.fragment.StylistFragment;
import vn.edu.usth.clothesapp.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String KEY_SELECTED_TAB = "selected_tab";
    private Uri imageUri;
    private int selectedTab = R.id.stylist;

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseService.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
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
                if (response.isSuccessful() && response.body() != null) {
                    List<ClothingItem> clothingItems = response.body();
                    for (ClothingItem item : clothingItems) {
                        Log.d("MainActivity", "Item Name: " + item.getItemName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ClothingItem>> call, Throwable t) {
                Log.e("MainActivity", "Error fetching data: " + t.getMessage());
            }
        });

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            selectedTab = item.getItemId();
            switchFragment(selectedTab);
            return true;
        });

        // Set the default tab
        switchFragment(selectedTab);
    }

    private void switchFragment(int itemId) {
        Fragment fragment = null;

        if (itemId == R.id.stylist) {
            fragment = new StylistFragment();
        } else if (itemId == R.id.chat) {
            fragment = new ChatFragment();
        } else if (itemId == R.id.my_closet) {
            fragment = new MyClosetFragment();
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
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
        }
        if (itemId == R.id.logout_button) {
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        // Sign out from Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        // Redirect the user to LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close the MainActivity to prevent going back
        Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
    }
}