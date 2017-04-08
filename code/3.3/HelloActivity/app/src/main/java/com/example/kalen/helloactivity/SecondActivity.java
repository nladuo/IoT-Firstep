package com.example.kalen.helloactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = (TextView) findViewById(R.id.text_view);
        textView.setText("代码里修改的TextView");

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        textView.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.edit_text);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            String text = textView.getText() + editText.getText().toString();
            textView.setText(text);
        } else if (v.getId() == R.id.text_view) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
            Toast.makeText(this, "点击了TextView", Toast.LENGTH_SHORT).show();
        }
    }
}
