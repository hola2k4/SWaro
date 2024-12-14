package vn.edu.usth.clothesapp.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseService {
    private static FirebaseService instance;
    private final FirebaseAuth firebaseAuth;
    private final DatabaseReference databaseReference;

    private FirebaseService() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void logOut() {
        firebaseAuth.signOut();
    }
}