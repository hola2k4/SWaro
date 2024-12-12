package vn.edu.usth.clothesapp.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.usth.clothesapp.db.ClothingItem;

public interface ServiceApi {
    // Lấy danh sách các item quần áo
    @GET("ClothingItems")
    Call<List<ClothingItem>> getClothingItems();

    // Thêm một item quần áo mới
    @POST("ClothingItems")
    Call<ClothingItem> addClothingItem(@Body ClothingItem clothingItem);

    // Lấy thông tin item quần áo theo ID
    @GET("ClothingItems/{id}")
    Call<ClothingItem> getClothingItemById(@Path("id") String id);

    @DELETE("ClothingItems/{id}")
    Call<Void> deleteClothingItem(@Path("id") String id);
}