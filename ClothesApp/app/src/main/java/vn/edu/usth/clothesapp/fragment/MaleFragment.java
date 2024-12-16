package vn.edu.usth.clothesapp.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import vn.edu.usth.clothesapp.R;

public class MaleFragment extends Fragment {

    private Button btnShareOutfit;

    private ImageView modelMale, topTanktop, topPolo, topSw, topBlazerMale, topBreast, botJeanMale, botPant,
            tankjeanmale, tankpant,
            polojeanmale, polopant,
            swjeanmale, swpant,
            blazermale, breastmale;
    private ImageButton buttonmodelMale, buttontopTanktop, buttontopPolo, buttontopSw, buttontopBlazerMale, buttontopBreast,
    buttonbotJeanMale, buttonbotPant,
    buttontankjeanmale, buttontankpant,
    buttonpolojeanmale, buttonpolopant,
    buttonswjeanmale, buttonswpant,
    buttonblazermale, buttonbreastmale;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_male, container, false);

        btnShareOutfit = view.findViewById(R.id.btnShareOutfit);
        btnShareOutfit.setOnClickListener(v -> shareCurrentOutfit(view.findViewById(R.id.model_container)));

        modelMale = view.findViewById(R.id.model_male);
        topTanktop = view.findViewById(R.id.top_tanktop_male);
        topPolo = view.findViewById(R.id.top_polo);
        topSw = view.findViewById(R.id.top_sweater);
        topBlazerMale = view.findViewById(R.id.top_blazer_male);
        topBreast = view.findViewById(R.id.top_breast);

        botJeanMale = view.findViewById(R.id.bot_jean_male);
        botPant = view.findViewById(R.id.bot_pant);

        tankjeanmale = view.findViewById(R.id.tank_jean);
        tankpant = view.findViewById(R.id.tank_pant);

        polojeanmale =view.findViewById(R.id.polo_jean);
        polopant = view.findViewById(R.id.polo_pant);

        swjeanmale =view.findViewById(R.id.sweater_jean_male);
        swpant =view.findViewById(R.id.sweater_pant);

        blazermale = view.findViewById(R.id.male_blazer);
        breastmale =view.findViewById(R.id.male_breast);

        // Tham chiếu các nút
        buttonmodelMale = view.findViewById(R.id.male_model_icon);

        buttontopTanktop = view.findViewById(R.id.top_tanktop_male_icon);
        buttontopPolo = view.findViewById(R.id.top_polo_icon);
        buttontopSw = view.findViewById(R.id.top_sweater_male_icon);
        buttontopBlazerMale = view.findViewById(R.id.top_blazer_male_icon);
        buttontopBreast = view.findViewById(R.id.top_breast_icon);

        buttonbotJeanMale = view.findViewById(R.id.bot_jean_male_icon);
        buttonbotPant = view.findViewById(R.id.bot_pant_icon);

        buttontankjeanmale = view.findViewById(R.id.tank_jean_male_icon);
        buttontankpant = view.findViewById(R.id.tank_pant_icon);

        buttonpolojeanmale =view.findViewById(R.id.polo_jean_icon);
        buttonpolopant = view.findViewById(R.id.polo_pant_icon);

        buttonswjeanmale =view.findViewById(R.id.sweater_jean_male_icon);
        buttonswpant =view.findViewById(R.id.sweater_pant_icon);

        buttonblazermale = view.findViewById(R.id.male_blazer_icon);
        buttonbreastmale =view.findViewById(R.id.male_breast_icon);


        // Xử lý sự kiện nút bấm
        buttonmodelMale.setOnClickListener(v -> showOutfit(modelMale));
        buttontopTanktop.setOnClickListener(v -> showOutfit(topTanktop));
        buttontopPolo.setOnClickListener(v -> showOutfit(topPolo));
        buttontopSw.setOnClickListener(v -> showOutfit(topSw));
        buttontopBlazerMale.setOnClickListener(v -> showOutfit(topBlazerMale));
        buttontopBreast.setOnClickListener(v -> showOutfit(topBreast));

        buttonbotJeanMale.setOnClickListener(v -> showOutfit(botJeanMale));
        buttonbotPant.setOnClickListener(v -> showOutfit(botPant));

        buttontankjeanmale.setOnClickListener(v -> showOutfit(tankjeanmale));
        buttontankpant.setOnClickListener(v -> showOutfit(tankpant));

        buttonpolojeanmale.setOnClickListener(v -> showOutfit(polojeanmale));
        buttonpolopant.setOnClickListener(v -> showOutfit(polopant));

        buttonswjeanmale.setOnClickListener(v -> showOutfit(swjeanmale));
        buttonswpant.setOnClickListener(v -> showOutfit(swpant));

        buttonblazermale.setOnClickListener(v -> showOutfit(blazermale));
        buttonbreastmale.setOnClickListener(v -> showOutfit(breastmale));

        return view;
    }

    private void showOutfit(ImageView selectedOutfit) {
        // Đặt tất cả các ImageView về trạng thái "gone"
        modelMale.setVisibility(View.VISIBLE);
        topTanktop.setVisibility(View.GONE);
        topBreast.setVisibility(View.GONE);
        topBlazerMale.setVisibility(View.GONE);
        topSw.setVisibility(View.GONE);
        topPolo.setVisibility(View.GONE);

        botJeanMale.setVisibility(View.GONE);
        botPant.setVisibility(View.GONE);

        tankjeanmale.setVisibility(View.GONE);
        tankpant.setVisibility(View.GONE);
        polojeanmale.setVisibility(View.GONE);
        polopant.setVisibility(View.GONE);
        swjeanmale.setVisibility(View.GONE);
        swpant.setVisibility(View.GONE);
        blazermale.setVisibility(View.GONE);
        breastmale.setVisibility(View.GONE);


        // Hiển thị ImageView được chọn
        selectedOutfit.setVisibility(View.VISIBLE);
        //modelFemale.setVisibility(View.GONE);
    }

    private void shareCurrentOutfit(View container) {
        // Chụp hình ảnh của view
        Bitmap bitmap = getBitmapFromView(container);

        // Lưu ảnh vào file tạm
        try {
            File cachePath = new File(requireContext().getCacheDir(), "images");
            cachePath.mkdirs();
            File imageFile = new File(cachePath, "outfit_share.png");
            FileOutputStream stream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            // Chia sẻ file
            Uri imageUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".fileprovider", imageFile);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(shareIntent, "Chia sẻ trang phục qua"));
        } catch (IOException e) {
            Toast.makeText(getContext(), "Lỗi khi chia sẻ trang phục!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}

