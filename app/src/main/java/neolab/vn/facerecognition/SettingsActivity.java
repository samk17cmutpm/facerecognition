package neolab.vn.facerecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUrlEditText;

    private Button mOkButton;

    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mUrlEditText = (EditText) findViewById(R.id.url_edit_text);
        mUrlEditText.setText(BaseUrl.getInstance().getUrl());
        mOkButton = (Button) findViewById(R.id.ok_button);
        mOkButton.setOnClickListener(this);
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_button:
                BaseUrl.getInstance().setUrl(mUrlEditText.getText().toString());
                Intent intentOk = new Intent(SettingsActivity.this, UploadActivity.class);
                startActivity(intentOk);
                finish();
                break;
            case R.id.cancel_button:
                Intent intentCancel = new Intent(SettingsActivity.this, UploadActivity.class);
                startActivity(intentCancel);
                finish();
                break;
        }
    }
}
