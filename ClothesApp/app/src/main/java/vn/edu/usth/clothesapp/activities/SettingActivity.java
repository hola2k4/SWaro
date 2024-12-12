package vn.edu.usth.clothesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.usth.clothesapp.fragment.LanguageFragment;
import vn.edu.usth.clothesapp.fragment.ProfileFragment;
import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.fragment.ThemeFragment;

public class SettingActivity extends AppCompatActivity {
    Button languageButton, themeButton, profileButton;
    ImageButton backButton;
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        languageButton = findViewById(R.id.language_button);
        themeButton = findViewById(R.id.theme_button);
        profileButton = findViewById(R.id.profile_button);
        titleTextView = findViewById(R.id.title_text);
        backButton = findViewById(R.id.close_setting_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadLanguageFragment();
            }
        });

        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadThemeFragment();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaProfileFragment();
            }
        });
    }


    public void loadLanguageFragment() {

        themeButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        profileButton.setVisibility(View.GONE);
        titleTextView.setText("Language");

        Fragment languageFragment = new LanguageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, languageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadThemeFragment() {

        themeButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        profileButton.setVisibility(View.GONE);
        titleTextView.setText("Theme");

        Fragment themeFragment = new ThemeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, themeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loaProfileFragment() {

        themeButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        profileButton.setVisibility(View.GONE);
        titleTextView.setText("Profile");

        Fragment profileFragment = new ProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, profileFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showSettingsButtons() {
        backButton.setVisibility(View.VISIBLE);
        languageButton.setVisibility(View.VISIBLE);
        themeButton.setVisibility(View.VISIBLE);
        profileButton.setVisibility(View.VISIBLE);
    }

}