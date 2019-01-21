package it.lscarpari.android.thermometerlegitimateappfirebase;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseManager {

    private static final String TAG = FirebaseManager.class.getName();

    private FirebaseAuth mFirebaseAuth;

    private FirebaseDatabase mFirebaseDatabase;

    private DatabaseReference mTemperatureDatabaseReference;

    public FirebaseManager(Application application) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTemperatureDatabaseReference = mFirebaseDatabase.getReference().child("temperature");
    }

    public FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser();
    }

    public void signInWithEmailAndPassword(String email, String password) {
        Log.d(TAG, "signInWithEmailAndPassword");

        if(mFirebaseAuth.getCurrentUser() == null) {
            Log.d(TAG, "current user = " + mFirebaseAuth.getCurrentUser());
            mFirebaseAuth.signInWithEmailAndPassword(email, password);
        }
    }

    public void signOut() {
        Log.d(TAG, "signOut");

        if(mFirebaseAuth.getCurrentUser() != null) {
            Log.d(TAG, "current user = " + mFirebaseAuth.getCurrentUser());
            mFirebaseAuth.signOut();
        }
    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener mAuthStateListener) {
        Log.d(TAG, "addAuthStateListener");

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    public void removeAuthStateListener(FirebaseAuth.AuthStateListener mAuthStateListener) {
        Log.d(TAG, "removeAuthStateListener");

        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    public void updateFirebaseDatabase(Temperature temperature) {
        Log.d(TAG, "updateFirebaseDatabase");

        mTemperatureDatabaseReference.setValue(temperature);
    }

    public void addValueEventListener(ValueEventListener mValueEventListener) {
        Log.d(TAG, "addValueEventListener");

        mTemperatureDatabaseReference.addValueEventListener(mValueEventListener);
    }

    public void removeValueEventListener(ValueEventListener mValueEventListener) {
        Log.d(TAG, "removeValueEventListener");

        mTemperatureDatabaseReference.removeEventListener(mValueEventListener);
    }

}
