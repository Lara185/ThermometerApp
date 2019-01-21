package it.lscarpari.androidthings.thermometerappfirebase;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Class that helps to authenticate on Firebase and use the database.
 */
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

    /**
     * Allows to sign in and access the database.
     */
    public void signIn() {
        Log.d(TAG, "signIn");

        if(mFirebaseAuth.getCurrentUser() == null) {
            mFirebaseAuth.signInWithEmailAndPassword("email", "password");
        }
    }

    /**
     * Signs out.
     */
    public void signOut() {
        Log.d(TAG, "signOut");

        if(mFirebaseAuth.getCurrentUser() != null) {
            mFirebaseAuth.signOut();
        }
    }

    /**
     * Adds a listener for authentication's state changes.
     * @param mAuthStateListener The listener to add.
     */
    public void addAuthStateListener(FirebaseAuth.AuthStateListener mAuthStateListener) {
        Log.d(TAG, "addAuthStateListener");

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    /**
     * Removes the listener for authentication's state changes.
     * @param mAuthStateListener The listener to remove.
     */
    public void removeAuthStateListener(FirebaseAuth.AuthStateListener mAuthStateListener) {
        Log.d(TAG, "removeAuthStateListener");

        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    /**
     * Updates the database with the new temperature.
     * @param temperature The new temperature.
     */
    public void updateFirebaseDatabase(Temperature temperature) {
        Log.d(TAG, "updateFirebaseDatabase");

        mTemperatureDatabaseReference.setValue(temperature);
    }


}
