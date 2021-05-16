package com.harshitmody72.inputconstraints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.harshitmody72.inputconstraints.databinding.ActivityInputConstraintsBinding;

public class InputConstraintsActivity extends AppCompatActivity {

    // request code of data transfer
    private static final int REQUEST_INPUT = 0;
    ActivityInputConstraintsBinding bind;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityInputConstraintsBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        // set title of the activity
        setTitle("Input Constraints Activity");

        //Send constraint
        sendConstraints();
    }

    /**
     * Send constraints to InputActivity
     */
    private void sendConstraints() {
        //click event listener on TakeInput Button
        bind.btnInput.setOnClickListener(v -> {

            //To put the constraints in bundle
            inputConstraints();

            //Check bundle
            if(bundle.isEmpty()){
                Toast.makeText(InputConstraintsActivity.this, "Select constraints", Toast.LENGTH_SHORT).show();
                return;
            }

            //Send Constraints to InputActivity
            Intent intent = new Intent(InputConstraintsActivity.this, InputActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_INPUT);
        });
    }

    /**
     * which constraint user checked
     * And put the constraint in bundle
     */
    private void inputConstraints(){
        //Initialize bundle
        bundle = new Bundle();

        //To check user select upperCase constraint
        if(bind.uppercaseCheckBox.isChecked()){
            bundle.putString(Constants.UPPERCASE_ALPHABETS, "true");
        }

        //To check user select lowerCase constraint
        if(bind.lowercaseCheckBox.isChecked()){
            bundle.putString(Constants.LOWERCASE_ALPHABETS, "true");
        }

        //To check user select digit constraint
        if(bind.digitsCheckBox.isChecked()){
            bundle.putString(Constants.DIGITS, "true");
        }

        //To check user select operators constraint
        if(bind.operationsCheckBox.isChecked()){
            bundle.putString(Constants.MATHEMATICAL_OPERATORS, "true");
        }

        //To check user select symbol constraint
        if(bind.symbolsCheckBox.isChecked()){
            bundle.putString(Constants.OTHER_SYMBOLS, "true");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Check result
        if(requestCode == REQUEST_INPUT && resultCode == RESULT_OK){
            bind.textView.setText("Result is : " + data.getStringExtra(Constants.INPUT_DATA));
            bind.textView.setVisibility(View.VISIBLE);

        }

    }
}