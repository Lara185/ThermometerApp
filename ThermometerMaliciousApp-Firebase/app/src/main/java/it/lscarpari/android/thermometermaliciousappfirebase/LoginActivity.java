package it.lscarpari.android.thermometermaliciousappfirebase;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private TemperatureViewModel mTemperatureViewModel;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private Button mSignInButton;

    private FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user != null) {
                // User is signed in.
                Log.d(TAG, "onAuthStateChanged: sign in");

                toastMessage("Sign in");

                Log.d(TAG, "onAuthStateChanged: startActivity");
                startActivity(new Intent(LoginActivity.this, MaliciousActivity.class));
            }
            else {
                // User is logged out.
                Log.d(TAG, "onAuthStateChanged: sing out");
                mEmailEditText.setText("");
                mPasswordEditText.setText("");
                toastMessage("Sign out");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_login);

        mTemperatureViewModel = ViewModelProviders.of(this).get(TemperatureViewModel.class);

        mEmailEditText = (EditText) findViewById(R.id.email_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                if(!email.equals("") && !password.equals("")){
                    mTemperatureViewModel.signInWithEmailAndPassword(email, password);
                }
                else {
                    Log.d(TAG, "Unable to sign in with empty fields.");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        mTemperatureViewModel.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");

        mTemperatureViewModel.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
