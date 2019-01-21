package it.lscarpari.android.thermometermaliciousappfirebase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

public class TemperatureViewModel extends AndroidViewModel {

    private static final String TAG = TemperatureViewModel.class.getName();

    // private LiveData<Temperature> mTemperatureLiveData;

    private FirebaseManager mFirebaseManager;

    public TemperatureViewModel(@NonNull Application application) {
        super(application);

        mFirebaseManager = new FirebaseManager(application);
    }

    /*
    public LiveData<Temperature> getTemperatureLiveData() {
        return mTemperatureLiveData;
    }
    */

    public FirebaseUser getCurrentUser() {
        return mFirebaseManager.getCurrentUser();
    }

    public void signInWithEmailAndPassword(String email, String password) {
        Log.d(TAG, "signInWithEmailAndPassword");

        mFirebaseManager.signInWithEmailAndPassword(email, password);
    }

    public void signOut() {
        Log.d(TAG, "signOut");

        mFirebaseManager.signOut();
    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener mAuthStateListener) {
        Log.d(TAG, "addAuthStateListener");

        mFirebaseManager.addAuthStateListener(mAuthStateListener);
    }

    public void removeAuthStateListener(FirebaseAuth.AuthStateListener mAuthStateListener) {
        Log.d(TAG, "removeAuthStateListener");

        mFirebaseManager.removeAuthStateListener(mAuthStateListener);
    }

    public void updateFirebaseDatabase(Temperature temperature) {
        Log.d(TAG, "updateFirebaseDatabase");

        mFirebaseManager.updateFirebaseDatabase(temperature);
    }

    public void addValueEventListener(ValueEventListener mValueEventListener) {
        Log.d(TAG, "addValueEventListener");

        mFirebaseManager.addValueEventListener(mValueEventListener);
    }

    public void removeEventListener(ValueEventListener mValueEventListener) {
        Log.d(TAG, "removeEventListener");

        mFirebaseManager.removeValueEventListener(mValueEventListener);
    }

}
