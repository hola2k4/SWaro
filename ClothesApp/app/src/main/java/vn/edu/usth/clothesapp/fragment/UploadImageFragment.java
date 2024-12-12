package vn.edu.usth.clothesapp.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

import vn.edu.usth.clothesapp.R;
import vn.edu.usth.clothesapp.model.ClothingItem;

public class UploadImageFragment extends Fragment {

    private Button btnPhoto;
    private Button btnOk;
    private ImageView imgCaptured;
    private Uri imageUri;

    // Đăng ký ActivityResultLauncher cho yêu cầu quyền camera
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    // Đăng ký ActivityResultLauncher để mở camera và nhận kết quả
    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    // Kiểm tra và hiển thị ảnh đã chụp
                    if (imageUri != null) {
                        imgCaptured.setImageURI(imageUri);  // Hiển thị ảnh trong ImageView của UploadImageFragment
                        btnOk.setVisibility(View.VISIBLE);  // Hiển thị nút OK sau khi chụp ảnh

                        // In ra đường dẫn của imageUri trong Logcat để kiểm tra
                        Log.d("UploadImageFragment", "Image captured with URI: " + imageUri.toString());
                    } else {
                        Toast.makeText(getContext(), "Failed to capture image", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_upload_image, container, false);

        btnPhoto = rootView.findViewById(R.id.btn_photo);
        btnOk = rootView.findViewById(R.id.btnOk);
        imgCaptured = rootView.findViewById(R.id.imgCaptured);

        // Sự kiện khi người dùng nhấn nút chụp ảnh
        btnPhoto.setOnClickListener(v -> {
            // Kiểm tra quyền camera
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                // Yêu cầu quyền camera
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA);
            }
        });

        // Sự kiện khi người dùng nhấn nút OK
        btnOk.setOnClickListener(v -> {
            // Tạo đối tượng ClothingItem chứa ảnh đã chụp
            ClothingItem newClothingItem = new ClothingItem("New Clothing Item", imageUri);

            // Truyền đối tượng ClothingItem vào MyClosetFragment
            MyClosetFragment myClosetFragment = (MyClosetFragment) getActivity()
                    .getSupportFragmentManager()
                    .findFragmentByTag(MyClosetFragment.class.getSimpleName());

            if (myClosetFragment == null) {
                // Nếu không tìm thấy MyClosetFragment, tạo mới và thêm vào fragment manager
                myClosetFragment = new MyClosetFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, myClosetFragment, MyClosetFragment.class.getSimpleName())
                        .commit();
                getActivity().getSupportFragmentManager().executePendingTransactions();
            }

            // Gọi phương thức addImageToRecyclerView để thêm ảnh vào RecyclerView của MyClosetFragment
            myClosetFragment.addImageToRecyclerView(newClothingItem);

            // Quay lại MyClosetFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, myClosetFragment)  // Thay thế fragment
                    .addToBackStack(null)  // Đưa vào backstack để quay lại sau
                    .commit();
        });

        return rootView;
    }

    // Phương thức mở camera
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = createImageFile();
        if (photoFile != null) {
            imageUri = FileProvider.getUriForFile(getContext(),
                    "vn.edu.usth.clothesapp.fileprovider", // Thay bằng tên gói của bạn
                    photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraActivityResultLauncher.launch(cameraIntent);
        }
    }

    private File createImageFile() {
        // Tạo tên tệp ảnh với thời gian hiện tại
        String imageFileName = "IMG_" + System.currentTimeMillis();

        // Lấy thư mục để lưu ảnh trong bộ nhớ ngoài (thư mục riêng của ứng dụng)
        File storageDir = getContext().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);

        // Tạo tệp ảnh trong thư mục Pictures của ứng dụng
        File image = new File(storageDir, imageFileName + ".png");

        try {
            if (image.createNewFile()) {
                return image;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.clothesapp.R;


public class UploadImageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_image, container, false);
    }
}

