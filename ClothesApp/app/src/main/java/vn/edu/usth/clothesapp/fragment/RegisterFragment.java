package vn.edu.usth.clothesapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vn.edu.usth.clothesapp.R;

public class RegisterFragment extends Fragment {

    private static final String TAG = "RegisterFragment";
    private static final String REGISTER_URL = "http://10.0.2.2:5000/register"; // Địa chỉ API (10.0.2.2 là localhost khi dùng emulator)
    private final OkHttpClient client = new OkHttpClient(); // OkHttpClient để gọi API

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Ánh xạ các EditText và Button từ layout
        EditText userIDEditText = view.findViewById(R.id.username_edit_text); // UserID input
        EditText passwordEditText = view.findViewById(R.id.password_edit_text); // Password input
        EditText confirmPasswordEditText = view.findViewById(R.id.confirm_password_edit_text); // Confirm password input
        Button registerButton = view.findViewById(R.id.register_submit_button); // Register button

        // Lắng nghe sự kiện click của nút đăng ký
        registerButton.setOnClickListener(v -> {
            String userID = userIDEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // Kiểm tra tính hợp lệ của dữ liệu
            if (userID.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Gửi yêu cầu đăng ký
                registerUser(userID, password);
            }
        });

        return view;
    }

    private void registerUser(String userID, String password) {
        // Tạo JSON object chứa dữ liệu gửi lên server
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserID", userID); // Trường "UserID" cần trùng khớp với backend
            jsonObject.put("PasswordHash", password);
        } catch (JSONException e) {
            Log.e(TAG, "JSON Exception: " + e.getMessage());
            Toast.makeText(getActivity(), "Failed to create registration data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo RequestBody cho POST request
        RequestBody requestBody = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        // Tạo Request và cấu hình API
        Request request = new Request.Builder()
                .url(REGISTER_URL) // URL API từ server
                .post(requestBody) // Gửi dữ liệu bằng POST
                .build();

        // Thực hiện API call qua OkHttpClient
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "API Call Failed: " + e.getMessage());
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getActivity(), "Registration failed. Please check your connection.", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getActivity(), "Registration successful!", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    String errorMessage = response.body() != null ? response.body().string() : "Unknown error";
                    Log.e(TAG, "API Response Error: " + errorMessage);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getActivity(), "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show());
                    }
                }
            }
        });
    }
}
