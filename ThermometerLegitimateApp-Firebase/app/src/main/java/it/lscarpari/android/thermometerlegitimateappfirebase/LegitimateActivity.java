package it.lscarpari.android.thermometerlegitimateappfirebase;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LegitimateActivity extends AppCompatActivity {

    private static final String TAG = LegitimateActivity.class.getName();

    private TemperatureViewModel mTemperatureViewModel;

    private TextView mTimeTextView;
    private TextView mValueTextView;

    private Button mSignOutButton;

    private final ValueEventListener mValueEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Temperature temperature = dataSnapshot.getValue(Temperature.class);

            if(temperature != null) {

                mTimeTextView.setText(String.valueOf(temperature.getDateTime()));
                mValueTextView.setText(String.valueOf(temperature.getValue()));

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_legitimate);

        mTemperatureViewModel = ViewModelProviders.of(this).get(TemperatureViewModel.class);

        mTimeTextView = (TextView) findViewById(R.id.time_text_view);
        mValueTextView = (TextView) findViewById(R.id.value_text_view);

        mSignOutButton = (Button) findViewById(R.id.sign_out_button);

        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Sign out");
                mTemperatureViewModel.signOut();
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        mTemperatureViewModel.addValueEventListener(mValueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");

        mTemperatureViewModel.removeEventListener(mValueEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }

}
