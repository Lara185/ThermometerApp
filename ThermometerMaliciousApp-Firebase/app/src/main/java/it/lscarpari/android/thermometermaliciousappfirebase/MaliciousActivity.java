package it.lscarpari.android.thermometermaliciousappfirebase;

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

public class MaliciousActivity extends AppCompatActivity {

    private static final String TAG = MaliciousActivity.class.getName();

    private TemperatureViewModel mTemperatureViewModel;

    private TextView mRealTimeTextView;
    private TextView mRealValueTextView;

    private TextView mModifiedTimeTextView;
    private TextView mModifiedValueTextView;

    private String datetime;

    private Button mSignOutButton;

    private final ValueEventListener mValueEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Temperature temperature = dataSnapshot.getValue(Temperature.class);

            if(temperature != null) {

                if(datetime != temperature.getDateTime()) {

                    mRealTimeTextView.setText(String.valueOf(temperature.getDateTime()));
                    mRealValueTextView.setText(String.valueOf(temperature.getValue()));

                    temperature = TemperatureGenerator.modify(temperature);

                    mModifiedTimeTextView.setText(String.valueOf(temperature.getDateTime()));
                    mModifiedValueTextView.setText(String.valueOf(temperature.getValue()));

                    mTemperatureViewModel.updateFirebaseDatabase(temperature);

                    datetime = temperature.getDateTime();

                }

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

        setContentView(R.layout.activity_malicious);

        mTemperatureViewModel = ViewModelProviders.of(this).get(TemperatureViewModel.class);

        mRealTimeTextView = (TextView) findViewById(R.id.real_time_text_view);
        mRealValueTextView = (TextView) findViewById(R.id.real_value_text_view);

        mModifiedTimeTextView = (TextView) findViewById(R.id.modified_time_text_view);
        mModifiedValueTextView = (TextView) findViewById(R.id.modified_value_text_view);

        datetime = "";

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
