package it.lscarpari.androidthings.thermometerappfirebase;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainActivityViewModel mMainActivityViewModel;

    private long counter = 0;

    /**
     * If button A is pressed,
     * it registers an observer for temperature's value
     * and shows temperature on display.
     * If button A is pressed again,
     * it removes the observer and closes the display.
     */
    private final Observer<Boolean> mButtonALiveDataObserver = new Observer<Boolean>() {

        @Override
        public void onChanged(@Nullable Boolean aBoolean) {
            Log.d(TAG, "onChanged: " + aBoolean);

            if(aBoolean != null && aBoolean) {
                if (mMainActivityViewModel.getTemperatureLiveData().hasObservers()) {
                    mMainActivityViewModel.getTemperatureLiveData().removeObservers(MainActivity.this);
                    mMainActivityViewModel.closeAlphanumericDisplay();
                    counter = 0;
                }
                else {
                    mMainActivityViewModel.openAlphanumericDisplay();
                    mMainActivityViewModel.getTemperatureLiveData().observe(MainActivity.this, mTemperatureLiveDataObserver);
                }
            }
        }

    };

    /**
     * If button C is press, it stops the execution.
     */
    private final Observer<Boolean> mButtonCLiveDataObserver = new Observer<Boolean>() {

        @Override
        public void onChanged(@Nullable Boolean aBoolean) {
            Log.d(TAG, "onChanged: " + aBoolean);

            if(aBoolean != null && aBoolean) {
                finish();
            }
        }

    };

    /**
     * It writes a new temperature's value to Firebase Database.
     */
    private final Observer<Float> mTemperatureLiveDataObserver = new Observer<Float>() {

        @Override
        public void onChanged(@Nullable Float value) {
            //Log.d(TAG, "onChanged: " + value);

            if(value != null) {
                if(counter % 200 == 0) {
                    Log.d(TAG, "onChanged: " + value);
                    mMainActivityViewModel.blinkLedRED();
                    mMainActivityViewModel.updateAlphanumericDisplay(value);
                    mMainActivityViewModel.updateFirebaseDatabase(new Temperature(value));
                }

                counter++;
            }
        }
    };

    /**
     * It listens to authentication's state changes.
     * If user is signed in, it turns the blue led on
     * and it attaches an observer for button A and for button C.
     */
    private FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if(user != null) {

                Log.d(TAG, "onAuthStateChanged: sign in");

                mMainActivityViewModel.openLedBLUE();

                mMainActivityViewModel.getButtonCLiveData().observe(MainActivity.this, mButtonCLiveDataObserver);

                mMainActivityViewModel.getButtonALiveData().observe(MainActivity.this, mButtonALiveDataObserver);

            }
            else {
                Log.d(TAG, "onAuthStateChanged: sign out");
            }

        }
    };

    /**
     * It logs the user in.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mMainActivityViewModel.signIn(mAuthStateListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    /**
     * It logs the user out,
     * detaches all the observers
     * and closes the resources.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");

        mMainActivityViewModel.signOut(mAuthStateListener);

        if (mMainActivityViewModel.getTemperatureLiveData().hasObservers())
            mMainActivityViewModel.getTemperatureLiveData().removeObservers(MainActivity.this);

        if (mMainActivityViewModel.getButtonALiveData().hasObservers())
            mMainActivityViewModel.getButtonALiveData().removeObservers(MainActivity.this);

        if (mMainActivityViewModel.getButtonCLiveData().hasObservers())
            mMainActivityViewModel.getButtonCLiveData().removeObservers(MainActivity.this);

        mMainActivityViewModel.closeAlphanumericDisplay();
        mMainActivityViewModel.closeLedRED();
        mMainActivityViewModel.closeLedBLUE();
    }

}
