package vn.edu.usth.clothesapp.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import vn.edu.usth.clothesapp.R;

public class MaleFragment extends Fragment {

    private ImageView modelMale, topBlazer, topTShirt, botSkirt;
    private ImageButton buttonTopBlazer, buttonTopTShirt, buttonbotSkirt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_male, container, false);

        modelMale = rootView.findViewById(R.id.model_male);
        topBlazer = rootView.findViewById(R.id.top_blazer);
        topTShirt = rootView.findViewById(R.id.top_t_shirt);
        botSkirt = rootView.findViewById(R.id.bot_skirt);

        buttonTopBlazer = rootView.findViewById(R.id.button_top_blazer);
        buttonTopTShirt = rootView.findViewById(R.id.button_top_t_shirt);
        buttonbotSkirt = rootView.findViewById(R.id.button_bot_shirt);

        // Set default visibility
        topBlazer.setVisibility(View.GONE);
        topTShirt.setVisibility(View.GONE);
        botSkirt.setVisibility(View.GONE);

        // Top Blazer button logic
        buttonTopBlazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topBlazer.setVisibility(View.VISIBLE);
                topTShirt.setVisibility(View.GONE);
                botSkirt.setVisibility(View.GONE);
                combineImages();  // method to combine images
            }
        });

        // Top T-Shirt button logic
        buttonTopTShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topBlazer.setVisibility(View.GONE);
                topTShirt.setVisibility(View.VISIBLE);
                botSkirt.setVisibility(View.GONE);
                combineImages();  // method to combine images
            }
        });

        // Bottom Pants button logic
        buttonbotSkirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topBlazer.setVisibility(View.GONE);
                topTShirt.setVisibility(View.GONE);
                botSkirt.setVisibility(View.VISIBLE);
                combineImages();  // method to combine images
            }
        });

        return rootView;
    }

    private void combineImages() {
        Bitmap result = BitmapFactory.decodeResource(getResources(), R.drawable.male); // base image

        if (topBlazer.getVisibility() == View.VISIBLE) {
            result = combineBitmaps(result, BitmapFactory.decodeResource(getResources(), R.drawable.top_blazer));
        } else if (topTShirt.getVisibility() == View.VISIBLE) {
            result = combineBitmaps(result, BitmapFactory.decodeResource(getResources(), R.drawable.top_shirt));
        }

        if (botSkirt.getVisibility() == View.VISIBLE) {
            result = combineBitmaps(result, BitmapFactory.decodeResource(getResources(), R.drawable.lower_body));
        }

        modelMale.setImageBitmap(result);
    }

    private Bitmap combineBitmaps(Bitmap base, Bitmap overlay) {
        Bitmap combined = Bitmap.createBitmap(base.getWidth(), base.getHeight(), base.getConfig());
        Canvas canvas = new Canvas(combined);
        canvas.drawBitmap(base, 0, 0, null);
        canvas.drawBitmap(overlay, 0, 0, null);
        return combined;
    }
}
