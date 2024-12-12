const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');
const bcrypt = require('bcrypt');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// MongoDB Connection
mongoose.connect(process.env.MONGO_URI)
    .then(() => console.log('MongoDB Connected'))
    .catch(err => console.error('MongoDB connection error:', err));

// User Schema
const UserSchema = new mongoose.Schema({
    UserID: { type: String, required: true }, // Đảm bảo uniqueness nhưng cho phép giá trị null
    PasswordHash: { type: String, required: true },
});

const User = mongoose.model('User', UserSchema, 'Users');

// ClothingItem Schema
const ClothingItemSchema = new mongoose.Schema({
    ClothingID: { type: String, required: true, unique: true }, // Đảm bảo uniqueness
    UserID: { type: String, required: true },
    ItemName: { type: String, required: true },
    ItemType: String,
    Category: String,
    Color: String,
    Material: String,
    Size: String,
    Brand: String,
    Season: String,
    Occasion: String,
    ImageURL: String,
    ModelURL: String,
});

const ClothingItems = mongoose.model('ClothingItems', ClothingItemSchema, 'ClothingItems');

// Helper Function: Standardize Error Responses
const errorResponse = (res, message, statusCode = 400) => {
    return res.status(statusCode).json({ error: message });
};

// API Endpoint: User Registration
app.post('/register', async (req, res) => {
    console.log("Request body:", req.body);

    const { UserID, PasswordHash } = req.body;

    if (!UserID || !PasswordHash) {
        return errorResponse(res, 'Missing UserID or PasswordHash');
    }

    try {
        // Check if UserID already exists
        const existingUser = await User.findOne({ UserID });
        if (existingUser) {
            return errorResponse(res, 'UserID already exists', 409);
        }

        // Hash the password and save the new user
        const hashedPassword = await bcrypt.hash(PasswordHash, 10);
        const newUser = new User({ UserID, PasswordHash: hashedPassword });
        await newUser.save();
        res.status(201).json({ message: 'User registered successfully' });
    } catch (err) {
        if (err.code === 11000) {
            return errorResponse(res, 'Duplicate UserID detected', 409);
        }
        res.status(500).json({ error: err.message });
    }
});

// API Endpoint: User Login
app.post('/login', async (req, res) => {
    const { UserID, PasswordHash } = req.body;

    if (!UserID || !PasswordHash) {
        return errorResponse(res, 'Missing UserID or PasswordHash');
    }

    try {
        const user = await User.findOne({ UserID });
        if (!user) {
            return errorResponse(res, 'Invalid credentials', 401);
        }

        const isPasswordValid = await bcrypt.compare(PasswordHash, user.PasswordHash);
        if (!isPasswordValid) {
            return errorResponse(res, 'Invalid credentials', 401);
        }

        res.status(200).json({ message: 'Login successful' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// API Endpoint: Get All Clothing Items
app.get('/ClothingItems', async (req, res) => {
    try {
        const items = await ClothingItems.find();
        res.status(200).json(items);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// API Endpoint: Add a New Clothing Item
app.post('/ClothingItems', async (req, res) => {
    try {
        const { ClothingID, UserID, ItemName } = req.body;

        // Validate required fields
        if (!ClothingID || !UserID || !ItemName) {
            return errorResponse(res, 'Missing required fields: ClothingID, UserID, or ItemName');
        }

        const newClothingItem = new ClothingItems(req.body);
        await newClothingItem.save();
        res.status(201).json(newClothingItem);
    } catch (err) {
        if (err.code === 11000) { // Handle duplicate key error
            return errorResponse(res, 'Duplicate ClothingID detected', 409);
        }
        res.status(500).json({ error: err.message });
    }
});

// API Endpoint: Get Clothing Item by ID
app.get('/ClothingItems/:id', async (req, res) => {
    try {
        const clothingItem = await ClothingItems.findById(req.params.id);
        if (!clothingItem) {
            return errorResponse(res, 'Clothing item not found', 404);
        }
        res.status(200).json(clothingItem);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// API Endpoint: Update Clothing Item by ID
app.put('/ClothingItems/:id', async (req, res) => {
    try {
        const updatedClothingItem = await ClothingItems.findByIdAndUpdate(req.params.id, req.body, { new: true });
        if (!updatedClothingItem) {
            return errorResponse(res, 'Clothing item not found', 404);
        }
        res.status(200).json(updatedClothingItem);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// API Endpoint: Delete Clothing Item by ID
app.delete('/ClothingItems/:id', async (req, res) => {
    try {
        const deletedClothingItem = await ClothingItems.findByIdAndDelete(req.params.id);
        if (!deletedClothingItem) {
            return errorResponse(res, 'Clothing item not found', 404);
        }
        res.status(200).json({ message: 'Clothing item deleted' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// Start Server
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
