package com.harshitmody72.inputconstraints;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.harshitmody72.inputconstraints.databinding.ActivityInputBinding;

public class InputActivity extends AppCompatActivity {

    ActivityInputBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        // set title of the activity
        setTitle("Input Activity");

        sendInput();
        setupHideErrorForEditText();
    }

    private void setupHideErrorForEditText() {
        //Text watcher for Input Text Field
        bind.inputTextField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bind.inputTextField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Sending data to InputConstraints Activity
     */
    private void sendInput() {
        //click event on SendBack Button
        bind.btnData.setOnClickListener(v -> {
            String text = bind.inputTextField.getEditText().getText().toString().trim();

            // check that the text is not empty
            if(text.isEmpty()){
                bind.inputTextField.setError("Please enter text");
                return;
            }
            //check data  validation
            else if(!text.matches(checkConstraints())){
                bind.inputTextField.setError("Invalid");
                return;
            }

            // sending the result back to the activity
            Intent intent = new Intent(InputActivity.this, InputConstraintsActivity.class);
            intent.putExtra(Constants.INPUT_DATA, text);
            setResult(RESULT_OK, intent);

            // to close this activity
            finish();
        });
    }

    /**
     * To check data Validation
     *
     * @return String of regex
     */
    private String checkConstraints() {
        Bundle bundle = getIntent().getExtras();
        // use to store the regex generated
        StringBuilder regex = new StringBuilder();
        regex.append("^([");
        //check data contain Uppercase letters
        if(Boolean.parseBoolean(bundle.getString(Constants.UPPERCASE_ALPHABETS, "false"))){
            regex.append("A-Z");

        }

        //check data contain Lowercase letters
        if(Boolean.parseBoolean(bundle.getString(Constants.LOWERCASE_ALPHABETS,"false"))){
            regex.append("a-z");

        }

        //check data contain Numbers
        if(Boolean.parseBoolean(bundle.getString(Constants.DIGITS,"false"))){
            regex.append("0-9");

        }

        //check data contain Operators
        if(Boolean.parseBoolean(bundle.getString(Constants.MATHEMATICAL_OPERATORS,"false"))){
            regex.append("+-/*%");

        }

        //check data contain symbols
        if(Boolean.parseBoolean(bundle.getString(Constants.OTHER_SYMBOLS,"false"))){
            regex.append("#@!&$");
        }
        regex.append("])+");

        return regex.toString();

    }
}