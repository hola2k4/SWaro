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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.clothesapp.Chatbox.ChatActivity;
import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.firebase.FirebaseService;
import vn.edu.usth.clothesapp.fragment.ChatFragment;
import vn.edu.usth.clothesapp.fragment.MyClosetFragment;
import vn.edu.usth.clothesapp.fragment.StylistFragment;
import vn.edu.usth.clothesapp.login.LoginActivity;
import vn.edu.usth.clothesapp.utilities.Constants;
import vn.edu.usth.clothesapp.utilities.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String KEY_SELECTED_TAB = "selected_tab";
    private Uri imageUri;
    private int selectedTab = R.id.stylist;
    private PreferenceManager preferenceManager;


    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceManager = new PreferenceManager(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedImageUri = sharedPreferences.getString(KEY_IMAGE_URI, null);
        if (savedImageUri != null) {
            imageUri = Uri.parse(savedImageUri);
        }
        selectedTab = sharedPreferences.getInt(KEY_SELECTED_TAB, R.id.stylist);

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            selectedTab = item.getItemId();
            switchFragment(selectedTab);
            return true;
        });

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
        if (itemId == R.id.chatbox) {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
            return true;
        }
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
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        String userId = preferenceManager.getString(Constants.KEY_USER_ID);

        if (userId != null) {
            DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(userId);

            // Xóa token FCM của người dùng để ngăn nhận thông báo sau khi đăng xuất
            HashMap<String, Object> updates = new HashMap<>();
            updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());

            documentReference.update(updates)
                    .addOnSuccessListener(unused -> {
                        // Xóa dữ liệu trong PreferenceManager
                        preferenceManager.clear();

                        // Chuyển đến LoginActivity
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Đóng MainActivity
                        Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this, "Failed to log out", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
        }
    }
}