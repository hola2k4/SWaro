package vn.edu.usth.clothesapp.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import vn.edu.usth.clothesapp.ApiService.ServiceApi;
import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.adapter.PagerAdapter;
import vn.edu.usth.clothesapp.db.ClothingItem;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ImageView chatHead;
    private LinearLayout chatBox;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private EditText messageInput;
    private Button sendButton;
    private ServiceApi serviceApi;

    private static final String TAG = "MainActivity";
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRetrofitCalls();
        setupViewPager();
        setupBottomNavigation();
        setupChatHead();
        setupSendButton();
    }

    private void initializeViews() {
        viewPager2 = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.tool_bar);
        chatHead = findViewById(R.id.chat_head_icon);
        chatBox = findViewById(R.id.chatBox);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        setSupportActionBar(toolbar);
    }

    private void setupRetrofitCalls() {
        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        fetchClothingItems();
    }

    private void fetchClothingItems() {
        serviceApi.getClothingItems().enqueue(new Callback<List<ClothingItem>>() {
            @Override
            public void onResponse(Call<List<ClothingItem>> call, Response<List<ClothingItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ClothingItem> clothingItems = response.body();
                    runOnUiThread(() -> {
                        for (ClothingItem item : clothingItems) {
                            Log.d(TAG, "Item Name: " + item.getItemName());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<ClothingItem>> call, Throwable t) {
                runOnUiThread(() -> {
                    Log.e(TAG, "Error fetching data: " + t.getMessage());
                });
            }
        });
    }

    private void deleteClothingItem(String itemId) {
        serviceApi.deleteClothingItem(itemId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Item deleted successfully");
                } else {
                    Log.e(TAG, "Error deleting item: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private void setupViewPager() {
        PagerAdapter adapter = new PagerAdapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(3);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
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
    }

    private void setupBottomNavigation() {
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

    private void setupChatHead() {
        try {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
                return;
            }

            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            if (windowManager == null) {
                Log.e(TAG, "WindowManager could not be initialized");
                return;
            }

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            if (inflater == null) {
                Log.e(TAG, "LayoutInflater could not be initialized");
                return;
            }

            View chatHeadView = inflater.inflate(R.layout.chat_head_layout, null);

            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                    PixelFormat.TRANSLUCENT
            );

            params.gravity = Gravity.TOP | Gravity.START;
            params.x = 50;
            params.y = 100;

            ImageView bubbleIcon = chatHeadView.findViewById(R.id.chat_head_icon);

            chatHeadView.setOnTouchListener(new View.OnTouchListener() {
                private int initialX, initialY;
                private float initialTouchX, initialTouchY;
                private long touchStartTime;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touchStartTime = System.currentTimeMillis();
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            return true;

                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(chatHeadView, params);
                            return true;

                        case MotionEvent.ACTION_UP:
                            long touchDuration = System.currentTimeMillis() - touchStartTime;
                            if (touchDuration < 200) {
                                openChatWindow();
                            }
                            return true;
                    }
                    return false;
                }
            });

            windowManager.addView(chatHeadView, params);
        } catch (Exception e) {
            Log.e(TAG, "Error setting up chat head", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                setupChatHead();
            }
        }
    }

    private void openChatWindow() {
        int visibility = chatBox.getVisibility();
        if (visibility == View.GONE) {
            chatBox.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(chatBox, "translationY", chatBox.getHeight(), 0);
            animator.setDuration(300);
            animator.start();
        } else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(chatBox, "translationY", 0, chatBox.getHeight());
            animator.setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    chatBox.setVisibility(View.GONE);
                }
            });
            animator.start();
        }
    }

    private void setupSendButton() {

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
        return super.onOptionsItemSelected(item);
    }
}