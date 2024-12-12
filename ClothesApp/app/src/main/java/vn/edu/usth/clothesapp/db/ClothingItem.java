package vn.edu.usth.clothesapp.db;

public class ClothingItem {
    private String ClothingID;
    private String UserID;
    private String ItemName;
    private String ItemType;
    private String Category;
    private String Color;
    private String Material;
    private String Size;
    private String Brand;
    private String Season;
    private String Occasion;
    private String ImageURL;
    private String ModelURL;

    // Constructors, getters, and setters
    public ClothingItem(String clothingID, String userID, String itemName, String itemType, String category,
                        String color, String material, String size, String brand, String season,
                        String occasion, String imageURL, String modelURL) {
        ClothingID = clothingID;
        UserID = userID;
        ItemName = itemName;
        ItemType = itemType;
        Category = category;
        Color = color;
        Material = material;
        Size = size;
        Brand = brand;
        Season = season;
        Occasion = occasion;
        ImageURL = imageURL;
        ModelURL = modelURL;
    }

    public String getClothingID() {
        return ClothingID;
    }

    public void setClothingID(String clothingID) {
        ClothingID = clothingID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getSeason() {
        return Season;
    }

    public void setSeason(String season) {
        Season = season;
    }

    public String getOccasion() {
        return Occasion;
    }

    public void setOccasion(String occasion) {
        Occasion = occasion;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getModelURL() {
        return ModelURL;
    }

    public void setModelURL(String modelURL) {
        ModelURL = modelURL;
    }
}