package it.lscarpari.androidthings.thermometerappfirebase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Class that realizes the ViewModel.
 */
public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAG = MainActivityViewModel.class.getName();

    // LiveData for A button.
    private ButtonALiveData mButtonALiveData;

    // LiveData for C button.
    private ButtonCLiveData mButtonCLiveData;

    // LiveData for temperature's value.
    private TemperatureLiveData mTemperatureLiveData;

    private RainbowHatManager mRainbowHatManager;

    private FirebaseManager mFirebaseManager;

    protected MainActivityViewModel(@NonNull Application application) {
        super(application);

        mButtonALiveData = new ButtonALiveData();
        mButtonCLiveData = new ButtonCLiveData();
        mTemperatureLiveData = new TemperatureLiveData(application);
        mRainbowHatManager = new RainbowHatManager(application);
        mFirebaseManager = new FirebaseManager(application);
    }

    /**
     * Gets an instance of A button LiveData.
     * @return An instance of A button LiveData.
     */
    protected LiveData<Boolean> getButtonALiveData() {
        return mButtonALiveData;
    }

    /**
     * Gets an instance of C button LiveData.
     * @return An instance of C button LiveData.
     */
    protected LiveData<Boolean> getButtonCLiveData() {
        return mButtonCLiveData;
    }

    /**
     * Gets an instance of temperature LiveData.
     * @return An instance of temperature LiveData.
     */
    protected LiveData<Float> getTemperatureLiveData() {
        return mTemperatureLiveData;
    }

    /**
     * Opens the alphanumeric display.
     */
    protected void openAlphanumericDisplay() {
        Log.d(TAG, "openAlphanumericDisplay");

        mRainbowHatManager.openAlphanumericDisplay();
    }

    /**
     * Shows a float value on the alphanumeric display.
     * @param value The float value to display.
     */
    protected void updateAlphanumericDisplay(Float value) {
        Log.d(TAG, "updateAlphanumericDisplay");

        mRainbowHatManager.updateAlphanumericDisplay(value);
    }

    /**
     * Closes the alphanumeric display.
     */
    protected void closeAlphanumericDisplay() {
        Log.d(TAG, "closeAlphanumericDisplay");

        mRainbowHatManager.closeAlphanumericDisplay();
    }

    /**
     * Makes the red led turn on and turn off.
     */
    protected void blinkLedRED() {
        Log.d(TAG, "closeAlphanumericDisplay");

        new LedAsyncTask(mRainbowHatManager).execute();
    }

    /**
     * Opens the red led.
     */
    protected void openLedRED() {
        Log.d(TAG, "openLedRED");

        mRainbowHatManager.openLedRED();
    }

    /**
     * Closes the red led.
     */
    protected void closeLedRED() {
        Log.d(TAG, "closeLedRED");

        mRainbowHatManager.closeLedRED();
    }

    /**
     * Opens the blue led.
     */
    protected void openLedBLUE() {
        Log.d(TAG, "openLedBLUE");

        mRainbowHatManager.openLedBLUE();
    }

    /**
     * Closes the blue led.
     */
    protected void closeLedBLUE() {
        Log.d(TAG, "closeLedBLUE");

        mRainbowHatManager.closeLedBLUE();
    }

    /**
     * Allows to sign in and access the database.
     * @param mAuthStateListener The listener for authentication's state changes.
     */
    public void signIn(FirebaseAuth.AuthStateListener mAuthStateListener) {
        Log.d(TAG, "signIn");

        mFirebaseManager.signIn();
        mFirebaseManager.addAuthStateListener(mAuthStateListener);
    }

    /**
     * Signs out.
     * @param mAuthStateListener The listener for authentication's state changes.
     */
    public void signOut(FirebaseAuth.AuthStateListener mAuthStateListener) {
        Log.d(TAG, "signOut");

        mFirebaseManager.removeAuthStateListener(mAuthStateListener);
        mFirebaseManager.signOut();
    }

    /**
     * Updates the database with the new temperature.
     * @param temperature The new temperature.
     */
    public void updateFirebaseDatabase(Temperature temperature) {
        Log.d(TAG, "updateFirebaseDatabase");

        mFirebaseManager.updateFirebaseDatabase(temperature);
    }

}
